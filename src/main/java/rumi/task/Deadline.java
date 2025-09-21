package rumi.task;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rumi.tag.Tag;
import rumi.utils.Assert;
import rumi.utils.RumiDate;

/** Represents a task of subtype deadline */
public class Deadline extends Task {
    private final RumiDate deadline;

    /**
     * Constructs a task of subtype deadline with the given title, deadline, and tags.
     */
    public Deadline(String title, String deadline, ArrayList<Tag> tags) throws DateTimeException {
        super(title, tags == null ? null : tags.toArray(new Tag[0]));

        Assert.nonEmptyString(title);
        this.deadline = RumiDate.fromString(deadline);
        validateDeadline();
    }

    /**
     * Constructs a task of subtype deadline with the given title and deadline.
     */
    public Deadline(String title, String deadline) throws DateTimeException {
        this(title, deadline, null);
    }

    Deadline(Task t, String deadline) throws DateTimeException {
        super(t);

        Assert.nonEmptyString(deadline);
        this.deadline = RumiDate.fromString(deadline);
        validateDeadline();
    }

    private void validateDeadline() throws InvalidTaskDateTimeException {
        if (this.deadline.isInThePast()) {
            throw new InvalidTaskDateTimeException();
        }
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s) %s", super.toString(), this.deadline,
                Tag.stringifyTagList(this.tags));
    }

    /** Constructs a deadline from a serialised string representing of a deadline. */
    public static Deadline fromString(String s) throws IllegalArgumentException {
        Task task = Task.fromString(s);
        Pattern pattern = Pattern.compile(
                "D\\s+@#@\\s+([DP])\\s+@#@\\s+(.+?)\\s+@#@\\s+(.+?)(?:\\s+@#@\\s+TAGS:(.*))?");
        Matcher matcher = pattern.matcher(s);

        if (!matcher.matches()) {
            throw new DeadlineStringParseException();
        }

        String deadline = matcher.group(3);
        return new Deadline(task, deadline);
    }

    @Override
    public String toSerialisedString() {
        return String.format("D @#@ %s @#@ %s @#@ %s @#@ TAGS:%s", this.getStatus() ? 'D' : 'P',
                this.getTitle(), this.deadline, Tag.serialiseTagList(this.tags));
    }
}
