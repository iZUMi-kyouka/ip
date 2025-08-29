
/** Handles parsing of user commands. */
public abstract class Command {

    public static final Runnable NOOP = () -> {
    };

    protected Runnable action = NOOP;

    public void perform() {
        this.action.run();
    }
}
