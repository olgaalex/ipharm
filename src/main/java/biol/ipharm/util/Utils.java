package biol.ipharm.util;

/**
 *
 * @author Olga
 */
// Noninstantiable utility class
public final class Utils {

    private Utils() {
        // Suppress default constructor for noninstantiability
        throw new AssertionError();
    }

    public static final int MAX_UPLOAD_FILE_SIZE = 1_572_864; // Approximately 1.5 megabytes
}
