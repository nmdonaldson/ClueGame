package clueGame;

import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.io.*;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

public class Board extends JPanel implements MouseListener {
	private int numRows;
	private int numCols;
	private int dieRoll;
	private int playerCounter = 0;
	private int X;
	private int Y;
	private boolean playerTurnOver;
	public final static int MAX_BOARD_SIZE = 50;
	private final static int DIM_X = 30; // Represents the width of each cell
	private final static int DIM_Y = 30; // Represents the height of each cell
	private BoardCell[][] grid;
	private Map<BoardCell, Set<BoardCell>> adjStore;
	private Set<BoardCell> targets;
	private Map<Character, String> legend;
	private Set<String> types;
	private String boardConfigFile;
	private String roomConfigFile;
	private ArrayList<Player> my_players;
	private Solution my_solution;
	private Solution currentGuess;
	private Decks my_deck;
	private Card guessResponse;
	private static Board instance = new Board();

	// Constructor, private to ensure only one is created
	private Board() {
		super();
		this.adjStore = new HashMap<BoardCell, Set<BoardCell>>();
		this.targets = new HashSet<BoardCell>();
		this.legend = new HashMap<Character, String>();
		this.types = new HashSet<String>();
		this.grid = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		this.my_solution =  new Solution();
		this.my_players = new ArrayList<Player>();
		this.my_deck = Decks.getInstance();
		this.playerTurnOver = false;
		this.currentGuess = new Solution();
		this.guessResponse = new Card();
		addMouseListener(this);
	}
	

	// Draws the board and its components
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draws the board
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				grid[i][j].draw(g, legend, DIM_X, DIM_Y);
				super.repaint();
			}
		}
		
		// Draws the human player's targets
		if (playerCounter == 0) my_players.get(playerCounter).drawTargets(g, targets, DIM_X, DIM_Y);
		
		// Draws each player
		for (Player player : my_players) player.draw(g, DIM_X, DIM_Y);
		super.repaint();
	}

	// Checks the accusation's accuracy
	public boolean checkAccusation(Solution accusation) { return accusation.equals(my_solution);}
	
	
	// Checks a suggestion for any matches between the players
	// Player param is the player making the accusation, suggestion is their suggestion
	public Card handleSuggestion(Solution suggestion, Player player) {
		boolean validAns = true;
		Card answer = new Card();
		// Searches every player's deck for a card that can disprove the suggestion
		for (int i = my_players.size() - 1; i > -1; i--) {
			// First player is the human player
			if (i == 0) {
				answer = my_players.get(i).disproveSuggestion(suggestion);
				// If human is accuser and they can disprove, return null
				if (player.getCards().contains(answer)) return null;
				// Otherwise, return the human's answer
				return answer;
			}
			answer = my_players.get(i).disproveSuggestion(suggestion);
			// If the accusing player has the card, return null
			if (player.getCards().contains(answer)) validAns = false;
			else if (answer != null) return answer;
		}
		
		if (validAns) {
			return answer;
		}
		else return null;
	}
	
	// Calculates adjacency list for each grid cell and stores into map adjStore
	public void calcAdjacencies() {
		// loop through grid
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Set<BoardCell> tempSet = new HashSet<BoardCell>();
				cellCheck(tempSet, grid[i][j].getInitial(), i, j);
			}
		}
	}
	
	public void cellCheck(Set<BoardCell> tempSet, char boardChar, int i, int j) {
		BoardCell current = grid[i][j];
		if (boardChar == 'W') {
			// Checks above, below, left and right if it is a valid space add it to our temp
			// set of valid spaces
			upAdd(tempSet, i, j);
			downAdd(tempSet, i, j);
			leftAdd(tempSet, i, j);
			rightAdd(tempSet, i, j);
			// update the valid spaces list from our temp set
			adjStore.put(current, tempSet);
		} 
		else {
			// if it is a room the temp set should contain nothing
			if (current.isRoom()) adjStore.put(current, tempSet);
			else if (current.isDoorway()) {
				DoorDirection my_door = current.getDoorDirection();
				// if the space is a doorway then add the corresponding exit to the set
				switch (my_door) {
				case UP:
					// adds UP
					BoardCell up = grid[i - 1][j];
					tempSet.add(up);
					break;
				case DOWN:
					// adds DOWN
					BoardCell down = grid[i + 1][j];
					tempSet.add(down);
					break;
				case LEFT:
					// adds LEFT
					BoardCell left = grid[i][j - 1];
					tempSet.add(left);
					break;
				case RIGHT:
					// adds RIGHT
					BoardCell right = grid[i][j + 1];
					tempSet.add(right);
					break;
				default:
					System.out.println("error in door");
					break;
				}
				adjStore.put(current, tempSet);
			}
		}
	}
	
	// Adds the cell above the current cell
	private void upAdd(Set<BoardCell> tempSet, int i, int j) {
		if (i - 1 >= 0) {
			// Checks UP
			BoardCell up = grid[i - 1][j];
			if (up.getInitial() == 'W' || (up.isDoorway() && up.getDoorDirection() == DoorDirection.DOWN)) {
				tempSet.add(up);
			}
		}
	}
	
	// Adds the cell below the current cell
	private void downAdd(Set<BoardCell> tempSet, int i, int j) {
		if (i + 1 < numRows) {
			// Checks DOWN
			BoardCell down = grid[i + 1][j];
			if (down.getInitial() == 'W'
					|| (down.isDoorway() && down.getDoorDirection() == DoorDirection.UP)) {
				tempSet.add(down);
			}
		}
	}
	
	// Adds the cell to the left of the current cell
	private void leftAdd(Set<BoardCell> tempSet, int i, int j) {
		if (j - 1 >= 0) {
			// Checks LEFT
			BoardCell left = grid[i][j - 1];
			if (left.getInitial() == 'W'
					|| (left.isDoorway() && left.getDoorDirection() == DoorDirection.RIGHT)) {
				tempSet.add(left);
			}
		}
	}
	
	// Adds the cell to the right of the current cell
	private void rightAdd(Set<BoardCell> tempSet, int i, int j) {
		if (j + 1 < numCols) {
			// Checks RIGHT
			BoardCell right = grid[i][j + 1];
			if (right.getInitial() == 'W'
					|| (right.isDoorway() && right.getDoorDirection() == DoorDirection.LEFT)) {
				tempSet.add(right);
			}
		}
	}

	// Calculates which board spaces make valid targets using recursive function
	public void calcTargets(int row, int column, int pathLength) {
		// Resets target list for subsequent calls
		targets.clear();
		// Set<BoardCell> visited = new HashSet<BoardCell>();
		BoardCell visited = new BoardCell();
		BoardCell current = grid[row][column];
		int pathTraverse = pathLength;
		pathGen(pathLength, pathTraverse, grid[row][column], visited, current);
	}

	// Performs a DFS on the board to get each possible target
	private void pathGen(int pathLength, int pathTraverse, BoardCell start, BoardCell visited, BoardCell current) {
		// Base case, reached at the end of each path. Adds the cell at the end of the path
		// to the targets set and resets everything else
		if (pathTraverse == 0) {
			pathTraverse = pathLength;
			if (current != visited && current != start)
				targets.add(current);
			current = start;
			return;
		}

		// Recursive case; when there are still unvisited paths, visit them
		// Move to all unvisited adjacent locations
		for (BoardCell adjCell : adjStore.get(current)) {
			if (visited != adjCell && adjCell != start) {
				pathGen(pathLength, pathTraverse - 1, start, current, adjCell);
				if (adjCell.isDoorway())
					targets.add(adjCell);
			}
		}
	}

	// Loads the board information from the input files and the adjacency
	// information
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
			calcAdjacencies();
			createSolution();
			createPlayers();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}

	// Reads in legend file to a map
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		// Initialize
		FileReader roomIn = new FileReader(roomConfigFile);
		Scanner scanner = new Scanner(roomIn);

		// loop through and parse by ", "
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String arr[] = line.split(", ");

			// add save to variable for easier modification
			char symbol = arr[0].charAt(0);
			String roomName = arr[1];
			String type = arr[2];

			// add into legend
			legend.put(symbol, roomName);

			// add into type
			types.add(type);

			if (!type.equals("Card") && !type.contentEquals("Other")) {
				throw new BadConfigFormatException("Error: Unsupported format in file " + roomConfigFile);
			}
		}
		// clean up
		scanner.close();
	}

	// Reads in the board itself from a CSV file
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		// Initialize
		FileReader file = new FileReader(boardConfigFile);
		Scanner dimFinder = new Scanner(file);

		// Find dimensions of the board
		String tempRow = dimFinder.nextLine();
		String arr[] = tempRow.split(",");
		this.numCols = arr.length;
		this.numRows++;

		while (dimFinder.hasNextLine()) {
			tempRow = dimFinder.nextLine();
			arr = tempRow.split(",");
			if (arr.length != numCols) {
				throw new BadConfigFormatException("Error: Board dimensions do not match required format");
			}
			this.numRows++;
		}

		dimFinder.close();
		
		FileReader file2 = new FileReader(boardConfigFile);
		Scanner scanner = new Scanner(file2);

		BoardCell[][] setterGrid = new BoardCell[numRows][numCols];
		int count = 0;

		// go until end of file
		while (scanner.hasNextLine()) {
			// read in line and parse into array
			String line = scanner.nextLine();
			arr = line.split(",");

			// go through array and add the correct index into the setter grid
			for (int i = 0; i < arr.length; i++) {
				char initial = 0;
				char door = ' ';

				initial = arr[i].charAt(0);
				// if there is a case with 2 chars such as KD for kitchen down
				if (arr[i].length() == 2) {
					door = arr[i].charAt(1);
				}
				if (!legend.containsKey(initial)) {
					throw new BadConfigFormatException("Error: Board does not contain key " + initial);
				}
				// index correct l and add
				BoardCell tempBoardCell = new BoardCell(count, i, initial, door);
				setterGrid[count][i] = tempBoardCell;
			}
			count++;
		}
		// set grid and clean up
		grid = setterGrid;
		scanner.close();
	}
	
	//makes the solution to the game
	public void createSolution() {
		my_deck.initialize();
		my_deck.shuffle(my_deck.getWeaponDeck());
		my_deck.shuffle(my_deck.getPlayerDeck());
		my_deck.shuffle(my_deck.getRoomDeck());
		my_solution.weapon = my_deck.getWeaponDeck().get(0).getCardName();
		my_solution.person = my_deck.getPlayerDeck().get(0).getCardName();
		my_solution.room = my_deck.getRoomDeck().get(0).getCardName();
	}	
	
	// Gets the players loaded in from the deck class
	public void createPlayers() {
		my_deck.dealCards();
		for(int i = 0; i < my_deck.getPlayers().size(); i++) {
			String name;
			Color color;
			int row, col;
			ArrayList<Card> cards;
			ArrayList<Player> players = my_deck.getPlayers();
			name = players.get(i).getName();
			color = players.get(i).getColor();
			row = players.get(i).getRow();
			col = players.get(i).getColumn();
			cards = players.get(i).getCards();
			if(i == 0) {
				Player human = new HumanPlayer(name, color, row, col, cards);
				my_players.add(human);
				continue;
			}
			Player CPU = new ComputerPlayer(name, color, row, col, cards);
			my_players.add(CPU);
		}
	}
	
	// Calls the functions necessary for a player's turn to happen
	public boolean turn(int i, boolean stillPlayerTurn) {
		playerCounter = i;

		// If the player is a human, prevent the turn from advancing unless they choose a target
		if (playerCounter == 0) {
			// If next player has been pressed but it's still the player's turn, don't update anything
			if (!stillPlayerTurn) {
				dieRoll = my_players.get(playerCounter).rollDie();
				calcTargets(my_players.get(playerCounter).getRow(), my_players.get(playerCounter).getColumn(), dieRoll);
			}
			return playerTurnOver;
		}
		
		playerTurnOver = false;
		dieRoll = my_players.get(playerCounter).rollDie();
		calcTargets(my_players.get(playerCounter).getRow(), my_players.get(playerCounter).getColumn(), dieRoll);
		guessResponse = my_players.get(playerCounter).makeMove(targets);
		
		return true;
	}
	
	// Gets the position of the mouse clicks
	@Override
	public void mouseClicked(MouseEvent e) {
		// If it's the human player's turn, check where they click on the board
		if (playerCounter == 0) {
			X = e.getX();
			X /= DIM_X;
			Y = e.getY();
			Y /= DIM_Y;
			boolean targetTest = false;
			BoardCell destination = new BoardCell();
			
			// Causes error window to appear if the player chooses an invalid target
			for (BoardCell target : targets) {
				if (target.getRow() == Y && target.getColumn() == X) {
					targetTest = true;
					destination = target;
					break;
				}
			}
			// If the player chooses a space that isn't a valid target, display this splash window
			if (!targetTest) {
				JFrame window = new JFrame();
				window.setPreferredSize(new Dimension(200, 525));
				JOptionPane splash = new JOptionPane();
				JOptionPane.showMessageDialog(window, "Please choose a valid space to move to!", 
						"Invalid Space", JOptionPane.INFORMATION_MESSAGE);
				splash.setVisible(true);
			}
			// Otherwise, advance the turn like normal
			else {
				my_players.get(0).setRow(destination.getRow());
				my_players.get(0).setColumn(destination.getColumn());
				playerTurnOver = true;
			}
		}
		repaint();
	}

	// Instance method, makes sure there is only one instance of board
	// Also provides a global access point to the board
	public static Board getInstance() {return instance;}
	
	// Returns list of targets
	public Set<BoardCell> getTargets() {return targets;}

	// Returns row number
	public int getNumRows() {return numRows;}

	// Returns col number
	public int getNumColumns() {return numCols;}
	
	//returns Solution
	public Solution getSolution() {return my_solution;}
	
	//returns the Deck
	public Decks getDecks() {return my_deck;}

	// Returns adjacency list for one BoardCell
	public Set<BoardCell> getAdjList(int row, int col) {
		if (adjStore.containsKey(grid[row][col]))
			return adjStore.get(grid[row][col]);
		else
			return null;
	}

	// Returns a single cell if it's within range, if not, returns null
	public BoardCell getCellAt(int row, int column) {
		if (row >= 0 && row < numRows && column >= 0 && column < numCols) {
			return grid[row][column];
		} else
			return null;
	}
	
	public int getDieRoll() {
		return dieRoll;
	}

	public Map<Character, String> getLegend() {return legend;}
	
	// Gets the list of players
	public ArrayList<Player> getMy_players() {
		return my_players;
	}

	public void setConfigFiles(String string, String string2) {
		this.boardConfigFile = string;
		this.roomConfigFile = string2;
	}
	

	public void setMy_players(ArrayList<Player> my_players) {
		this.my_players = my_players;
	}
	
	public void setCurrentGuess(Solution currentGuess) {
		this.currentGuess = currentGuess;
	}

	public Solution getCurrentGuess() {
		return currentGuess;
	}

	public Card getGuessResponse() {
		return guessResponse;
	}


	// Unimplemented mouse listener methods
	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
