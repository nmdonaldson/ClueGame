package clueGame;

import java.util.Set;
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

public class Board {
	private int numRows;
	private int numCols;
	public final static int MAX_BOARD_SIZE = 50;
	private BoardCell[][] grid;
	private Map<BoardCell, Set<BoardCell>> adjStore;
	private Set<BoardCell> targets;
	private Map<Character, String> legend;
	private Set<String> types;
	private String boardConfigFile;
	private String roomConfigFile;
	private static Board instance = new Board();

	// Constructor, private to ensure only one is created
	private Board() {
		super();
		this.adjStore = new HashMap<BoardCell, Set<BoardCell>>();
		this.targets = new HashSet<BoardCell>();
		this.legend = new HashMap<Character, String>();
		this.types = new HashSet<String>();
		this.grid = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	}
	
	
	// Calculates adjacency list for each grid cell and stores into map adjStore
	public void calcAdjacencies() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Set<BoardCell> tempSet = new HashSet<BoardCell>();
				char boardChar = grid[i][j].getInitial();
				switch(boardChar){
					case 'W':
						if (i - 1 >= 0) {
							if (grid[i-1][j].getInitial() == 'W' || (grid[i-1][j].isDoorway() && grid[i-1][j].getDoorDirection() == DoorDirection.DOWN)) {
								tempSet.add(grid[i - 1][j]);
							}
						}
						if (i + 1 < numRows) {
							if (grid[i+1][j].getInitial() == 'W' || (grid[i+1][j].isDoorway() && grid[i+1][j].getDoorDirection() == DoorDirection.UP)) {
								tempSet.add(grid[i + 1][j]);
							}
						}
						if (j - 1 >= 0) { 
							if (grid[i][j-1].getInitial() == 'W' || (grid[i][j-1].isDoorway() && grid[i][j-1].getDoorDirection() == DoorDirection.RIGHT)) {
								tempSet.add(grid[i][j - 1]);
							}
						}
						if (j + 1 < numCols) {
							if (grid[i][j+1].getInitial() == 'W' || (grid[i][j+1].isDoorway() && grid[i][j+1].getDoorDirection() == DoorDirection.LEFT)) {
								tempSet.add(grid[i][j + 1]);
							}
						} 
						adjStore.put(grid[i][j], tempSet);
						break;
						
					default:
						if(grid[i][j].isRoom()) {
							adjStore.put(grid[i][j], tempSet);
						}
						if(grid[i][j].isDoorway()) {
							DoorDirection my_door = grid[i][j].getDoorDirection();
							if(my_door == DoorDirection.UP) {tempSet.add(grid[i-1][j]);}
							else if(my_door == DoorDirection.DOWN) {tempSet.add(grid[i+1][j]);}
							else if(my_door == DoorDirection.LEFT) {tempSet.add(grid[i][j-1]);}
							else if(my_door == DoorDirection.RIGHT) {tempSet.add(grid[i][j+1]);}
							else {System.out.println("error in door");}
							adjStore.put(grid[i][j], tempSet);
						}
						break;
				}
			}
		}
	}
	
	// Calculates which board spaces make valid targets using recursive function
	public void calcTargets(int row, int column, int pathLength) {
		// Resets target list for subsequent calls
		targets.clear();
		//Set<BoardCell> visited = new HashSet<BoardCell>();
		BoardCell visited = new BoardCell();
		BoardCell current = grid[row][column];
		int pathTraverse = pathLength;
		pathGen(pathLength, pathTraverse, grid[row][column], visited, current);
	}
	
	// Recursively moves through each possible path available to the player
	void pathGen(int pathLength, int pathTraverse, BoardCell start, 
			BoardCell visited, BoardCell current) {
		// Base case, reached at the end of each path. Adds the cell at the end of the path
		// to the targets set and resets everything else 
		if (pathTraverse == 0) {
			pathTraverse = pathLength;
			if (current != visited && current != start) targets.add(current);
			current = start;
			return;
		}
		
		// Recursive case; when there are still unvisited paths, visit them
		// Move to all unvisited adjacent locations
		for (BoardCell adjCell: adjStore.get(current)) {
			if (visited != adjCell && adjCell != start) {
				pathGen(pathLength, pathTraverse - 1, start, current, adjCell);
				if (adjCell.isDoorway()) targets.add(adjCell);
		    }
	    }
	}

	// Loads the board information from the input files and the adjacency information
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
			calcAdjacencies();
		} 
		catch (FileNotFoundException | BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Reads in legend file to a map
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		//Initialize
		FileReader roomIn = new FileReader(roomConfigFile);		
		Scanner scanner = new Scanner(roomIn);

		//loop through and parse by ", "
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String arr[] = line.split(", ");

			// add save to variable for easier modification
			char symbol = arr[0].charAt(0);
			String roomName = arr[1];
			String type = arr[2];

			//add into legend
			legend.put(symbol, roomName);

			//add into type
			types.add(type);
			
			if (!type.equals("Card") && !type.contentEquals("Other")) {
				throw new BadConfigFormatException("Error: Unsupported format in file " + roomConfigFile);
			}
		}
		//clean up
		scanner.close();
	}
	
	// Reads in the board itself from a CSV file
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		//Initialize
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

		//go until end of file 
		while (scanner.hasNextLine()) {
			//read in line and parse into array
			String line = scanner.nextLine();
			arr = line.split(",");

			//go through array and add the correct index into the setter grid
			for (int i = 0; i < arr.length; i++) {
				char initial = 0;
				char door = ' ';
				
				initial = arr[i].charAt(0);
				//if there is a case with 2 chars such as KD for kitchen down
				if (arr[i].length() == 2) {
					door = arr[i].charAt(1);
				}
				if (!legend.containsKey(initial)) {
					throw new BadConfigFormatException("Error: Board does not contain key " + initial);
				}
				//index correct l and add
				BoardCell tempBoardCell = new BoardCell(count, i, initial, door);
				setterGrid[count][i] = tempBoardCell;
			}
			count++;
		}
		//set grid and clean up
		
		grid = setterGrid;
		scanner.close();
	}
	
	
	private int size(String[] arr) {
		// TODO Auto-generated method stub
		return 0;
	}

	// Instance method, makes sure there is only one instance of board
	// Also provides a global access point to the board
	public static Board getInstance() {
		return instance;
	}

	// Returns list of targets
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numCols;
	}

	// Returns adjacency list for one BoardCell
	public Set<BoardCell> getAdjList(int row, int col) {
		if (adjStore.containsKey(grid[row][col])) return adjStore.get(grid[row][col]);
		else return null;
	}

	// Returns a single cell if it's within range, if not, returns null
	public BoardCell getCellAt(int row, int column) {
		if (row >= 0 && row < numRows && column >= 0 && column < numCols) {
			return grid[row][column];
		}
		else return null;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}
	
	public void setConfigFiles(String string, String string2) {
		this.boardConfigFile = string;
		this.roomConfigFile = string2;
	}
}
