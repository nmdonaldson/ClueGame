package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import clueGame.BoardCell;

import clueGame.Board;

public class BoardAdjTargetTests {
	private static Board board;
	
	// Setup
	@BeforeClass
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("Board.csv", "ClueRooms.txt");
		board.initialize();
	}
	
	// Tests locations with only adjacent walkways
	// Purple
	@Test
	public void testWalkways() {
		// Walkway surrounded by walkways
		Set<BoardCell> testList = board.getAdjList(16, 5);
		assertTrue(testList.contains(board.getCellAt(15, 5)));
		assertTrue(testList.contains(board.getCellAt(17, 5)));
		assertTrue(testList.contains(board.getCellAt(16, 4)));
		assertTrue(testList.contains(board.getCellAt(16, 6)));
		assertEquals(testList.size(), 4);
		
		// Left edge walkway, two adjacent cells
		testList = board.getAdjList(15, 0);
		assertTrue(testList.contains(board.getCellAt(16, 0)));
		assertTrue(testList.contains(board.getCellAt(15, 1)));
		assertEquals(testList.size(), 2);
		
		// Right edge walkway, one adjacent cell
		testList = board.getAdjList(14, 23);
		assertTrue(testList.contains(board.getCellAt(14, 22)));
		assertEquals(testList.size(), 1);
		
		// Top edge of walkway, two adjacent cells
		testList = board.getAdjList(0, 4);
		assertTrue(testList.contains(board.getCellAt(0, 5)));
		assertTrue(testList.contains(board.getCellAt(1, 4)));
		assertEquals(testList.size(), 2);
		
		// Bottom edge of map, two adjacent cells
		testList = board.getAdjList(21, 8);
		assertTrue(testList.contains(board.getCellAt(21, 7)));
		assertTrue(testList.contains(board.getCellAt(20, 8)));
		assertEquals(testList.size(), 2);
		
		// Walkway sandwiched between two rooms
		testList = board.getAdjList(11, 7);
		assertTrue(testList.contains(board.getCellAt(10, 7)));
		assertTrue(testList.contains(board.getCellAt(12, 7)));
		assertEquals(testList.size(), 2);
		
		// Walkway by doors it cannot enter
		testList = board.getAdjList(8, 0);
		assertTrue(testList.contains(board.getCellAt(9, 0)));
		assertEquals(testList.size(), 1);
	}

	// Tests cells within rooms, making sure they have no adjacent cells
	// Orange
	@Test
	public void testRooms() {
		// Top corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		
		// Wall and Walkway
		testList = board.getAdjList(0, 10);
		assertEquals(0, testList.size());
		
		// Walkway and Doorway
		testList = board.getAdjList(3, 18);
		assertEquals(0, testList.size());
		
		// Walkway and Doorway corner
		testList = board.getAdjList(5, 16);
		assertEquals(0, testList.size());
		
		// Walkway only
		testList = board.getAdjList(15, 19);
		assertEquals(0, testList.size());
		
		// Center of room
		testList = board.getAdjList(19, 12);
		assertEquals(0, testList.size());
	}
	
	// Tests doorways for their exit potential
	// Dark purple
	@Test
	public void testRoomExits() {
		// Down
		Set<BoardCell> testList = board.getAdjList(8, 2);
		assertTrue(testList.contains(board.getCellAt(9, 2)));
		assertEquals(testList.size(), 1);
		
		// Up
		testList = board.getAdjList(10, 5);
		assertTrue(testList.contains(board.getCellAt(9, 5)));
		assertEquals(testList.size(), 1);
			
		// Right
		testList = board.getAdjList(21, 6);
		assertTrue(testList.contains(board.getCellAt(21, 7)));
		assertEquals(testList.size(), 1);
		
		// Left
		testList = board.getAdjList(2, 6);
		assertTrue(testList.contains(board.getCellAt(2, 5)));
		assertEquals(testList.size(), 1);
			
		// Down surrounded by rooms
		testList = board.getAdjList(5, 15);
		assertTrue(testList.contains(board.getCellAt(6, 15)));
		assertEquals(testList.size(), 1);
		
		// Left corner
		testList = board.getAdjList(15, 17);
		assertTrue(testList.contains(board.getCellAt(15, 16)));
		assertEquals(testList.size(), 1);
	}
	
	// Testing valid walkways next to doorways
	// Green
	@Test
	public void testDoorEntrances() {
		// Down
		Set<BoardCell> testList = board.getAdjList(9, 2);
		assertTrue(testList.contains(board.getCellAt(8, 2)));
		assertTrue(testList.contains(board.getCellAt(9, 3)));
		assertTrue(testList.contains(board.getCellAt(9, 1)));
		assertEquals(testList.size(), 3);
		
		// Up
		testList = board.getAdjList(9, 5);
		assertTrue(testList.contains(board.getCellAt(10, 5)));
		assertTrue(testList.contains(board.getCellAt(9, 6)));
		assertTrue(testList.contains(board.getCellAt(9, 4)));
		assertTrue(testList.contains(board.getCellAt(8, 5)));
		assertEquals(testList.size(), 4);
			
		// Right
		testList = board.getAdjList(21, 7);
		assertTrue(testList.contains(board.getCellAt(21, 6)));
		assertTrue(testList.contains(board.getCellAt(21, 8)));
		assertTrue(testList.contains(board.getCellAt(20, 7)));
		assertEquals(testList.size(), 3);
		
		// Left
		testList = board.getAdjList(2, 5);
		assertTrue(testList.contains(board.getCellAt(2, 6)));
		assertTrue(testList.contains(board.getCellAt(2, 4)));
		assertTrue(testList.contains(board.getCellAt(3, 5)));
		assertTrue(testList.contains(board.getCellAt(1, 5)));
		assertEquals(testList.size(), 4);
			
		// Down surrounded by rooms
		testList = board.getAdjList(6, 15);
		assertTrue(testList.contains(board.getCellAt(5, 15)));
		assertTrue(testList.contains(board.getCellAt(7, 15)));
		assertTrue(testList.contains(board.getCellAt(6, 14)));
		assertTrue(testList.contains(board.getCellAt(6, 16)));
		assertEquals(testList.size(), 4);
		
		// Left corner
		testList = board.getAdjList(15, 16);
		assertTrue(testList.contains(board.getCellAt(15, 17)));
		assertTrue(testList.contains(board.getCellAt(14, 16)));
		assertTrue(testList.contains(board.getCellAt(16, 16)));
		assertEquals(testList.size(), 3);
	}
	
	// Test Doorway orientation for various cells
	// White
	@Test
	public void testDorwayOrientation() {
		// Cell within a room
		Set<BoardCell> testList = board.getAdjList(4,1);
		assertEquals(0, testList.size());
		
		// Down facing doorway
		testList = board.getAdjList(8, 1);
		assertEquals(testList.size(), 1);
		assertTrue(testList.contains(board.getCellAt(9, 1)));
		
		// Random Walkway
		testList = board.getAdjList(6, 6);
		assertEquals(testList.size(), 3);
		assertFalse(board.getCellAt(6, 6).isDoorway());
		
		// Right facing doorway
		testList = board.getAdjList(20, 6);
		assertEquals(testList.size(), 1);
		assertTrue(testList.contains(board.getCellAt(20, 7)));
		
		// Up facing Doorway
		testList = board.getAdjList(16, 12);
		assertEquals(testList.size(), 1);
		assertTrue(testList.contains(board.getCellAt(15, 12)));
		
		// Left facing doorway
		testList = board.getAdjList(9, 19);
		assertEquals(testList.size(), 1);
		assertTrue(testList.contains(board.getCellAt(9, 18)));
	}
	
	
	// Target testing on walkways, 1 tile away
	// Light blue
	@Test
	public void targTest_1() {
		// Start tile is in a corner
		board.calcTargets(18, 3, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 4)));
		assertTrue(targets.contains(board.getCellAt(17, 3)));
		
		// This tile is a door
		board.calcTargets(4, 18, 1);
		targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 18)));
	}
	
	// Target testing on walkways, 3 tiles away. Also tests getting into a room.
	// Light blue
	@Test
	public void targTest_3() {
		board.calcTargets(4, 11, 3);
		Set<BoardCell> targets = board.getTargets();
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 11)));
		assertTrue(targets.contains(board.getCellAt(6, 12)));
		assertTrue(targets.contains(board.getCellAt(7, 11)));
		assertTrue(targets.contains(board.getCellAt(6, 10)));
	
		// Near a door that is 3 spaces away
		board.calcTargets(12, 18, 3);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(9, 18)));
		// Door space
		assertTrue(targets.contains(board.getCellAt(10, 19)));
		assertTrue(targets.contains(board.getCellAt(13, 16)));
		assertTrue(targets.contains(board.getCellAt(14, 17)));
		assertTrue(targets.contains(board.getCellAt(14, 19)));
	}
	
	// Target testing on walkways, 5 tiles away
	@Test
	public void targTest_5() {
		// Start tile next to the edge of the map and surrounded by rooms
		board.calcTargets(5, 23, 5);
		Set<BoardCell> targets = board.getTargets();
		targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 18)));
		assertTrue(targets.contains(board.getCellAt(6, 19)));
	}
	
	// Tests getting into a room without using all available steps
	@Test
	public void targTestShortIntoRoom() {
		board.calcTargets(12, 18, 4);
		Set<BoardCell> targets = board.getTargets();
		targets = board.getTargets();

		assertEquals(9, targets.size());
		// Directly above
		assertTrue(targets.contains(board.getCellAt(8, 18)));
		// Short doorway entrance
		assertTrue(targets.contains(board.getCellAt(10, 19)));
		// Other doorway (not short)
		assertTrue(targets.contains(board.getCellAt(9, 19)));
		// Everything else
		assertTrue(targets.contains(board.getCellAt(14, 18)));
		assertTrue(targets.contains(board.getCellAt(14, 20)));
		assertTrue(targets.contains(board.getCellAt(14, 16)));
		assertTrue(targets.contains(board.getCellAt(13, 15)));
		assertTrue(targets.contains(board.getCellAt(13, 17)));
		assertTrue(targets.contains(board.getCellAt(13, 19)));
	}
	
	// Tests leaving a room
	@Test
	public void targTestExit() {
		// One step
		board.calcTargets(4, 18, 1);
		Set<BoardCell> targets = board.getTargets();
		targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 18)));
		
		// Two steps
		board.calcTargets(10, 19, 2);
		targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(9, 18)));
		assertTrue(targets.contains(board.getCellAt(11, 18)));
	}
}
