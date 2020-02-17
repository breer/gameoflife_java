package de.andreasbreer.util;

/**
 * This class provides utility's to check if two objects are equal.
 * @author Andreas Breer
 */
public class EqualsUtils {

    /**
     * This method checks if two optional objects are equal.
     * @param obj1
     *            the first object.
     * @param obj2
     *            the second object.
     * @return true if the objects are equal, else false.
     */
    public static boolean nullSafeEquals(final Object obj1, final Object obj2) {
        return (obj1 == obj2) || (obj1 != null && obj1.equals(obj2));
    }

    /**
     * Checks whether the double value within a specified accuracy is the same.
     * @param value
     *            the value to check
     * @param expected
     *            the expected value
     * @param epsilon
     *            the difference that is acceptable
     * @return if the value corresponds the expected value +/- epsilon
     */
    public static boolean isEqualWithinPrecision(final double value,
            final double expected, final double epsilon) {
        return (value > expected - epsilon && value < expected + epsilon);
    }

}