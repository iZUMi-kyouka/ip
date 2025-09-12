package rumi.command;

import rumi.task.TaskList;
import rumi.ui.Ui;

/** Represents a `list` command. */
public class ListCommand extends Command {

    private final Ui ui;
    private final TaskList tasks;

    /**
     * Creates a new 'list' command
     */
    public ListCommand(TaskList tasks, Ui ui) {
        this.tasks = tasks;
        this.ui = ui;
    }

    @Override
    public void run() {
        if (this.tasks.isEmpty()) {
            this.ui.printResponse("Oh no! You haven't given me any tasks yet, Master... "
                    + "Please do soon, I'm eager to serve you~!");
        } else {
            this.ui.printResponsef(
                    "You have entrusted me with %d task(s), Master~\n"
                            + "Here's the list, all neat and tidy just for you ♥.\n%s",
                    tasks.size(), tasks);
        }
    }

    @Override
    public CommandType getType() {
        return CommandType.LIST;
    }
}
