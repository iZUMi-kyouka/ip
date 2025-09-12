package rumi.utils;

/**
 * Utilities for printing responses
 */
public class Utils {

    public static final String DIVIDER =
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";

    public static void printIndent(String s) {
        printIndent(s, 1);
    }

    /**
     * Prints the given string with the specified tab count
     */
    public static void printIndent(String s, int tabCount) {
        System.out.println(indentLines(s, tabCount));
    }

    /**
     * Indents each line of a string with the specified tab count
     */
    public static String indentLines(String s, int tabCount) {
        String tabs = "\t".repeat(Math.max(0, tabCount));
        StringBuilder indentedString = new StringBuilder();
        String[] lines = s.split("\n");

        for (int i = 0; i < lines.length; i++) {
            indentedString.append(tabs).append(lines[i]);
            if (i != lines.length - 1) {
                indentedString.append('\n');
            }
        }

        return indentedString.toString();
    }


    public static String boxText(String s) {
        return DIVIDER + "\n" + s + "\n" + DIVIDER;
    }
}
