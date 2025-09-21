package rumi.command;

/**
 * Exceptions related to Parser failure to parse unknown user command.
 */
public class UnknownUserCommandException extends IllegalArgumentException {

    public UnknownUserCommandException() {
        super("Unknown command.");
    }

}
