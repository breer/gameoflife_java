package de.andreasbreer.gameoflife.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import de.andreasbreer.util.StreamUtils;

/**
 * This class handles the properties of this application.
 * @author Andreas Breer
 */
public final class ApplicationProperties {

    /**
     * A logger for all kinds of info-messages, warnings and errors.
     */
    private static final Logger LOGGER = Logger.getRootLogger();

    /**
     * The path to the property file.
     */
    public final static String FILE_PATH = "gameoflife.properties";

    /**
     * The properties in the property file.
     */
    private Properties properties = new Properties();

    /**
     * The on instance of this class. (Singleton-Pattern)
     */
    private static volatile ApplicationProperties instance;

    /**
     * The private constructor. (Singleton-Pattern)
     */
    private ApplicationProperties() {
        loadProperties();
    }

    /**
     * This method returns the only instance of this class. (Singleton-Pattern)
     * @return he only instance of this class
     */
    public synchronized static ApplicationProperties getInstance() {
        if (instance == null) {
            instance = new ApplicationProperties();
        }
        return instance;
    }

    /**
     * This method load the property out of the property file.
     */
    private synchronized void loadProperties() {
        FileInputStream in = null;

        try {
            try {
                in = new FileInputStream(FILE_PATH);
                properties.load(in);
            } catch (IOException e) {
                // Could not load properties!
                LOGGER.error("Could not load properties!");
            }

        } finally {
            StreamUtils.safeCloseInputStream(in);
        }
    }

    /**
     * This method stores the property into the property file.
     */
    public synchronized void storeProperties() throws IOException {
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(FILE_PATH);
            properties.store(out, "");
        } finally {
            StreamUtils.safeCloseOutputStream(out);
        }
    }

    /**
     * This method return the value of a specific property identified by his
     * key.
     * @param key
     *            the specific key
     * @return the value of the property
     */
    public synchronized String getProperty(final PropertyName key) {
        return properties.getProperty(key.propertyKey);
    }

    /**
     * This method sets the value of a specific property identified by his key.
     * @param key
     *            the specific key
     * @param value
     *            the value of the property
     */
    public synchronized void setProperty(final PropertyName key,
            final String value) {
        properties.setProperty(key.propertyKey, value);
    }
}
