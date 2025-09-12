package rumi.task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rumi.utils.RumiDate;

/** Represents a task of subtype event. */
public class Event extends Task {
    private RumiDate from;
    private RumiDate to;

    /**
     * Constructs a task of subtype event with the given title, from, and to date
     */
    public Event(String title, String from, String to) {
        super(title);

        assert !from.isEmpty() && !to.isEmpty();

        this.from = RumiDate.fromString(from);
        this.to = RumiDate.fromString(to);
    }

    Event(String title, String from, String to, boolean isDone) {
        super(title);

        this.from = RumiDate.fromString(from);
        this.to = RumiDate.fromString(to);

        if (isDone) {
            this.markAsDone();
        }
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s --> to: %s)", super.toString(), this.from, this.to);
    }

    /** Creates an event from a serialised string representing event. */
    public static Event fromString(String s) throws EventStringParseException {
        Pattern pattern = Pattern
                .compile("E\\s+@#@\\s+([DP])\\s+@#@\\s+(.+?)\\s+@#@\\s+(.+?)\\s+@#@\\s+(.+)");
        Matcher matcher = pattern.matcher(s);

        if (!matcher.matches()) {
            throw new EventStringParseException();
        }

        boolean isDone = matcher.group(1).equals("D");
        String title = matcher.group(2);
        String from = matcher.group(3);
        String to = matcher.group(4);
        return new Event(title, from, to, isDone);
    }

    @Override
    public String toSerialisedString() {
        return String.format("E @#@ %s @#@ %s @#@ %s @#@ %s", this.getStatus() ? 'D' : 'P',
                this.getTitle(), this.from, this.to);
    }

}
