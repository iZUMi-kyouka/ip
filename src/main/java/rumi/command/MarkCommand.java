package rumi.command;

import rumi.Rumi;
import rumi.task.Task;
import rumi.task.TaskList;
import rumi.ui.Ui;

/** Represents a `mark` command. */
public class MarkCommand extends Command {

    private final TaskList tasks;
    private final Ui ui;
    private final int taskNo;

    /**
     * Creates a MarkCommand with given a TaskList and a task number.
     */
    public MarkCommand(TaskList tasks, Ui ui, String taskNoStr) {
        this.tasks = tasks;
        this.ui = ui;
        taskNo = Integer.parseInt(taskNoStr);
    }

    @Override
    public void run() {
        if (taskNo > tasks.size() || taskNo <= 0) {
            this.ui.printResponse(Rumi.UNKNOWN_TASK_RESPONSE);
            return;
        }
        Task task = tasks.get(taskNo);
        task.markAsDone();
        this.ui.printResponse(String.format(
                "Wonderful! I've marked this task as complete, Master~\n    ✔ %s\nYou're doing amazing!",
                task));
    }

    @Override
    public CommandType getType() {
        return CommandType.MARK;
    }
}
