public class Utils {
    public static final String DIVIDER = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";


    public static void printIndent(String s) {
        printIndent(s, 1);
    }

    public static void printIndent(String s, int tabCount) {
        String tabs = "\t".repeat(Math.max(0, tabCount));
        StringBuilder indentedString = new StringBuilder();
        String[] lines = s.split("\n");

        for (int i = 0; i < lines.length; i++) {
            indentedString.append(tabs).append(lines[i]);
            if (i != lines.length - 1) {
                indentedString.append('\n');
            }
        }

        System.out.println(indentedString);
    }

    public static String boxText(String s) {
        return DIVIDER + "\n" + s + "\n" + DIVIDER;
    }
}
