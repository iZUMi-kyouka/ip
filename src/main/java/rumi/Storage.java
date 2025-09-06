package rumi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

/**
 * Handles persistence of tasks
 */
public class Storage {

    private static final String SAVE_FILE_NAME = ".rumi_data";

    /**
     * Attempts to load the tasks from the .rumi_data file. If this fails, an empty TaskList is
     * returned.
     */
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

    /**
     * Attempts to save the tasks into the .rumi_data text file. If this fails, an error is shown.
     */
    public static void saveTasks(List<Task> tasks) {
        try (PrintWriter saveFile = new PrintWriter(SAVE_FILE_NAME)) {
            tasks.forEach(task -> saveFile.println(task.toSerialisedString()));
        } catch (IOException e) {
            System.out.printf("[ERROR] Failed to save task: %s\n", e);
        }
    }
}
