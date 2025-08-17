import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static String getTaskListString() {
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
                printResponse(String.format("You have %d tasks, Master.\n%s", taskNo, getTaskListString()));
            } else if (command.matches("mark\\s+-?\\d+")) {
                int taskNo = Integer.parseInt(command.split(" ")[1]);
                if (taskNo > Rumi.taskNo || taskNo <= 0) {
                    printResponse("Oops! Rumi does not of know of such task :(");
                    command = scanner.nextLine();
                    continue;
                }

                Task task = tasks[taskNo - 1];
                task.markAsDone();
                printResponse(String.format("Got it! Rumi has marked this task as done: \n\t%s", task));
            } else if (command.matches("unmark\\s+-?\\d+")) {
                int taskNo = Integer.parseInt(command.split(" ")[1]);
                if (taskNo > Rumi.taskNo || taskNo <= 0) {
                    printResponse("Oops! Rumi does not of know of such task :(");
                    command = scanner.nextLine();
                    continue;
                }

                Task task = tasks[taskNo - 1];
                task.unmarkAsDone();
                printResponse(String.format("Roger that! Rumi has marked this task as not done yet: \n\t%s", task));
            } else if (command.matches("todo\\s+(.+)")) {
                Pattern pattern = Pattern.compile("todo\\s+(.+)");
                Matcher matcher = pattern.matcher(command);

                if (matcher.matches()) {
                    String title = matcher.group(1);

                    ToDo todo = new ToDo(title);
                    tasks[taskNo] = todo;
                    taskNo++;

                    printResponse(String.format(
                        "Okay! I've added this task: \n\t%s\n" +
                        "Now you have %d tasks in the list.", todo, taskNo));
                }
            } else if (command.matches("deadline\\s+(.+?)\\s+/by\\s+(.+)")) {
                Pattern pattern = Pattern.compile("deadline\\s+(.+?)\\s+/by\\s+(.+)");
                Matcher matcher = pattern.matcher(command);

                if (matcher.matches()) {
                    String title = matcher.group(1);
                    String dueDate = matcher.group(2);

                    Deadline deadline = new Deadline(title, dueDate);
                    tasks[taskNo] = deadline;
                    taskNo++;

                    printResponse(String.format(
                            "Okay! I've added this task: \n\t%s\n" +
                            "Now you have %d tasks in the list.", deadline, taskNo));
                }
            } else if (command.matches("event\\s+(.+?)\\s+/from\\s+(.+?)\\s+/to\\s+(.+)")) {
                Pattern pattern = Pattern.compile("event\\s+(.+?)\\s+/from\\s+(.+?)\\s+/to\\s+(.+)");
                Matcher matcher = pattern.matcher(command);

                if (matcher.matches()) {
                    String title = matcher.group(1);
                    String from = matcher.group(2);
                    String to = matcher.group(3);

                    Event event = new Event(title, from, to);
                    tasks[taskNo] = event;
                    taskNo++;

                    printResponse(String.format(
                            "Okay! I've added this task: \n\t%s\n" +
                            "Now you have %d tasks in the list.", event, taskNo));
                }
            } else {
                printResponse("Rumi does not understand, Master :(");
            }

            command = scanner.nextLine();
        }

        showGoodbyeMessage();
    }
}
