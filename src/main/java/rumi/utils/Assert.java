package rumi.utils;

/**
 * Convenience abstractions for various assertions
 */
public class Assert {

    /**
     * Asserts that all objects given are not null.
     */
    public static void notNull(Object... o) {
        for (Object obj : o) {
            assert obj != null;
        }
    }

    /**
     * Asserts that all numbers given are positive
     */
    public static void positive(Number... n) {
        for (Number num : n) {
            assert num.doubleValue() > 0;
        }
    }

    /**
     * Asserts that all strings given are non null and non-empty.
     */
    public static void nonEmptyString(String... strings) {
        Assert.notNull((Object[]) strings);

        for (String s : strings) {
            assert !s.isEmpty();
        }
    }
}
