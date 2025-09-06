package rumi;

import java.util.ArrayList;
import java.util.List;

/** Handles adding, removing, and representing the task list as a string. */
public class TaskList extends ArrayList<Task> {

    public TaskList(List<Task> list) {
        super(list);
    }

    @Override
    public Task remove(int taskNo) {
        return super.remove(taskNo - 1);
    }

    @Override
    public String toString() {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < this.size(); i++) {
            list.append(String.format("%d. ", i + 1)).append(this.get(i));
            if (i < this.size() - 1) {
                list.append('\n');
            }
        }

        return list.toString();
    }

    /** Returns a TaskList containing the tasks with title that matches the query */
    public TaskList find(String query) {
        List<Task> results = this.stream().filter(task -> task.getTitle().contains(query)).toList();
        return new TaskList(results);
    }
}
