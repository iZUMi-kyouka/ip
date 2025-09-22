package rumi.utils;

/** A utility class containing various comparison logic. */
public class Comparator {
    /**
     * Takes in two list of objects, and returns true if each object in the same index in both list
     * are equal according to their own equals() method. Returns false if the list is of different
     * length.
     */
    public static boolean allEqual(Object[] os1, Object[] os2) {
        assert os1.length == os2.length;

        for (int i = 0; i < os1.length; i++) {
            if (!os1[i].equals(os2[i])) {
                return false;
            }
        }

        return true;
    }
}
