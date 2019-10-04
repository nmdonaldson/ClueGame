package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import experiment.BoardCell;
import experiment.IntBoard;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

class IntBoardTests {
	private IntBoard board;
	
	// Setup
	@Before
	public void beforeAll() {
		board = new IntBoard();
	}

	// Adjacency list tests
	@Test
	public void testAdjTopLeft() {
		BoardCell tile = board.getCell(0, 0);
		Set<BoardCell> testList = board.getAdjList(tile);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjBotRight() {
		BoardCell tile = board.getCell(3, 3);
		Set<BoardCell> testList = board.getAdjList(tile);
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjRightEdge() {
		BoardCell tile = board.getCell(1, 3);
		Set<BoardCell> testList = board.getAdjList(tile);
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjLeftEdge() {
		BoardCell tile = board.getCell(3, 0);
		Set<BoardCell> testList = board.getAdjList(tile);
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void test2ndColMiddle() {
		BoardCell tile = board.getCell(1, 1);
		Set<BoardCell> testList = board.getAdjList(tile);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertEquals(4, testList.size());
	}
	
	@Test
	public void testPenultimateColMiddle() {
		BoardCell tile = board.getCell(2, 2);
		Set<BoardCell> testList = board.getAdjList(tile);
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(4, testList.size());
	}
	
	// Target testing
	@Test
	public void testTargets0_3() {
		BoardCell tile = board.getCell(0, 0);
		board.calcTargets(tile, 3);
		Set<BoardCell> targs = board.getTargets();
		assertEquals(6, targs.size());
		assertTrue(targs.contains(board.getCell(3, 0)));
		assertTrue(targs.contains(board.getCell(2, 1)));
		assertTrue(targs.contains(board.getCell(0, 1)));
		assertTrue(targs.contains(board.getCell(1, 2)));
		assertTrue(targs.contains(board.getCell(0, 3)));
		assertTrue(targs.contains(board.getCell(1, 0)));
	}
	

	
}
