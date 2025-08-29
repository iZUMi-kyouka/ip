/** Represents a `delete` command. */
public class DeleteCommand extends Command {

    private final TaskList tasks;
    private final int taskNo;

    /** Creates a DeleteCommand with given a TaskList and a task number. */
    public DeleteCommand(TaskList tasks, int taskNo) {
        this.tasks = tasks;
        this.taskNo = taskNo;
    }

    @Override
    public void perform() {
        this.tasks.remove(this.taskNo);
    }
}
