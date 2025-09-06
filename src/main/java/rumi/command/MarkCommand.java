package rumi.command;
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

        int taskNo = Integer.parseInt(taskNoStr);
        if (taskNo > tasks.size() || taskNo <= 0) {
            this.ui.printResponse(
                    "Forgive me, Master, but I cannot find such a task... Are you certain it exists?");
        }

        this.taskNo = taskNo;
    }

    @Override
    public void run() {
        Task task = tasks.get(taskNo - 1);
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
