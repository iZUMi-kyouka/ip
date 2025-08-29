/** Represents a `mark` command. */
public class MarkCommand extends Command {

    private final TaskList tasks;
    private final int taskNo;

    /** Creates a MarkCommand with given a TaskList and a task number. */
    public MarkCommand(TaskList tasks, int taskNo) {
        this.tasks = tasks;
        this.taskNo = taskNo;
    }

    @Override
    public void perform() {
        this.tasks.get(this.taskNo).markAsDone();
    }
}
