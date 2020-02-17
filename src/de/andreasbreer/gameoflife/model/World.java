package de.andreasbreer.gameoflife.model;

import java.util.Observable;

import de.andreasbreer.gameoflife.exception.NoCellAtThisPositionException;

/**
 * This class represents the world.
 * @author Andreas Breer
 */
public class World extends Observable {

    /**
     * An 2D-array holds the cells.
     */
    private Cell[][] cells;

    /**
     * The width of the word.
     */
    private int width;

    /**
     * The height of the word.
     */
    private int height;

    /**
     * The constructor of this class using the fields width and height, to
     * initialize the dimension of the world.
     * @param width
     *            the width of the world
     * @param height
     *            the height of the world
     */
    public World(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];

        // Initialize the world with cells.
        clear();
    }

    /**
     * The copy constructor.
     * @param world
     *            the world to clone
     */
    public World(World world) {
        super();
        this.width = world.width;
        this.height = world.height;
        cells = new Cell[width][height];

        // Clone all the cells
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.cells[x][y] = new Cell(world.cells[x][y]);
            }
        }
    }

    /**
     * This method fills the world with dead cells.
     */
    public synchronized void clear() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[x][y] = Cell.createDeadCell();
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * This method fills the world with a random state.
     * @param probability
     *            the probability for a living cell in percent.
     */
    public synchronized void random(int probability) {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[x][y] = Cell.createRandomCell(probability);
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Create a glider in this world.
     */
    public synchronized void setGlider() {
        clear();
        cells[1][0] = Cell.createLivingCell();
        cells[2][1] = Cell.createLivingCell();
        cells[0][2] = Cell.createLivingCell();
        cells[1][2] = Cell.createLivingCell();
        cells[2][2] = Cell.createLivingCell();
    }

    /**
     * Get the state of the cell at position x, y.
     * @param x
     *            the x-position of the cell
     * @param y
     *            the y-position of the cell
     * @return if the state of the cell at position x, y is ALIVE.
     */
    public boolean isCellAlive(int x, int y)
            throws NoCellAtThisPositionException {
        return getCell(x, y).isAlive();
    }

    /**
     * Get the state of the cell at position x, y.
     * @param x
     *            the x-position of the cell
     * @param y
     *            the y-position of the cell
     * @return the state of the cell at position x, y.
     */
    private Cell getCell(int x, int y) throws NoCellAtThisPositionException {
        if (x < 0 || x >= width) {
            throw new NoCellAtThisPositionException(x, y);
        }

        if ((y < 0) || (y >= height)) {
            throw new NoCellAtThisPositionException(x, y);
        }

        return cells[x][y];
    }

    /**
     * Get the height of this world.
     * @return the height of this world.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the width of this world.
     * @return the width of this world
     */
    public int getWidth() {
        return width;
    }

    /**
     * Calculates and activates the next generation of the cell.
     */
    public synchronized void nextGeneration() {
        calculateNextGeneration();
        activateNextGeneration();
        setChanged();
        notifyObservers();
    }

    /**
     * Calculate the next generation of the world.
     */
    private void calculateNextGeneration() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                switch (countAliveNeighborCells(x, y)) {
                case 2:
                    cells[x][y].solid();
                    break;
                case 3:
                    cells[x][y].awake();
                    break;
                // Ueberbevoelkerung und Einsamkeit
                default:
                    cells[x][y].die();
                    break;
                }
            }
        }
    }

    /**
     * Activates the next generation of the cell.
     */
    private void activateNextGeneration() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[x][y].activateNextGeneration();
            }
        }
    }

    /**
     * This method counts the neighbor cells which are alive.
     * @param x
     *            the x-position of the cell
     * @param y
     *            the y-position of the cell
     * @return the count of cells which are alive
     */
    private int countAliveNeighborCells(int x, int y) {
        int count = 0;
        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++) {
                int cellX = (x + i < 0) ? width - 1 : (x + i) % width;
                int cellY = (y + j < 0) ? height - 1 : (y + j) % height;
                if ((cellX != x) || (cellY != y)) {
                    count += (cells[cellX][cellY].isAlive()) ? 1 : 0;
                }
            }
        }

        return count;
    }

    /**
     * Toggle the state of the cell at x, y.
     * @param x
     *            the x position of the cell
     * @param y
     *            the y position of the cell
     * @throws NoCellAtThisPositionException
     */
    public synchronized void toggleStateofCell(int x, int y)
            throws NoCellAtThisPositionException {
        getCell(x, y).toggleState();
        setChanged();
        notifyObservers();
    }

    public synchronized void changeDimension(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        clear();
    }
    
    /**
     * A implementation of the equals method for a world object.
     * @param other 
     * 			the other world
     * @return true if the two worlds are equal
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
    	
    	// Call equals method of the base class.
    	//if (!super.equals(other)) {
    	//	return false;
    	//}
    	
    	// Now the casting is safely
    	final World otherWorld = (World) other;
    	
    	
    	// Check attributes.
    	return equalsImpl(otherWorld);
    }
    
    
    /**
     * Compares the attributes of this world with another world.
     * @param otherWorld 
     * 				the other world
     * @return true if the attributes of the two world are equal
     */
    private boolean equalsImpl(World otherWorld)
    {
    	if ((this.width != otherWorld.width) || (this.height != otherWorld.height)) {
    		return false;
    	}
    	
    	for (int y = 0; y < height; y++) {
    		for(int x = 0; x < width; x++) {
    			if (!this.cells[x][y].equals(otherWorld.cells[x][y])) {
    				return false;
    			}
    		}		
    	}
    	
    	return true;
    }
    
    /**
     * Calculate a hash value for this world.
     * @return the hash value
     */
    @Override
    public int hashCode() {
    	final int PRIME = 17;   
    	int hashCode = PRIME * this.width + this.height;
    	
    	for (int y = 0; y < height; y++) {
    		for(int x = 0; x < width; x++) {
    			hashCode = hashCode * PRIME + cells[x][y].hashCode();
    		}		
    	}
    	
    	return hashCode;
    }
}
