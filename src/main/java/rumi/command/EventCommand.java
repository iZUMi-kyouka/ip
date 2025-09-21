package rumi.command;

import java.time.DateTimeException;
import java.util.ArrayList;

import rumi.exception.RumiException;
import rumi.tag.Tag;
import rumi.task.Event;
import rumi.task.TaskList;
import rumi.ui.Ui;
import rumi.utils.Assert;

/**
 * Represents a command to create an event.
 */
public class EventCommand extends TaskCommand {

    private final String from;
    private final String to;

    /**
     * Creates a EventCommand with the given TaskList, Ui, task title, and from and to datetime
     * string.
     */
    public EventCommand(TaskList tasks, Ui ui, String title, String from, String to) {
        this(tasks, ui, title, from, to, null);
    }

    /**
     * Creates a EventCommand with the given TaskList, Ui, task title, from and to datetime string,
     * and a tag list.
     */
    public EventCommand(TaskList tasks, Ui ui, String title, String from, String to,
            ArrayList<Tag> tags) {
        super(tasks, ui, title, tags);
        Assert.notNull(tasks, ui, title, from, to);

        this.from = from;
        this.to = to;
    }

    @Override
    public void run() throws RumiException {
        try {
            Event event = new Event(title, from, to, tags);
            TaskList.TaskListAddOutcome outcome = this.tasks.addTask(event);
            this.showOutcome(outcome, event);
        } catch (DateTimeException e) {
            throw RumiException.INVALID_DATE_EXCEPTION;
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

    @Override
    protected String getSuccessMessage() {
        return "Noted! I've scheduled this delightful event for you, Master~";
    }
}
