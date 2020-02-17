package de.andreasbreer.gameoflife;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import de.andreasbreer.gameoflife.controller.GameController;
import de.andreasbreer.gameoflife.model.GameModel;
import de.andreasbreer.gameoflife.util.ApplicationProperties;
import de.andreasbreer.gameoflife.util.PropertyName;
import de.andreasbreer.gameoflife.view.GameView;

/**
 * This class starts the application.
 * @author Andreas Breer
 */
public class StartUp {
    /**
     * A logger for all kinds of info-messages, warnings and errors.
     */
    private static final Logger LOGGER = Logger.getLogger(StartUp.class);

    /**
     * The main method to start the application.
     * @param args
     *            not in use
     * @throws IOException
     */
    public static void main(String[] args) {  	
    	int worldWidth = 0;
        int worldHeight = 0;
    	int windowWidth = 0;
        int windowHeight = 0;
       	
        
        // Read the configuration and instantiate the logger.
        DOMConfigurator.configureAndWatch("log4j.xml");

        ApplicationProperties properties = ApplicationProperties.getInstance();

        GameModel model = null;
        
        try {
        	worldWidth =
                    Integer.parseInt(properties
                            .getProperty(PropertyName.WORLD_WIDTH));
        	worldHeight =
                    Integer.parseInt(properties
                            .getProperty(PropertyName.WORLD_HEIGHT));
        	model = new GameModel(worldWidth, worldHeight);
        } catch (NumberFormatException e) {
        	model = new GameModel();
        }
        
        GameView view = null;

        try {
            windowWidth =
                    Integer.parseInt(properties
                            .getProperty(PropertyName.WINDOW_WIDTH));
            windowHeight =
                    Integer.parseInt(properties
                            .getProperty(PropertyName.WINDOW_HEIGHT));
            view = new GameView(model, windowWidth, windowHeight);
        } catch (NumberFormatException e) {
            view = new GameView(model);
        }

        new GameController(model, view);

        LOGGER.info("The Application has started!");

        // What happens with uncaught Exceptions?
        Thread.setDefaultUncaughtExceptionHandler(new LoggingUncaughtExceptionHandler(
                LOGGER));
    }

}
