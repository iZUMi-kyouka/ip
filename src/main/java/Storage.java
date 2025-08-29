import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private static final String SAVE_FILE_NAME = ".rumi_data";

    public static TaskList loadTasks() {
        TaskList tasks = new TaskList();
        try (Scanner sc = new Scanner(new File(".rumi_data"))) {
            while (sc.hasNextLine()) {
                String task = sc.nextLine();
                if (task.isEmpty()) {
                    continue;
                }

                try {
                    switch (task.charAt(0)) {
                        case 'E' -> tasks.add(Event.fromString(task));
                        case 'D' -> tasks.add(Deadline.fromString(task));
                        case 'T' -> tasks.add(ToDo.fromString(task));
                        default -> {
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.out.printf("[WARN] %s\n", e);
                }
            }
        } catch (FileNotFoundException e) {
            return new TaskList();
        }

        return tasks;
    }

    public static void saveTasks(List<Task> tasks) {
        try (PrintWriter saveFile = new PrintWriter(SAVE_FILE_NAME)) {
            tasks.forEach(task -> saveFile.println(task.toSerialisedString()));
        } catch (IOException e) {
            System.out.printf("[ERROR] Failed to save task: %s\n", e);
        }
    }
}
