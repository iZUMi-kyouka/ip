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
    public static final String LOGO =
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠿⠿⠛⠛⠛⠛⠻⠿⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠛⣉⠄⣢⣴⣟⣫⣤⣾⢿⣿⣷⡶⢦⣬⣉⠻⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⣡⡴⠋⢑⣬⣴⣿⣿⡻⣿⣿⣶⣝⠻⣿⣷⣾⣿⢿⣦⡉⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⣡⡾⠫⣠⣾⣿⣿⣿⣿⣿⣷⢹⣿⣿⣿⣷⡙⢿⣿⣿⣧⡐⡝⣦⡙⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⢃⣼⣿⠀⣼⣿⣿⣿⣿⣿⣿⣿⣿⣇⣿⣿⣿⣿⣿⡌⠙⢿⣿⣿⡐⣿⣷⠈⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠁⣹⢻⡟⡘⣿⠇⣿⣿⣿⣿⣿⣿⣿⡏⣿⠻⣿⣿⣿⣿⡌⢷⡉⢙⠀⠈⠀⡶⠈⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⠀⠐⠀⠾⢠⣷⣜⢸⣿⣿⡇⣿⣿⣿⣿⡇⢻⣧⢻⣿⣿⣿⣷⡀⡁⠀⠁⡁⣦⣄⠁⠘⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠀⢀⠑⣸⡦⣈⡻⠇⢨⢹⣿⡧⣿⣿⣿⣿⡇⣘⣿⡜⣿⣿⣿⢿⢇⠀⠀⣧⢱⡹⣷⡌⠂⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣿⣿⡟⢠⡄⡈⠻⣿⣿⣿⣿⣿⣿⠁⠌⢀⢇⡿⠀⣿⣿⡇⣦⣾⣿⣃⣿⣿⣿⣿⡇⠸⣟⢃⢛⣋⡴⠂⠎⡀⠘⣿⡌⣷⡘⣿⡄⠀⠘⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣿⡿⢀⠟⢜⣼⠀⣼⣿⣿⣿⣿⠇⠌⠀⣸⠸⡇⠀⡇⠟⣃⣿⣿⣿⢸⣿⢿⣶⣭⠁⡁⠹⡠⠌⢉⣬⣉⣀⡃⠀⠸⣷⢸⣧⡹⣷⡀⢧⠈⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣿⣷⡈⠲⠛⢉⠀⢻⣿⣿⣿⡟⠀⣼⠀⣿⢰⡇⠀⠀⣿⡇⣿⣿⡟⢨⡿⣸⣿⡟⢠⣿⣄⠁⠀⣿⣿⣿⣿⢰⠀⠀⢿⡆⢿⣷⡙⣷⡈⠀⢹⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡾⠛⠻⣾⣿⣿⣿⠃⢰⡏⠠⣿⠸⣧⠀⠀⠁⠁⠹⡿⠁⣼⠃⡟⠉⠀⠒⠈⠉⠁⠀⠛⣿⣿⣿⢸⢐⠲⠘⣿⠈⣻⣷⡌⢿⡄⢾⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣅⠀⢀⣿⣿⣿⣿⢀⡿⠁⠘⣿⢨⢻⡀⠀⢦⠂⠀⠠⣤⣥⣤⣦⣶⡆⠀⠀⠙⣿⡇⢀⣿⣿⣿⡏⢐⠳⡄⢿⡇⡟⡏⢻⣆⢈⠀⠙⠛⣛⠉⢸⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⣿⣿⣿⠸⠁⠀⡇⡟⠘⡘⣧⠀⢸⣇⠀⡀⢹⣿⣿⣿⣿⣧⠐⣸⠀⣽⠇⡏⣿⣿⡿⠇⢘⠵⠃⢸⡇⠃⠇⠈⡜⡄⠻⣶⣦⣤⣶⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠌⠛⠋⠀⠰⠀⠁⡇⠀⢧⠘⠧⡀⠿⢦⠡⣾⣿⣿⣿⣿⡿⢓⡿⠶⡟⠘⢰⣿⣿⢱⠀⠀⠀⠀⣼⠇⢰⠀⠀⢱⡀⢧⢹⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣶⣶⠟⠃⠀⠀⠀⠃⠀⠘⡀⠀⠀⠐⢄⣠⣿⣿⣿⣿⣿⣯⡐⣜⢂⡠⠂⣿⣿⡧⢸⠀⠀⠀⢠⠋⠀⠈⠀⠀⠘⠀⠈⢂⣿⣿⣿⣿⣿⣿⣿\n" +
            "⠿⠿⠿⠿⠿⠿⠿⠿⠿⠿⠿⠿⠿⠿⠿⠃⠆⠀⠸⠇⠀⠀⠀⠄⠥⠀⢀⠀⠀⠙⠛⠿⢶⣼⣿⡿⠿⠛⢉⣤⢰⣿⡿⠃⡇⠀⠀⠀⠀⠀⠀⠀⡴⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿\n" +
            "⠀⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣭⣭⣭⡒⡄⠂⠀⠀⠀⠀⠀⠀⡀⣀⠀⢰⣿⢃⣾⠿⠁⠀⠀⠀⠀⢀⠀⠀⠠⢄⠀⠀⢀⣜⣠⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣇⢹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡘⡀⠐⢂⣀⣤⣴⠊⡠⠴⠀⠉⠡⠛⠁⠀⠀⢠⡶⢀⡴⣄⡐⢶⡄⢠⡀⢺⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⡄⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⢡⠀⠹⢿⣿⢃⢰⣶⣥⣒⡶⠟⣓⣤⣤⡾⢋⠔⣩⣾⣿⣿⠖⣠⣾⠇⠸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣷⡈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡆⠂⠀⠀⠁⣌⢦⠙⢛⣣⣵⣾⣿⠿⢋⣐⣁⣨⣭⣭⣤⣤⣤⣤⣤⣬⣀⠙⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣧⠘⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣏⠻⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡈⠄⠀⠀⣫⡕⣬⣓⡲⣶⠖⠂⡄⠛⠉⠙⣿⣿⣿⡿⠿⠛⠋⠁⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣆⢹⣿⣿⣿⣿⣿⣿⣿⣿⣿⠏⠁⢰⡙⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⠐⠀⠀⣿⣿⠸⣿⠟⣱⣾⡆⢱⢠⢰⠈⠉⣀⣤⢠⣤⣤⠔⣠⣶⡀⠀⢀⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⡄⢻⣿⣿⣿⣿⣿⣿⣿⣿⣆⠀⠈⣷⡈⢛⣿⣿⣿⣿⣿⣿⣿⣿⣿⡆⠁⠈⢿⠿⠿⠈⢺⣿⣿⣷⠈⡌⢸⠀⣿⣿⠇⣾⡟⢁⠠⠤⠄⠠⢀⠸⣿⣿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⡈⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡐⡄⠰⢊⣀⣀⡛⠻⣿⡿⠀⡇⠆⠀⠿⠋⠀⠋⠀⠤⣴⣶⣶⢶⣤⠀⠘⢿⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣧⠘⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⢱⠀⠚⠉⣩⡶⠀⢀⠀⠀⢀⠀⠠⣀⣀⠀⠀⠀⢐⣄⠀⠀⠀⠈⢂⠀⢨⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⣿⣿⣿⣿⣿⣇⠸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣇⢧⠀⠖⣉⠤⠴⢂⣀⠈⡓⠦⠄⠀⠀⠀⠐⠤⣌⠛⠓⢄⠀⠀⠀⠀⠘⣿⣿⣿⣿⣿⣿⣿\n" +
            "⣿⠀⠤⠄⠀⢀⣀⣠⣨⣉⣉⣉⣉⣛⣛⡛⣛⢛⡛⠛⠛⠛⠛⠻⠿⠿⠿⠿⠿⠿⠿⠿⠜⡆⠈⠉⠀⠀⠙⠋⠠⠤⠐⠛⠀⠀⠀⠀⠀⠈⠳⠀⠀⠀⠀⠀⠀⠀⠛⠛⠛⠛⠛⠛⠻\n" +
            "⣿⣶⣦⣤⣤⣤⣤⣤⣄⣀⣀⣀⣈⣉⣉⡉⠉⠉⠉⠉⠛⠛⠛⠛⠛⠚⠓⠒⠒⠶⠖⠲⠦⠰⠶⠰⠂⠉⠉⠉⠛⠛⠓⠛⠛⠁⠀⣉⣁⣀⣀⣀⣀⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣼\n" +
            "⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣶⣶⣶⣶⣶⣶⣤⣤⣤⣤⣤⣤⣴⣶⣶⣶⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿";

    private static void printResponse(String s) {
        Utils.printIndent(Utils.boxText(s));
    }

    private static void showIntroMessage() {
        String message = String.format(
            "Welcome home, Master. %s at your service (๑˃ᴗ˂)ﻭ!\n" +
            "How may I assist you?", CHATBOT_NAME);
        System.out.println(LOGO);
        printResponse(message);
    }

    private static void showGoodbyeMessage() {
        printResponse(
        "Thank you for allowing me to serve you today, Master. " +
            "I shall await your return with great anticipation~");
    }

    private static String getTaskListString() {
        if (taskNo == 0) {
            return "Oh no! You haven't given me any tasks yet, Master... Please do soon, I'm eager to serve you~!\"";
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
        showIntroMessage();

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        while (!command.equals("bye")) {
            if (command.equals("list")) {
                printResponse(String.format(
                    "You have entrusted me with %d task(s), Master~\nHere's the list, all neat and tidy just for you ♥.\n%s",
                    taskNo, getTaskListString()));
            } else if (command.matches("mark\\s+-?\\d+")) {
                int taskNo = Integer.parseInt(command.split(" ")[1]);
                if (taskNo > Rumi.taskNo || taskNo <= 0) {
                    printResponse("Forgive me, Master, but I cannot find such a task... Are you certain it exists?");
                    command = scanner.nextLine();
                    continue;
                }

                Task task = tasks[taskNo - 1];
                task.markAsDone();
                printResponse(String.format(
                    "Wonderful! I've marked this task as complete, Master~\n    ✔ %s\nYou're doing amazing!",
                    task));

            } else if (command.matches("unmark\\s+-?\\d+")) {
                int taskNo = Integer.parseInt(command.split(" ")[1]);
                if (taskNo > Rumi.taskNo || taskNo <= 0) {
                    printResponse("Forgive me, Master, but I cannot find such a task... Are you certain it exists?");
                    command = scanner.nextLine();
                    continue;
                }

                Task task = tasks[taskNo - 1];
                task.unmarkAsDone();
                printResponse(String.format(
                    "Understood, Master. I've marked this task as not done yet~\n    ✘ %s\nLet me know when it’s done again!",
                    task));
            } else if (command.matches("delete\\s+-?\\d+")) {
                int taskNo = Integer.parseInt(command.split(" ")[1]);
                if (taskNo > Rumi.taskNo || taskNo <= 0) {
                    printResponse("Forgive me, Master, but I cannot find such a task... Are you certain it exists?");
                    command = scanner.nextLine();
                    continue;
                }

                Task task = tasks[taskNo - 1];
                task.unmarkAsDone();
                printResponse(String.format(
                        "Understood, Master. I've marked this task as not done yet~\n    ✘ %s\nLet me know when it’s done again!",
                        task));
            } else if (command.matches("todo\\s+(.+)")) {
                Pattern pattern = Pattern.compile("todo\\s+(.+)");
                Matcher matcher = pattern.matcher(command);

                if (matcher.matches()) {
                    String title = matcher.group(1);

                    ToDo todo = new ToDo(title);
                    tasks[taskNo] = todo;
                    taskNo++;

                    printResponse(String.format(
                        "Right away, Master! I've added this to your to-do list:\n    %s\nYou now have %d task(s) awaiting your attention~",
                        todo, taskNo));

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
                        "Right away, Master! I've added this to your to-do list:\n    %s\nYou now have %d task(s) awaiting your attention~",
                        deadline, taskNo));
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
                        "Noted! I've scheduled this delightful event for you, Master~\n  %s\n" +
                        "Everything is perfectly arranged~\nYou now have %d task(s)  in the list.",
                        event, taskNo));

                }
            } else {
                printResponse("Pardon my clumsiness, Master... I didn’t quite understand that (｡•́︿•̀｡)\nCould you please try again?");
            }

            command = scanner.nextLine();
        }

        showGoodbyeMessage();
    }
}
