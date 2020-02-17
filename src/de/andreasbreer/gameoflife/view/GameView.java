package de.andreasbreer.gameoflife.view;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.andreasbreer.gameoflife.model.GameModel;

/**
 * The main frame of this application.
 * @author Andreas Breer.
 */
public class GameView extends JFrame {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     * The default value for the frame width.
     */
    private final static int DEFAULT_FRAME_WIDTH = 500;

    /**
     * The default value for the frame height.
     */
    private final static int DEFAULT_FRAME_HEIGHT = 500;

    /**
     * The model of the game.
     */
    private GameModel model;

    /**
     * The panel which draws the world.
     */
    private WorldPanel worldPanel;

    /**
     * The panel which holds the controls.
     */
    private ControlPanel controlPanel;

    /**
     * The menu bar at the top.
     */
    private JMenuBar menuBar;

    /**
     * The file menu.
     */
    private JMenu menuFile;

    /**
     * Menu item file -> close. This item closes the application.
     */
    private JMenuItem menuFileClose;

    /**
     * The world menu.
     */
    private JMenu menuWorld;

    /**
     * Menu item world -> dimension. This item changes a new dimension of the
     * world.
     */
    private JMenuItem menuWorldDimension;

    /**
     * Menu item world -> fill. This item fills the world with a random state.
     */
    private JMenuItem menuWorldFill;

    /**
     * Menu item world -> clear. This item fills the world with dead cells.
     */
    private JMenuItem menuWorldCear;

    /**
     * The help menu.
     */
    private JMenu menuHelp;

    /**
     * Menu item help -> about. This item show a about dialog.
     */
    private JMenuItem menuHelpAbout;

    /**
     * The constructor using the field world and the default dimension for the
     * frame.
     * @param model
     *            the world to play on.
     */
    public GameView(GameModel model) {
        this(model, DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
    }

    /**
     * The constructor using the fields world, frameWidth and frameHeight.
     * @param model
     *            the world to play on.
     * @param frameWidth
     *            the width of the frame.
     * @param frameHeight
     *            the height of the frame.
     */
    public GameView(GameModel model, int frameWidth, int frameHeight) {
        setLayout(new BorderLayout());

        this.model = model;

        initComponents();
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent event) {
                getWorldPanel().resetDimenstion();
            }
        });

        setSize(frameWidth, frameHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Initialize the swing components.
     */
    private void initComponents() {
        // Create the menu bar
        menuBar = new JMenuBar();

        // Create the file menu
        menuFile = new JMenu("Datei");
        menuFileClose = new JMenuItem("Beenden");
        menuFile.add(menuFileClose);
        menuBar.add(menuFile);

        // Create the world menu
        menuWorld = new JMenu("Welt");
        menuWorldDimension = new JMenuItem("Grösse ändern");
        menuWorld.add(menuWorldDimension);
        menuWorld.addSeparator();
        menuWorldFill = new JMenuItem("Zufällig füllen");
        menuWorld.add(menuWorldFill);
        menuWorldCear = new JMenuItem("Leeren");
        menuWorld.add(menuWorldCear);
        menuBar.add(menuWorld);

        // Create the help menu
        menuHelp = new JMenu("Hilfe");
        menuHelpAbout = new JMenuItem("Über Game of Life");
        menuHelp.add(menuHelpAbout);
        menuBar.add(menuHelp);

        add(menuBar, BorderLayout.NORTH);

        // Create the world panel
        worldPanel = new WorldPanel(model.getWorld());
        add(worldPanel, BorderLayout.CENTER);

        // Create the control panel
        controlPanel = new ControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * Delegate this method to the world panel.
     * @param p
     *            the x and y position of the cell
     * @return the cell at position x
     */
    public Point getCellPositionFromMousePosition(final Point p) {
        return worldPanel.getCellPositionFromMousePosition(p);
    }

    /**
     * Getter for the world panel.
     * @return the world panel
     */
    public WorldPanel getWorldPanel() {
        return worldPanel;
    }

    /**
     * Getter for the control panel.
     * @return the control panel
     */
    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    /**
     * Getter for the menu item: file -> close.
     * @return the menuFileClose
     */
    public JMenuItem getMenuFileClose() {
        return menuFileClose;
    }

    /**
     * Getter for the menu item: world -> fill.
     * @return the menuWorldFill
     */
    public JMenuItem getMenuWorldFill() {
        return menuWorldFill;
    }

    /**
     * Getter for the menu item: world -> clear.
     * @return the menuWorldCear
     */
    public JMenuItem getMenuWorldCear() {
        return menuWorldCear;
    }

    /**
     * Getter for the menu item: help -> about.
     * @return the menuHelpAbout
     */
    public JMenuItem getMenuHelpAbout() {
        return menuHelpAbout;
    }

    /**
     * Getter for the menu item: world -> dimension.
     * @return the menuWorldDimension
     */
    public JMenuItem getMenuWorldDimension() {
        return menuWorldDimension;
    }

}
