
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
        task.unmarkAsDone();
        this.ui.printResponse(String.format(
                "Understood, Master. I've marked this task as not done yet~\n"
                + "    ✘ %s\nLet me know when it’s done!",
                task));
    }

    @Override
    public CommandType getType() {
        return CommandType.UNMARK;
    }
}
