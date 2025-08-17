public class Utils {
    public static void printIndent(String s) {
        printIndent(s, 1);
    }

    public static void printIndent(String s, int tabCount) {
        String tabs = "\t".repeat(Math.max(0, tabCount));
        StringBuilder indentedString = new StringBuilder();
        String[] lines = s.split("\n");

        for (String line : lines) {
            indentedString.append(tabs).append(line).append("\n");
        }

        System.out.println(indentedString);
    }
}
