package rumi.command;

import rumi.task.TaskList;
import rumi.ui.Ui;
import rumi.utils.Assert;
import rumi.utils.Utils;

/** Represents a command to list all takss. */
public class ListCommand extends Command {

    private final Ui ui;
    private final TaskList tasks;

    /**
     * Creates a new 'list' command
     */
    public ListCommand(TaskList tasks, Ui ui) {
        Assert.notNull(tasks, ui);

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
                    tasks.size(), Utils.indentLines(tasks.toString(), 1));
        }
    }

    @Override
    public CommandType getType() {
        return CommandType.LIST;
    }
}
