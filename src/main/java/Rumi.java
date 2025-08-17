import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Logger;

public class Rumi {
    private static final String DIVIDER = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";

    private static final String[] texts = new String[100];
    private static int textNo = 0;

    public static final String CHATBOT_NAME = "Rumi";
    public static final String FALLBACK_LOGO = "\n" +
        "\n" +
        "                                ____           \n" +
        "                              ,'  , `.  ,--,   \n" +
        "  __  ,-.         ,--,     ,-+-,.' _ |,--.'|   \n" +
        ",' ,'/ /|       ,'_ /|  ,-+-. ;   , |||  |,    \n" +
        "'  | |' |  .--. |  | : ,--.'|'   |  ||`--'_    \n" +
        "|  |   ,','_ /| :  . ||   |  ,', |  |,,' ,'|   \n" +
        "'  :  /  |  ' | |  . .|   | /  | |--' '  | |   \n" +
        "|  | '   |  | ' |  | ||   : |  | ,    |  | :   \n" +
        ";  : |   :  | : ;  ; ||   : |  |/     '  : |__ \n" +
        "|  , ;   '  :  `--'   \\   | |`-'      |  | '.'|\n" +
        " ---'    :  ,      .-./   ;/          ;  :    ;\n" +
        "          `--`----'   '---'           |  ,   / \n" +
        "                                       ---`-'  \n" +
        "\n";

    public static String LOGO = null;

    private static void printIndent(String s) {
        printIndent(s, 1);
    }

    private static void printIndent(String s, int tabCount) {
        String tabs = "\t".repeat(Math.max(0, tabCount));
        StringBuilder indentedString = new StringBuilder();
        String[] lines = s.split("\n");

        for (String line : lines) {
            indentedString.append(tabs).append(line).append("\n");
        }

        System.out.println(indentedString);
    }

    private static void showIntroduction() {
        System.out.println(LOGO);
        System.out.println(DIVIDER);
        System.out.printf("Welcome back, master. %s at your service (๑˃ᴗ˂)ﻭ!\n", CHATBOT_NAME);
        System.out.println("What would you like me to do, Master?");
        System.out.println(DIVIDER);
    }

    private static void showGoodbyeMessage() {
        System.out.println(DIVIDER);
        System.out.println("I'm happy to have served you today, Master. I shall be waiting for your homecoming.");
        System.out.println(DIVIDER);
    }

    private static void initialiseLogo() {
        try {
            LOGO = Files.readString(Paths.get("./src/logo.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGO = FALLBACK_LOGO;
        }
    }

    private static String getTextList() {
        if (textNo == 0) {
            return "Oops! You have not added anything yet.";
        }

        StringBuilder list = new StringBuilder();
        for (int i = 0; i < textNo; i++) {
            list.append(String.format("%d. ", i + 1)).append(texts[i]);
            if (i < textNo - 1) {
                list.append('\n');
            }
        }

        return list.toString();
    }

    public static void main(String[] args) {
        initialiseLogo();
        showIntroduction();

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        while (!command.equals("bye")) {
            if (command.equals("list")) {
                printIndent(DIVIDER + "\n" + getTextList() + "\n" + DIVIDER);
            } else {
                texts[textNo] = command;
                textNo++;
                printIndent(DIVIDER + "\n" + "added: " + command + "\n" + DIVIDER);
            }

            command = scanner.nextLine();
        }

        showGoodbyeMessage();
    }
}
