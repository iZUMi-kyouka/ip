package rumi.task;

import java.time.DateTimeException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rumi.utils.RumiDate;

/** Represents a task of subtype deadline */
public class Deadline extends Task {
    private final RumiDate deadline;

    /**
     * Constructs a task of subtype deadline with the given title and deadline
     */
    public Deadline(String title, String deadline) throws DateTimeException {
        super(title);

        assert !deadline.isEmpty();
        this.deadline = RumiDate.fromString(deadline);
    }

    Deadline(String title, String deadline, boolean isDone) throws DateTimeException {
        super(title);
        this.deadline = RumiDate.fromString(deadline);
        if (isDone) {
            this.markAsDone();
        }
    }


    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), this.deadline);
    }

    /** Creates a deadline from a serialised string representing deadline. */
    public static Deadline fromString(String s) throws DeadlineStringParseException {
        Pattern pattern = Pattern.compile("D\\s+@#@\\s+([DP])\\s+@#@\\s+(.+?)\\s+@#@\\s+(.+)");
        Matcher matcher = pattern.matcher(s);

        if (!matcher.matches()) {
            throw new DeadlineStringParseException();
        }

        boolean isDone = matcher.group(1).equals("D");
        String title = matcher.group(2);
        String deadline = matcher.group(3);
        return new Deadline(title, deadline, isDone);
    }

    @Override
    public String toSerialisedString() {
        return String.format("D @#@ %s @#@ %s @#@ %s", this.getStatus() ? 'D' : 'P',
                this.getTitle(), this.deadline);
    }
}
