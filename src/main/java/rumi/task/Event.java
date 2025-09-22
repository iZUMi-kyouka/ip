package rumi.task;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rumi.tag.Tag;
import rumi.tag.TagList;
import rumi.utils.Assert;
import rumi.utils.Comparator;
import rumi.utils.RumiDate;

/** Represents a task of subtype event. */
public class Event extends Task {
    private final RumiDate from;
    private final RumiDate to;

    /**
     * Constructs a task of subtype event with the given title, and from and to date string.
     */
    public Event(String title, String from, String to) throws DateTimeException {
        this(title, from, to, false, null);
    }

    /**
     * Constructs a task of subtype event from the give Task object, and from and to date string.
     */
    public Event(Task t, String from, String to) throws DateTimeException {
        super(t);
        Assert.notNull(t, from, to);

        this.from = RumiDate.fromString(from);
        this.to = RumiDate.fromString(to);
        this.validateEndDateTime();
    }

    Event(String title, String from, String to, boolean isDone, TagList tags)
            throws DateTimeException {
        super(title, tags == null ? null : tags.toArray(new Tag[0]));
        Assert.notNull(title, from, to);

        this.from = RumiDate.fromString(from);
        this.to = RumiDate.fromString(to);

        this.validateEndDateTime();

        if (isDone) {
            this.markAsDone();
        }
    }

    /**
     * Constructs a task of subtype event with the given title, from and to date string, and tags
     */
    public Event(String title, String from, String to, TagList tags)
            throws DateTimeException {
        this(title, from, to, false, tags);
    }

    private void validateEndDateTime() {
        if (this.to.isInThePast()) {
            throw new InvalidTaskDateTimeException();
        }
    }


    @Override
    public String toString() {
        return String.format("[E]%s (from: %s --> to: %s) %s", super.toString(), this.from, this.to,
                this.tags);
    }

    /** Creates an event from a serialised string representing event. */
    public static Event fromString(String s) throws EventStringParseException {
        Task task = Task.fromString(s);
        Pattern pattern = Pattern.compile(
                "E\\s+@#@\\s+([DP])\\s+@#@\\s+(.+?)\\s+@#@\\s+(.+?)\\s+@#@\\s+(.+?)(?:\\s+@#@\\s+TAGS:(.*))?");
        Matcher matcher = pattern.matcher(s);

        if (!matcher.matches()) {
            throw new EventStringParseException();
        }

        String from = matcher.group(3);
        String to = matcher.group(4);
        return new Event(task, from, to);
    }

    @Override
    public String toSerialisedString() {
        return String.format("E @#@ %s @#@ %s @#@ %s @#@ %s @#@ TAGS:%s",
                this.getStatus() ? 'D' : 'P', this.getTitle(), this.from.toSerialisedString(),
                this.to.toSerialisedString(), this.tags.toSerialisedString());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Event) || o == null) {
            return false;
        }

        Event evt = (Event) o;
        return super.equals(o) && Comparator.allEqual(new Object[] {this.from, this.to},
                new Object[] {evt.from, evt.to});
    }

}
