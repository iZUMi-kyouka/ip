package rumi.task;

import java.util.ArrayList;
import java.util.List;

/** Handles adding, removing, and representing the task list as a string. */
public class TaskList extends ArrayList<Task> {



    public TaskList() {
        super();
    }

    public TaskList(List<Task> list) {
        super(list);
    }

    /**
     * Adds a task to the list and returns the outcome.
     *
     * @param t the task to add
     * @return TaskListAddOutcome.OK if added successfully, TaskListAddOutcome.DUPLICATE if a task
     *         with the same title already exists
     */
    public TaskListAddOutcome addTask(Task t) {
        if (!this.findExact(t.getTitle()).isEmpty()) {
            super.add(t);
            return TaskListAddOutcome.DUPLICATE;
        }

        super.add(t);
        return TaskListAddOutcome.ADDED;
    }

    @Override
    public Task get(int taskNo) {
        assert taskNo >= 1;
        return super.get(taskNo - 1);
    }

    @Override
    public Task remove(int taskNo) {
        assert taskNo >= 1;
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

    /**
     * Finds the tasks with a title exactly matching the query.
     *
     * @param query the exact title to search for
     * @return the matching Task, or null if none found
     */
    public TaskList findExact(String query) {
        List<Task> results = this.stream().filter(task -> task.getTitle().equals(query)).toList();
        return new TaskList(results);
    }

    /**
     * Represents the possible outcomes of adding a task to a TaskList.
     */
    public enum TaskListAddOutcome {
        /** The task was added successfully. */
        ADDED,

        /** A task with the same title already exists in the list. */
        DUPLICATE
    }
}
