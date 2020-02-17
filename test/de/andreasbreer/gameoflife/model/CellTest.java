package de.andreasbreer.gameoflife.model;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * Unit test for the cell class.
 * @author Andreas Breer
 *
 */
public class CellTest extends TestCase {
	
	/**
	 * The cell to test.
	 */
	private Cell cell;
	
	
	/**
	 * Set up the cell.
	 */
	@Before
	public void setUp() {
		cell = new Cell(CellState.DEAD);
	}
	
	/**
	 * Test the toggleState method of the cell object.
	 */
	@Test
	public void testToggleState() {
		assertEquals(CellState.DEAD, cell.getState());
		
		cell.toggleState();
		assertEquals(CellState.ALIVE, cell.getState());
		
		cell.toggleState();
		assertEquals(CellState.DEAD, cell.getState());
	}
	
	/**
	 * Test the activateNextGeneration method of the cell object.
	 */
	@Test
	public void testActivateNextGeneration() {
		cell.setState(CellState.ALIVE);
		assertEquals(CellState.DEAD, cell.getState());
		
		cell.activateNextGeneration();
		assertEquals(CellState.ALIVE, cell.getState());
	}
	
}
