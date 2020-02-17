package de.andreasbreer.gameoflife.model;

import java.util.Random;

/**
 * This class represents one cell.
 * @author Andreas Breer
 */
public class Cell {

    /**
     * The current state of this cell. A cell can be dead, alive or
     * uninitialized.
     */
    private CellState currentState;

    /**
     * The new state of this cell. A cell can be dead, alive or uninitialized.
     */
    private CellState newState;

    /**
     * The constructor of this class using the field state.
     * @param state
     *            the state of this cell.
     */
    public Cell(CellState state) {
        super();
        currentState = state;
        newState = CellState.UNINITIALIZED;
    }

    /**
     * The copy constructor.
     * @param cell
     *            the cell to clone.
     */
    public Cell(Cell cell) {
        this.currentState = cell.currentState;
        this.newState = cell.newState;
    }

    /**
     * Get the current state of this cell.
     * @return the state of this cell
     */
    protected synchronized CellState getState() {
        return currentState;
    }

    /**
     * Get the current state of this cell.
     * @return the state of this cell
     */
    protected synchronized boolean isAlive() {
        return CellState.ALIVE.equals(getState());
    }

    /**
     * Toggle the current state of this cell.
     */
    protected synchronized void toggleState() {
        currentState =
                (CellState.ALIVE.equals(currentState)) ? CellState.DEAD
                        : CellState.ALIVE;
    }

    /**
     * Factory-Method for a random cell.
     * @param probability
     *            the probability for a living cell in percent.
     * @return a random cell
     */
    public static Cell createRandomCell(int probability) {
        Random random = new Random();
        return (random.nextFloat() * 100 >= probability) ? new Cell(
                CellState.DEAD) : new Cell(CellState.ALIVE);
    }

    /**
     * Factory-Method for a living cell.
     * @return a living cell
     */
    public static Cell createLivingCell() {
        return new Cell(CellState.ALIVE);
    }

    /**
     * Factory-Method for a dead cell.
     * @return a dead cell
     */
    public static Cell createDeadCell() {
        return new Cell(CellState.DEAD);
    }

    /**
     * Set the new state of this cell - alive.
     * @param state
     *            the state of this cell
     */
    protected synchronized void setState(CellState state) {
        newState = state;
    }

    /**
     * Set the new state of this cell - alive.
     */
    protected synchronized void awake() {
        newState = CellState.ALIVE;
    }

    /**
     * Set the new state of this cell - dead.
     */
    protected synchronized void die() {
        newState = CellState.DEAD;
    }

    /**
     * Set the new state of this cell = old state of this cell.
     */
    protected synchronized void solid() {
        newState = this.currentState;
    }

    /**
     * Activates the next generation of the cell.
     */
    protected synchronized void activateNextGeneration() {
        currentState = newState;
        newState = CellState.UNINITIALIZED;
    }
    
    /**
     * A implementation of the equals method for a cell object.
     * @param other 
     * 			the other object
     * @return true if the two objects are equal
     */
    @Override
    public boolean equals(Object other) {
    	// accept null value.
    	if (other == null) {
    		return false;
    	}
    	
    	// Check if the types are the same.
    	if (getClass() != other.getClass()) {
    		return false;
    	}
    	    	
    	// Now the casting is safely
    	final Cell otherCell = (Cell) other;  	
    	
    	// Check attributes.
    	return (this.currentState == otherCell.currentState) && (this.newState == otherCell.newState);
    }
    
    
    /**
     * Calculate a hash value for this cell.
     * @return the hash value
     */
    @Override
    public int hashCode() {
    	final int PRIME = 37;    	
    	
    	return this.currentState.hashCode() * PRIME + this.newState.hashCode();
    }
}
