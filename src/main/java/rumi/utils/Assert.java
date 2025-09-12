package rumi.utils;

/** Convenience abstractions for various assertions */
public class Assert {

    /** Asserts that all objects given are not null. */
    public static void notNull(Object... o) {
        for (Object obj : o) {
            assert obj != null;
        }
    }

    /** Asserts that all numbers given are positive */
    public static void positive(Number... n) {
        for (Number num : n) {
            assert num.doubleValue() > 0;
        }
    }
}
