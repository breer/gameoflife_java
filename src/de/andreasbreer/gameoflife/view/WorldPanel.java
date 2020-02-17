package de.andreasbreer.gameoflife.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import de.andreasbreer.gameoflife.exception.NoCellAtThisPositionException;
import de.andreasbreer.gameoflife.model.World;

/**
 * This panel draws the world.
 * @author Andreas Breer
 */
public class WorldPanel extends JPanel implements Observer {

    /**
     * The default value for gap.
     */
    private static final int DEFAULT_GAP = 1;

    /**
     * Color of a living cell.
     */
    private static final Color COLOR_ALIVE = new Color(0, 255, 102);

    /**
     * Color of a living cell.
     */
    private static final Color COLOR_ALIVE_GLOW = COLOR_ALIVE.darker().darker()
            .darker();

    /**
     * Color of the border of a cell.
     */
    private static final Color COLOR_BORDER = Color.DARK_GRAY;

    /**
     * Color of the background.
     */
    private static final Color COLOR_BACKGROUND = Color.BLACK;

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     * The game model to draw.
     */
    private World world;

    /**
     * The old world.
     */
    private World oldWorld;

    /**
     * The gap between the cells.
     */
    private int gap;

    /**
     * The width of a cell.
     */
    private int cellWidth;

    /**
     * The height of a cell.
     */
    private int cellHeight;

    /**
     * The border of the panel.
     */
    private int borderX;

    /**
     * The border of the panel.
     */
    private int borderY;

    private boolean showOldState;

    /**
     * The constructor using the fields world and sets the default value for
     * gap.
     * @param gameWorld
     *            the world to draw
     */
    public WorldPanel(World gameWorld) {
        this(gameWorld, DEFAULT_GAP);
    }

    /**
     * The constructor using the fields world and gap.
     * @param gameWorld
     *            the world to draw
     * @param gap
     *            the gap between the cells
     */
    public WorldPanel(World gameWorld, int gap) {
        gameWorld.addObserver(this);
        this.world = gameWorld;
        this.oldWorld = new World(0, 0);
        this.gap = gap;
    }

    /**
     * Draw the world on the panel. Invoke by swing.
     */
    @Override
    public void paint(Graphics g) {
        // Clear the panel
        g.setColor(COLOR_BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw each cell on the panel.
        for (int y = 0; y < world.getHeight(); y++) {
            for (int x = 0; x < world.getWidth(); x++) {
                boolean cellState = false;
                boolean oldCellState = false;
                try {
                    cellState = world.isCellAlive(x, y);
                    oldCellState = oldWorld.isCellAlive(x, y);
                } catch (NoCellAtThisPositionException e) {

                }

                // Calculate the position of the cell.
                int posX = borderX + cellWidth * x;
                int posY = borderY + cellHeight * y;

                // Draw border
                g.setColor(COLOR_BORDER);
                g.drawRect(posX, posY, cellWidth, cellHeight);

                // Select the color and draw the cells.
                if (cellState) {
                    g.setColor(COLOR_ALIVE);
                    g.fillRect(posX + gap, posY + gap, cellWidth - 2 * gap,
                            cellHeight - 2 * gap);
                } else {
                    if (oldCellState && showOldState) {
                        g.setColor(COLOR_ALIVE_GLOW);
                        g.fillRect(posX + gap, posY + gap, cellWidth - 2 * gap,
                                cellHeight - 2 * gap);
                    }
                }
            }
        }

        // Save the old state for the glow.
        oldWorld = new World(world);
    }

    /**
     * Get the cell at position x.
     * @param p
     *            the x and y position to check
     * @return the x and y index of the cell
     */
    public Point getCellPositionFromMousePosition(final Point p) {
        return new Point((p.x - borderX) / cellWidth, (p.y - borderY)
                / cellHeight);
    }

    /**
     * Redraw the panel whenever the observed world object is changed.
     * @param o
     *            - the observable object.
     * @param obj
     *            - an argument passed to the notifyObservers method.
     */
    public void update(Observable o, Object obj) {
        repaint();
    }

    /**
     * Resets the dimension of the world panel
     */
    public void resetDimenstion() {
        // Calculate the dimension of a cell.
        cellWidth = getWidth() / world.getWidth();
        cellHeight = getHeight() / world.getHeight();

        // Decide which side is the smallest.
        if (cellWidth < cellHeight) {
            cellHeight = cellWidth;
        } else {
            cellWidth = cellHeight;
        }

        // Calculate the borders
        borderY = (getHeight() - cellHeight * world.getHeight()) / 2;
        borderX = (getWidth() - cellWidth * world.getWidth()) / 2;
    }

    /**
     * Setter for showOldState.
     * @param showOldState
     *            the showOldState to set
     */
    public void setShowOldState(boolean showOldState) {
        this.showOldState = showOldState;
        repaint();
    }

}
