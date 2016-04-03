package quiz.utils;

/**
 * Utilities Class
 *
 * @author Javatlacati
 */
public class Util {

    /**
     * Workaround for mutiple varargs.
     *
     * @param <T> Data Type
     * @param myVararg variable arguments
     * @return A vararg!
     */
    public static <T> T[] varargs(final T... myVararg) {
        return myVararg;
    }
}
