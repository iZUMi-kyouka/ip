
/** Exceptions related to Parser failure to parse user command. */
public class UnknownUserCommandException extends IllegalArgumentException {

    public UnknownUserCommandException() {
        super("Unknown command.");
    }

}
