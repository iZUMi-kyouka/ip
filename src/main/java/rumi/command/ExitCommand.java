package rumi.command;

/** Represents a command to exit the application. */
public class ExitCommand extends Command {

    @Override
    public CommandType getType() {
        return CommandType.EXIT;
    }
}
