/** Represents a `deadline` command. */
public class DeadlineCommand extends Command {

    private final TaskList tasks;
    private final String title;
    private final String dueDate;

    /** Creates a DeadlineCommand with given a TaskList and a task number. */
    public DeadlineCommand(TaskList tasks, String title, String dueDate) {
        this.tasks = tasks;
        this.title = title;
        this.dueDate = dueDate;
    }

    @Override
    public void perform() {
        this.tasks.add(new Deadline(this.title, this.dueDate));
    }
}
