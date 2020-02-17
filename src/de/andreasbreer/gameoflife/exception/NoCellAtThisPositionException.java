package de.andreasbreer.gameoflife.exception;

/**
 * Thrown to indicate that the world has been accessed with an illegal index.
 * @author breer
 */
public class NoCellAtThisPositionException extends Exception {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     * The cunstructor of this class.
     */
    public NoCellAtThisPositionException(int x, int y) {
        super(String.format("No Cell at position %d:%d", x, y));
    }

}
