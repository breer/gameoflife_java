package de.andreasbreer.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtils {

    public static final void safeCloseInputStream(final InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                // ignore only io close exception.
            }
        }
    }

    public static final void safeCloseOutputStream(final OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                // ignore only io close exception.
            }
        }
    }

}
