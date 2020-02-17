package de.andreasbreer.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class provides utility's to work with the file system.
 * @author Andreas Breer
 */
public class FileUtils {

    /**
     * Checks if the directory exists.
     * @param directoryToCheck
     *            the directory to check.
     * @return if the directory exists.
     */
    public static boolean directoryExists(final File directoryToCheck) {
        // handling null value
        if (directoryToCheck == null) {
            return false;
        }

        return directoryToCheck.exists() && directoryToCheck.isDirectory();
    }

    /**
     * Checks if the file exists.
     * @param fileToCheck
     *            the file to check.
     * @return if the file exists.
     */
    public static boolean fileExists(final File fileToCheck) {
        // handling null value
        if (fileToCheck == null) {
            return false;
        }

        return fileToCheck.exists() && fileToCheck.isFile();
    }

    /**
     * Returns the content of a directory as Array of Files.
     * @param directoryToCheck
     *            the directory to check.
     * @return the content of the directory.
     */
    public static List<File> getContent(final File directoryToCheck) {
        // checking if the directory exists
        if (directoryExists(directoryToCheck)) {

            // get the content of the directory
            final File[] content = directoryToCheck.listFiles();

            // handling null value
            if (content != null) {
                return Arrays.asList(content);
            }
        }

        // if there is no content the result is an array with length 0.
        return new ArrayList<File>();
    }

    /**
     * Creates the directory named by this abstract pathname, including any
     * necessary directories, if the directory does not already exists.
     * @param destinationFile
     *            the abstract pathname
     * @return false, when the file already exists
     */
    public static boolean ensurePathExists(final File destinationFile) {
        // NULL-Check
        if (destinationFile == null) {
            throw new IllegalArgumentException("Passed file must be not null!");
        }

        // If the file do not exist, create the directories.
        if (!destinationFile.exists()) {
            return destinationFile.mkdirs();
        }

        // If the file exits.
        return destinationFile.isDirectory();
    }

}
