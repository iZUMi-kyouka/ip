import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String FILENAME = ".rumi_data";

    public ArrayList<Task> loadTasks() {
        try (Scanner sc = new Scanner(new File(".rumi_data"))) {
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
