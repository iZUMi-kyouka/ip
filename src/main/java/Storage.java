import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private final String FILENAME = ".rumi_data";

    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(".rumi_data"))) {
            while (sc.hasNextLine()) {
                String task = sc.nextLine();
                if (task.isEmpty()) {
                    continue;
                }

                try {
                    switch (task.charAt(0)) {
                        case 'E':
                            tasks.add(Event.fromString(task));
                            break;
                        case 'D':
                            tasks.add(Deadline.fromString(task));
                            break;
                        case 'T':
                            tasks.add(ToDo.fromString(task));
                            break;
                        default:
                            continue;
                    }

                } catch (IllegalArgumentException e) {
                    System.out.printf("[WARN] %s\n", e);
                }
            }
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }

        return tasks;
    }

    public static void saveTasks(List<Task> tasks) {
        try (PrintWriter saveFile = new PrintWriter(".rumi_data")) {
            tasks.forEach(task -> saveFile.println(task.toSerialisedString()));
        } catch (IOException e) {
            System.out.printf("[ERROR] Failed to save task: %s\n", e);
        }
    }
}
