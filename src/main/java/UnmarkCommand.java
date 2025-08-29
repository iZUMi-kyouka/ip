/** Represents an `unmark` command. */
public class UnmarkCommand extends Command {

    private final TaskList tasks;
    private final int taskNo;

    /** Creates a UnmarkCommand with given a TaskList and a task number. */
    public UnmarkCommand(TaskList tasks, int taskNo) {
        this.tasks = tasks;
        this.taskNo = taskNo;
    }

    @Override
    public void perform() {
        this.tasks.get(this.taskNo).unmarkAsDone();
    }
}
