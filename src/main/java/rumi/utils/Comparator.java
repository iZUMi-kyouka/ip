package rumi.utils;

public class Comparator {
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
