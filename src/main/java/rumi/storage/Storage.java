package rumi.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import rumi.task.Deadline;
import rumi.task.Event;
import rumi.task.Task;
import rumi.task.TaskList;
import rumi.task.ToDo;

/**
 * Handles persistence of tasks
 */
public class Storage {

    private final String saveFileName;

    /**
     * Constructs a Storage instance which saves tasks to and loads them from the file specified in
     * the path saveFileName.
     */
    public Storage(String saveFilePath) {
        assert saveFilePath != null && !saveFilePath.isEmpty() && !saveFilePath.isBlank();
        this.saveFileName = saveFilePath;
    }

    /**
     * Attempts to load the tasks from the .rumi_data file. If this fails, an empty TaskList is
     * returned.
     */
    public TaskList loadTasks() {
        TaskList tasks = new TaskList();
        try (Scanner sc = new Scanner(new File(this.saveFileName))) {
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
                        default -> System.out.println("[WARN] Encountered unknown task format.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.printf("[WARN] %s\n", e);
                } catch (NullPointerException e) {
                    System.out.printf("[WARN] Potential Rumi data file corruption: %s\n", e);
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
    public void saveTasks(List<Task> tasks) {
        try (PrintWriter saveFile = new PrintWriter(this.saveFileName)) {
            tasks.forEach(task -> saveFile.println(task.toSerialisedString()));
        } catch (IOException e) {
            System.out.printf("[ERROR] Failed to save task: %s\n", e);
        }
    }
}
