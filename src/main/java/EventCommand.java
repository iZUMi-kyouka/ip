/** Represents an `event` command. */
public class EventCommand extends Command {

    private final TaskList tasks;
    private final String title;
    private final String from;
    private final String to;

    /** Creates a EventCommand with given a TaskList and a task number. */
    public EventCommand(TaskList tasks, String title, String from, String to) {
        this.tasks = tasks;
        this.title = title;
        this.from = from;
        this.to = to;
    }

    @Override
    public void perform() {
        this.tasks.add(new Event(this.title, this.from, this.to));
    }
}
