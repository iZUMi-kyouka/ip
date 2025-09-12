package rumi.command;

import rumi.Rumi;
import rumi.task.Task;
import rumi.task.TaskList;
import rumi.ui.Ui;

/** Represents an `unmark` command. */
public class UnmarkCommand extends Command {

    private final TaskList tasks;
    private final Ui ui;
    private final int taskNo;

    /**
     * Creates a UnmarkCommand with given a TaskList and a task number.
     */
    public UnmarkCommand(TaskList tasks, Ui ui, String taskNoStr) {
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
        task.unmarkAsDone();
        this.ui.printResponsef("Understood, Master. I've marked this task as not done yet~\n"
                + "    ✘ %s\nLet me know when it’s done!", task);
    }

    @Override
    public CommandType getType() {
        return CommandType.UNMARK;
    }
}
