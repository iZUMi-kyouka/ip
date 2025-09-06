package rumi.command;

/** Represents an exit command. */
public class ExitCommand extends Command {

    @Override
    public void run() {
    }

    @Override
    public CommandType getType() {
        return CommandType.EXIT;
    }
}
