package rumi.command;

import rumi.Rumi;
import rumi.task.Task;
import rumi.task.TaskList;
import rumi.ui.Ui;
import rumi.utils.Assert;

/**
 * Represents a command to delete a task.
 */
public class DeleteCommand extends Command {

    private final TaskList tasks;
    private final Ui ui;
    private final int taskNo;

    /**
     * Creates a DeleteCommand with given a TaskList, Uu, and a task number string.
     */
    public DeleteCommand(TaskList tasks, Ui ui, String taskNoStr) {
        Assert.notNull(tasks, ui, taskNoStr);

        this.tasks = tasks;
        this.ui = ui;
        this.taskNo = Integer.parseInt(taskNoStr);
    }

    @Override
    public void run() {
        if (taskNo > tasks.size() || taskNo <= 0) {
            this.ui.printResponse(Rumi.RESPONSE_UNKNOWN_TASK);
            return;
        }

        Task task = tasks.remove(taskNo);
        this.ui.printResponsef(
                "Roger, Master! I've deleted this from your to-do list:\n"
                        + "    %s\nYou now have %d task(s) awaiting your attention~",
                task, tasks.size());
    }

    @Override
    public CommandType getType() {
        return CommandType.DELETE;
    }
}
