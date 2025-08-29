/** Represents a `list` command. */
public class ListCommand extends Command {
    /** Creates a ListCommand with the given runnable. */
    public ListCommand(Runnable f) {
        super();
        this.action = f;
    }
}
