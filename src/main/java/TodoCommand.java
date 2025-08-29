/** Represents a `todo` command. */
public class TodoCommand extends Command {

    private final TaskList tasks;
    private final String title;

    /** Creates a DeleteCommand with given a TaskList and a task number. */
    public TodoCommand(TaskList tasks, String title) {
        this.tasks = tasks;
        this.title = title;
    }

    @Override
    public void perform() {
        this.tasks.add(new ToDo(this.title));
    }
}
