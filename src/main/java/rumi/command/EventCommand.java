package rumi.command;

import java.time.DateTimeException;
import java.util.ArrayList;

import rumi.exception.RumiException;
import rumi.tag.Tag;
import rumi.tag.TagList;
import rumi.task.Event;
import rumi.task.InvalidTaskDateTimeException;
import rumi.task.TaskList;
import rumi.ui.Ui;
import rumi.utils.Assert;
import rumi.utils.Comparator;

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
            TagList tags) {
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
        } catch (InvalidTaskDateTimeException e) {
            throw RumiException.INVALID_DATETIME_IN_THE_PAST_EXCEPTION;
        } catch (DateTimeException e) {
            throw RumiException.INVALID_DATE_FORMAT_EXCEPTION;
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

        EventCommand cmd = (EventCommand) o;
        return super.equals(o) && Comparator.allEqual(new Object[] {this.from, this.to},
                new Object[] {cmd.from, cmd.to});
    }

    @Override
    protected String getSuccessMessage() {
        return "Noted! I've scheduled this delightful event for you, Master~";
    }
}
