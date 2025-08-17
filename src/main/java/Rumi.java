import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Rumi {
    private static final Task[] tasks = new Task[100];
    private static int taskNo = 0;

    public static final String CHATBOT_NAME = "Rumi";
    public static final String FALLBACK_LOGO = """

                                            ____          \s
                                          ,'  , `.  ,--,  \s
              __  ,-.         ,--,     ,-+-,.' _ |,--.'|  \s
            ,' ,'/ /|       ,'_ /|  ,-+-. ;   , |||  |,   \s
            '  | |' |  .--. |  | : ,--.'|'   |  ||`--'_   \s
            |  |   ,','_ /| :  . ||   |  ,', |  |,,' ,'|  \s
            '  :  /  |  ' | |  . .|   | /  | |--' '  | |  \s
            |  | '   |  | ' |  | ||   : |  | ,    |  | :  \s
            ;  : |   :  | : ;  ; ||   : |  |/     '  : |__\s
            |  , ;   '  :  `--'   \\   | |`-'      |  | '.'|
             ---'    :  ,      .-./   ;/          ;  :    ;
                      `--`----'   '---'           |  ,   /\s
                                                   ---`-' \s
            
            """;

    public static String LOGO = null;

    private static void printResponse(String s) {
        Utils.printIndent(Utils.boxText(s));
    }

    private static void showIntroMessage() {
        String message = String.format(
            "Welcome back, master. %s at your service (๑˃ᴗ˂)ﻭ!\n" +
            "What would you like me to do, Master?", CHATBOT_NAME);
        System.out.println(LOGO);
        printResponse(message);
    }

    private static void showGoodbyeMessage() {
        printResponse("I'm happy to have served you today, Master. I shall be waiting for your homecoming.");
    }

    private static void initialiseLogo() {
        try {
            LOGO = Files.readString(Paths.get("./src/logo.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGO = FALLBACK_LOGO;
        }
    }

    private static String getTextList() {
        if (taskNo == 0) {
            return "Oops! You have not added anything yet.";
        }

        StringBuilder list = new StringBuilder();
        for (int i = 0; i < taskNo; i++) {
            list.append(String.format("%d. ", i + 1)).append(tasks[i]);
            if (i < taskNo - 1) {
                list.append('\n');
            }
        }

        return list.toString();
    }

    public static void main(String[] args) {
        initialiseLogo();
        showIntroMessage();

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        while (!command.equals("bye")) {
            if (command.equals("list")) {
                printResponse(getTextList());
            } else if (command.matches("mark \\d+")) {
                int taskNo = Integer.parseInt(command.split(" ")[1]);
                if (taskNo > Rumi.taskNo) {
                    printResponse("Oops! Rumi does not of know of such task :(");
                }

                Task task = tasks[Rumi.taskNo - 1];
                task.markAsDone();
                printResponse(String.format("Got it! Rumi has marked this task as done: \n\t%s", task));
            } else {
                tasks[taskNo] = new Task(command);
                taskNo++;
                printResponse("added: " + command);
            }

            command = scanner.nextLine();
        }

        showGoodbyeMessage();
    }
}
