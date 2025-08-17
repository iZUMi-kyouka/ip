import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Rumi {
    public static void main(String[] args) {
        final String CHATBOT_NAME = "Rumi";
        final String DIVIDER = "________________________________________________";

        String LOGO = null;
        try {
            LOGO = Files.readString(Paths.get("./src/logo.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGO = "\n" +
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
        }

        System.out.println(LOGO);
        System.out.println(DIVIDER);
        System.out.printf("Welcome back, master. %s at your service (๑˃ᴗ˂)ﻭ!\n", CHATBOT_NAME);
        System.out.println("What should I do?");
        System.out.println(DIVIDER);
        System.out.println("Goodbye master. Have a great day ahead!");
        System.out.println(DIVIDER);
    }
}
