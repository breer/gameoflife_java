package de.andreasbreer.gameoflife.controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import de.andreasbreer.gameoflife.exception.NoCellAtThisPositionException;
import de.andreasbreer.gameoflife.model.GameModel;
import de.andreasbreer.gameoflife.util.ApplicationProperties;
import de.andreasbreer.gameoflife.util.PropertyName;
import de.andreasbreer.gameoflife.view.FillWorldDialog;
import de.andreasbreer.gameoflife.view.GameView;
import de.andreasbreer.gameoflife.view.WorldDimensionDialog;

/**
 * This class controls the interaction with the user.
 * @author Andreas Breer
 */
public class GameController extends MouseAdapter implements ActionListener,
        ChangeListener {
	
    /**
     * A logger for all kinds of info-messages, warnings and errors.
     */
    private static final Logger LOGGER = Logger.getLogger(GameController.class);
    
    /**
     * The text of the start button.
     */
    private final static String TEXT_START_BUTTON = "Start";

    /**
     * The text of the stop button.
     */
    private final static String TEXT_STOP_BUTTON = "Stop";

    /**
     * The model to work on.
     */
    private GameModel model;

    /**
     * The view to work on.
     */
    private GameView view;

    /**
     * The constructor of this class using the fields model and view.
     * @param model
     *            the model to work on
     * @param view
     *            the model to work on
     */
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        initListeners();
    }

    /**
     * Initialize the listeners for the swing components.
     */
    private void initListeners() {
        view.getWorldPanel().addMouseListener(this);
        view.getControlPanel().getStartButton().addActionListener(this);
        view.getMenuFileClose().addActionListener(this);
        view.getMenuHelpAbout().addActionListener(this);
        view.getMenuWorldCear().addActionListener(this);
        view.getMenuWorldFill().addActionListener(this);
        view.getMenuWorldDimension().addActionListener(this);
        view.getControlPanel().getFramesPerSecondSlider()
                .addChangeListener(this);
    }

    /**
     * This method handles the mouse click event.
     * @param event
     *            this calls holds all relevant information of this event.
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        Point p = view.getCellPositionFromMousePosition(event.getPoint());

        try {
            model.getWorld().toggleStateofCell(p.x, p.y);
        } catch (NoCellAtThisPositionException e) {
            LOGGER.warn(e.toString());
        }
    }

    /**
     * This method handles the button click event.
     * @param event
     *            this calls holds all relevant information of this event.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        // Menu item: file -> close
        if (event.getSource() == view.getMenuFileClose()) {
            ApplicationProperties properties =
                    ApplicationProperties.getInstance();
            properties.setProperty(PropertyName.WINDOW_HEIGHT,
                    String.format("%d", view.getHeight()));
            properties.setProperty(PropertyName.WINDOW_WIDTH,
                    String.format("%d", view.getWidth()));
            try {
                properties.storeProperties();
            } catch (IOException exception) {
            	LOGGER.info("Could not store properties!");
            }
            System.exit(0);
        }

        // Menu item: help -> about
        if (event.getSource() == view.getMenuHelpAbout()) {

        }

        // Menu item: world -> dimension
        if (event.getSource() == view.getMenuWorldDimension()) {
            stopGame();
            new WorldDimensionDialog(view, model.getWorld());
            view.getWorldPanel().resetDimenstion();
        }

        // Menu item: world -> clear
        if (event.getSource() == view.getMenuWorldCear()) {
            stopGame();
            model.getWorld().clear();
        }

        // Menu item: world -> fill
        if (event.getSource() == view.getMenuWorldFill()) {
            stopGame();
            new FillWorldDialog(view, model.getWorld());
        }

        // The start button at the control panel
        if (event.getSource() == view.getControlPanel().getStartButton()) {
            if (model.isRunnung()) {
                stopGame();
            } else {
                startGame();
            }
        }

    }

    /**
     * This method handles the slider event.
     * @param event
     *            this calls holds all relevant information of this event.
     */
    @Override
    public void stateChanged(ChangeEvent event) {
        int fps =
                (int) view.getControlPanel().getFramesPerSecondSlider()
                        .getValue();
        model.setFramesPerSecond(fps);
    }

    /**
     * Stop the game.
     */
    private void stopGame() {
        model.stopGame();
        view.getWorldPanel().setShowOldState(false);
        view.getControlPanel().getStartButton().setText(TEXT_START_BUTTON);
    }

    /**
     * Start the game.
     */
    private void startGame() {
        model.startGame();
        view.getWorldPanel().setShowOldState(true);
        view.getControlPanel().getStartButton().setText(TEXT_STOP_BUTTON);
    }


}
