package rumi.command;

import rumi.Rumi;
import rumi.task.Task;
import rumi.task.TaskList;
import rumi.ui.Ui;
import rumi.utils.Assert;

/** Represents a command to unmark a done task, reverting its status to pending. */
public class UnmarkCommand extends Command {

    private final TaskList tasks;
    private final Ui ui;
    private final int taskNo;

    /**
     * Creates a UnmarkCommand with given a TaskList and a task number.
     */
    public UnmarkCommand(TaskList tasks, Ui ui, String taskNoStr) {
        Assert.notNull(tasks, ui, taskNoStr);

        this.taskNo = Integer.parseInt(taskNoStr);
        this.tasks = tasks;
        this.ui = ui;
    }

    @Override
    public void run() {
        if (taskNo > tasks.size() || taskNo <= 0) {
            this.ui.printResponse(Rumi.UNKNOWN_TASK_RESPONSE);
            return;
        }

        Task task = tasks.get(taskNo);
        task.unmarkAsDone();
        this.ui.printResponsef("Understood, Master. I've marked this task as not done yet~\n"
                + "    ✘ %s\nLet me know when it’s done!", task);
    }

    @Override
    public CommandType getType() {
        return CommandType.UNMARK;
    }
}
