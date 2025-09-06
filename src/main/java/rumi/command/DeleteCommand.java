package rumi.command;
import rumi.task.Task;
import rumi.task.TaskList;
import rumi.ui.Ui;

/** Represents a `delete` command. */
public class DeleteCommand extends Command {

    private final TaskList tasks;
    private final Ui ui;
    private final int taskNo;

    /**
     * Creates a DeleteCommand with given a TaskList and a task number.
     */
    public DeleteCommand(TaskList tasks, Ui ui, String taskNoStr) {
        this.tasks = tasks;
        this.ui = ui;
        int taskNo = Integer.parseInt(taskNoStr);
        if (taskNo > tasks.size() || taskNo <= 0) {
            this.ui.printResponse(
                    "Forgive me, Master, but I cannot find such a task... Are you certain it exists?");
        }

        this.taskNo = taskNo;
    }

    @Override
    public void run() {
        Task task = tasks.remove(taskNo);
        this.ui.printResponse(String.format(
                "Roger, Master! I've deleted this from your to-do list:\n"
                + "    %s\nYou now have %d task(s) awaiting your attention~",
                task, tasks.size()));
    }

    @Override
    public CommandType getType() {
        return CommandType.DELETE;
    }
}
