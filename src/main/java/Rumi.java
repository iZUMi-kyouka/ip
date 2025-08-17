import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Rumi {
    private static final String DIVIDER = "________________________________________________";
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

    private static void showIntroduction() {
        System.out.println(LOGO);
        System.out.println(DIVIDER);
        System.out.printf("Welcome back, master. %s at your service (๑˃ᴗ˂)ﻭ!\n", CHATBOT_NAME);
        System.out.println("What should I do?");
        System.out.println(DIVIDER);
        System.out.println("Goodbye master. Have a great day ahead!");
        System.out.println(DIVIDER);
    }

    private static void initialiseLogo() {
        try {
            LOGO = Files.readString(Paths.get("./src/logo.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGO = FALLBACK_LOGO;
        }
    }

    public static void main(String[] args) {
        initialiseLogo();
        showIntroduction();
    }
}
