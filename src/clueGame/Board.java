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
		this.grid = new BoardCell[numRows][numCols];
	}
	
	
	// Calculates adjacency list for each grid cell and stores into map adjStore
	public void calcAdjacencies() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Set<BoardCell> tempSet = new HashSet<BoardCell>();
				if (i - 1 >= 0) tempSet.add(grid[i - 1][j]);
				if (i + 1 < numRows) tempSet.add(grid[i + 1][j]);
				if (j - 1 >= 0) tempSet.add(grid[i][j - 1]);
				if (j + 1 < numCols) tempSet.add(grid[i][j + 1]);
				adjStore.put(grid[i][j], tempSet);
			}
		}
	}
	
	// Calculates which board spaces make valid targets
	public void calcTargets(BoardCell startCell, int pathLength) {
		int col = 0;
		// Calculates outer edge of the range and adds that to the targets
		while (pathLength > 0) {
			col = startCell.getColumn() - pathLength;
			for (int i = startCell.getRow(); i < startCell.getRow() + pathLength; i++) {
				if (i >= 0 && i < numRows && col >= 0 && col < numCols) targets.add(grid[i][col]);
				col++;
				
			}
			col = startCell.getColumn();
			for (int i = startCell.getRow() + pathLength; i > startCell.getRow(); i--) {
				if (i >= 0 && i < numRows && col >= 0 && col < numCols) targets.add(grid[i][col]);
				col++;
				
			}
			col = startCell.getColumn() + pathLength;
			for (int i = startCell.getRow(); i > startCell.getRow() - pathLength; i--) {
				if (i >= 0 && i < numRows && col >= 0 && col < numCols) targets.add(grid[i][col]);
				col--;
			}
			col = startCell.getColumn();
			for (int i = startCell.getRow() - pathLength; i < startCell.getRow(); i++) {
				if (i >= 0 && i < numRows && col >= 0 && col < numCols) targets.add(grid[i][col]);
				col--;
			}
			pathLength -= 2;
		}
	}

	// Loads the board information from the input files
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
		
		System.out.println(numCols);
		System.out.println(numRows);
		
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
				//index correctl and add
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
	public Set<BoardCell> getAdjList(BoardCell cell) {
		if (adjStore.containsKey(cell)) return adjStore.get(cell);
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
