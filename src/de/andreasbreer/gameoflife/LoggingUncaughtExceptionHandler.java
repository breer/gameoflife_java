package de.andreasbreer.gameoflife;

import java.lang.Thread.UncaughtExceptionHandler;

import org.apache.log4j.Logger;

/**
 * This class catches all uncaught exceptions.
 * 
 * @author Andreas Breer
 */
public class LoggingUncaughtExceptionHandler implements
		UncaughtExceptionHandler {

	/**
	 * A logger for all kinds of info-messages, warnings and errors.
	 */
	private final Logger logger;

	/**
	 * Default constructor of this class.
	 */
	public LoggingUncaughtExceptionHandler() {
		this(Logger.getLogger(LoggingUncaughtExceptionHandler.class));
	}

	/**
	 * Constructor using field logger.
	 * 
	 * @param logger
	 *            the logger to use.
	 */
	public LoggingUncaughtExceptionHandler(final Logger logger) {
		this.logger = logger;
	}

	/**
	 * Logging all uncaught exceptions.
	 */
	public void uncaughtException(final Thread thread, final Throwable throwable) {
		logger.error("Unexpecteed exception occured: ", throwable);
	}

}
