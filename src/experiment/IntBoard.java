package experiment;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

public class IntBoard {
	private final static int ROWS = 4;
	private final static int COLS = 4;
	private BoardCell[][] grid;
	private HashMap<BoardCell, Set<BoardCell>> adjStore;
	private Set<BoardCell> targets;
	
	// Constructor
	public IntBoard() {
		super();
		this.adjStore = new HashMap<BoardCell, Set<BoardCell>>();
		this.grid = new BoardCell[ROWS][COLS];
		this.targets = new HashSet<BoardCell>();
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[i][j] = new BoardCell();
				grid[i][j].setRow(i);
				grid[i][j].setColumn(j);
			}
		}
		calcAdjacencies();
	}

	// Calculates adjacency list for each grid cell and stores into map adjStore
	public void calcAdjacencies() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				Set<BoardCell> tempSet = new HashSet<BoardCell>();
				if (i - 1 >= 0) tempSet.add(grid[i - 1][j]);
				if (i + 1 < ROWS) tempSet.add(grid[i + 1][j]);
				if (j - 1 >= 0) tempSet.add(grid[i][j - 1]);
				if (j + 1 < COLS) tempSet.add(grid[i][j + 1]);
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
				if (i >= 0 && i < ROWS && col >= 0 && col < COLS) targets.add(grid[i][col]);
				col++;
				
			}
			col = startCell.getColumn();
			for (int i = startCell.getRow() + pathLength; i > startCell.getRow(); i--) {
				if (i >= 0 && i < ROWS && col >= 0 && col < COLS) targets.add(grid[i][col]);
				col++;
				
			}
			col = startCell.getColumn() + pathLength;
			for (int i = startCell.getRow(); i > startCell.getRow() - pathLength; i--) {
				if (i >= 0 && i < ROWS && col >= 0 && col < COLS) targets.add(grid[i][col]);
				col--;
			}
			col = startCell.getColumn();
			for (int i = startCell.getRow() - pathLength; i < startCell.getRow(); i++) {
				if (i >= 0 && i < ROWS && col >= 0 && col < COLS) targets.add(grid[i][col]);
				col--;
			}
			pathLength -= 2;
		}
	}
	
	// Returns list of targets
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	// Returns adjacency list for one BoardCell
	public Set<BoardCell> getAdjList(BoardCell cell) {
		if (adjStore.containsKey(cell)) return adjStore.get(cell);
		else return null;
	}

	// Returns a single cell if it's within range, if not, returns null
	public BoardCell getCell(int row, int column) {
		if (row >= 0 && row < ROWS && column >= 0 && column < COLS) {
			return grid[row][column];
		}
		else return null;
	}
}
