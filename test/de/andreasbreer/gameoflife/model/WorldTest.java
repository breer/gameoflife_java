package de.andreasbreer.gameoflife.model;

import org.junit.Before;
import org.junit.Test;

import de.andreasbreer.gameoflife.exception.NoCellAtThisPositionException;

import junit.framework.TestCase;

/**
 * Unit test for the world class.
 * @author Andreas Breer
 *
 */
public class WorldTest extends TestCase {
	
	/**
	 * The default value for the world width.
	 */
	private final static int DEFAULT_WORLD_WIDTH = 3;
	
	/**
	 * The default value for the world height.
	 */
	private final static int DEFAULT_WORLD_HEIGHT = 3;
	
	/**
	 * The default value for probability of living cells.
	 */
	private final static int DEFAULT_PROBABILITY = 30;
	
	/**
	 * The world object to test.
	 */
	private World world;
	
	/**
	 * Set up the world to test.
	 */
	@Before
	public void setUp() {
		world = new World(DEFAULT_WORLD_WIDTH, DEFAULT_WORLD_HEIGHT);
	}
	
	/**
	 * Test the game rules.
	 */
	@Test
	public void testGameRules() {
		for (int i = 0; i <= 8; i++) {
			try {
				world.clear();
				world.toggleStateofCell(1, 1);
				switch (i) {
					case 8: world.toggleStateofCell(0, 0); // Fall through
					case 7: world.toggleStateofCell(0, 1); // Fall through
					case 6: world.toggleStateofCell(0, 2); // Fall through
					case 5: world.toggleStateofCell(1, 0); // Fall through
					case 4: world.toggleStateofCell(1, 2); // Fall through
					case 3: world.toggleStateofCell(2, 0); // Fall through
					case 2: world.toggleStateofCell(2, 1); // Fall through
					case 1: world.toggleStateofCell(2, 2); // Fall through
				}
				
				world.nextGeneration();
				switch (i) {
					case 8: // Fall through
					case 7: // Fall through
					case 6: // Fall through
					case 5: // Fall through
					case 4: assertFalse(world.isCellAlive(1, 1)); break; // Fall through
					case 3: assertTrue(world.isCellAlive(1, 1)); break;
					case 2: assertTrue(world.isCellAlive(1, 1)); break;
					case 1: // Fall through
					case 0: assertFalse(world.isCellAlive(1, 1));
				}	
			} catch (NoCellAtThisPositionException e) {
				fail();
			}
		}
	}
	
	
	/**
	 * Test the equal method.
	 */
	@Test
	public void testEqualWorlds() {
		world.random(DEFAULT_PROBABILITY);
		
		World anotherWorld = new World(world);
		assertTrue(world.equals(anotherWorld));
		
		try {
			world.toggleStateofCell(1, 1);
			world.toggleStateofCell(1, 2);
		} catch (NoCellAtThisPositionException e) {
			fail();
		}
		
		assertFalse(world.equals(anotherWorld));
	}

}
