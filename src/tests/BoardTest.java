package tests;

import static org.junit.Assert.*;

import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class BoardTest {
	
	public static final int LEGEND_SIZE = 11;
	public static final int BOARD_COLUMNS = 24;
	public static final int BOARD_ROWS = 22;
	public static final int BOARD_DOORS = 14;
	
	private static Board board;
	
	
	// Sets up the board for use in testing
	@BeforeClass
	public static void setup() {
		board = Board.getInstance();
		board.setConfigFiles("Board.csv", "ClueRooms.txt");
		board.initialize();
	}

	// Tests each room to make sure they're using the correct symbols
	@Test
	public void testRooms() {
		Map<Character, String> legend = board.getLegend();
		
		assertEquals(LEGEND_SIZE, legend.size());
		
		// Tests several rooms unique to this board
		assertEquals("Foyer", legend.get('F'));
		assertEquals("Armory", legend.get('A'));
		assertEquals("Pool", legend.get('P'));
		assertEquals("Bathroom", legend.get('B'));
		assertEquals("Office", legend.get('O'));
		
		// Test last element in file
		assertEquals("Walkway", legend.get('W'));
		
		// Test first element in file
		assertEquals("Conservatory", legend.get('C'));
	}
	
	// Tests proper board dimensions
	@Test
	public void testDimensions() {
		assertEquals(BOARD_ROWS, board.getNumRows());
		assertEquals(BOARD_COLUMNS, board.getNumColumns());
	}
	
	// Tests proper doorway directions/placement
	@Test
	public void testDoorwayDirections() {
		// Tests various rooms and each possible door direction
		BoardCell room = board.getCellAt(8, 1);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(10, 5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		room = board.getCellAt(9, 19);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(21, 6);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		// Room cell that isn't a door
		room = board.getCellAt(3, 7);
		assertFalse(room.isDoorway());
		
		// Test that the walkway isn't a door
		BoardCell cell = board.getCellAt(9, 0);
		assertFalse(cell.isDoorway());
	}
	
	// Tests for correct number of doors
	@Test
	public void testDoorNumber() {
		int doorCount = 0;
		
		for (int row = 0; row < board.getNumRows(); row++) {
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway()) doorCount++;
			}
		}
		assertEquals(BOARD_DOORS, doorCount);
	}
	
	// Tests for the correct room initials
	@Test
	public void testInitials() {
		// Test various random cells within rooms
		assertEquals('O', board.getCellAt(19, 0).getInitial());
		assertEquals('F', board.getCellAt(19, 10).getInitial());
		assertEquals('P', board.getCellAt(19, 20).getInitial());
		assertEquals('A', board.getCellAt(5, 7).getInitial());
		assertEquals('S', board.getCellAt(5, 12).getInitial());
		
		// Test the walkway
		assertEquals('W', board.getCellAt(11, 7).getInitial());
		
		// Test the closet
		assertEquals('X', board.getCellAt(11, 8).getInitial());
	}
}
