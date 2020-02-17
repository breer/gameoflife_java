package de.andreasbreer.util;

/**
 * This class provides utility's to generate the hash value. The calculation
 * methods based on the book "Effective Java" from Joshua Bloch.
 * @author Andreas Breer
 */
public final class HashUtils {

    /**
     * A low prime number.
     */
    public static final int PRIME = 29;

    /**
     * Generated a hash value for an boolean value.
     * @param hash
     *            the already computed hash
     * @param input
     *            the new value.
     * @return the new hash.
     */
    public static final int calcHashCode(final int hash, final boolean input) {
        return PRIME * hash + (input ? 1 : 0);
    }

    /**
     * Generated a hash value for an integer value.
     * @param hash
     *            the already computed hash
     * @param input
     *            the new value.
     * @return the new hash.
     */
    public static final int calcHashCode(final int hash, final int input) {
        return PRIME * hash + input;
    }

    /**
     * Generated a hash value for an long value.
     * @param hash
     *            the already computed hash
     * @param input
     *            the new value.
     * @return the new hash.
     */
    public static final int calcHashCode(final int hash, final long input) {
        return PRIME * hash + (int) (input ^ (input >>> 32));
    }

    /**
     * Generated a hash value for an float value.
     * @param hash
     *            the already computed hash
     * @param input
     *            the new value.
     * @return the new hash.
     */
    public static final int calcHashCode(final int hash, final float input) {
        return calcHashCode(hash, Float.floatToIntBits(input));
    }

    /**
     * Generated a hash value for an double value.
     * @param hash
     *            the already computed hash
     * @param input
     *            the new value.
     * @return the new hash.
     */
    public static final int calcHashCode(final int hash, final double input) {
        return calcHashCode(hash, Double.doubleToLongBits(input));
    }

    /**
     * Generated a hash value for an boolean value.
     * @param hash
     *            the already computed hash
     * @param input
     *            the new value.
     * @return the new hash.
     */
    public static final int calcHashCode(final int hash, final Object input) {
        return (input == null) ? 0 : PRIME * hash + input.hashCode();
    }

}
