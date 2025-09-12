package rumi.command;

import rumi.task.Event;
import rumi.task.TaskList;
import rumi.ui.Ui;
import rumi.utils.Assert;
import rumi.utils.Utils;

/**
 * Represents an `event` command.
 */
public class EventCommand extends Command {

    private final TaskList tasks;
    private final Ui ui;
    private final String title;
    private final String from;
    private final String to;

    /**
     * Creates a EventCommand with given a TaskList and a task number.
     */
    public EventCommand(TaskList tasks, Ui ui, String title, String from, String to) {
        Assert.notNull(tasks, ui, title, from, to);

        this.tasks = tasks;
        this.ui = ui;
        this.title = title;
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        Event event = new Event(title, from, to);
        switch (this.tasks.addTask(event)) {
        case ADDED -> this.ui.printResponsef(
                "Noted! I've scheduled this delightful event for you, Master~\n  %s\n"
                        + "Everything is perfectly arranged~\nYou now have %d task(s)  in the list.",
                event, tasks.size());
        case DUPLICATE -> this.ui.printResponsef(
                "The task that you've just added seems to be duplicates of the following tasks:\n"
                        + "%s\nYou now have %d task(s) awaiting your attention~",
                Utils.indentLines(this.tasks.findPossibleDuplicates(title, event).toString(), 1),
                tasks.size());
        default -> {
        }
        }
    }

    @Override
    public CommandType getType() {
        return CommandType.EVENT;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EventCommand)) {
            return false;
        }

        EventCommand command = (EventCommand) o;
        return command.title.equals(this.title) && command.from.equals(this.from)
                && command.to.equals(this.to);
    }
}
