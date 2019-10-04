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
	private BoardCell[][] grid;
	private HashMap<BoardCell, Set<BoardCell>> adjStore;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	// Constructor
	public IntBoard() {
		super();
		adjStore = new HashMap<BoardCell, Set<BoardCell>>();
		calcAdjacencies();
	}

	// Calculates adjacency list for each grid cell and stores into map adjStore
	public void calcAdjacencies() {
		
	}
	
	// 
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	// Returns list of targets
	public HashSet<BoardCell> getTargets() {
		return null;
	}
	
	// Returns adjacency list for one BoardCell
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return null;
	}

	// Returns a single Cell
	public BoardCell getCell(int row, int column) {
		return null;
	}

}
