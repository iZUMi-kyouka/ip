package rumi;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Represents a deadline */
public class Deadline extends Task {
    private final RumiDate deadline;

    Deadline(String title, String deadline) {
        super(title);
        this.deadline = RumiDate.fromString(deadline);
    }

    Deadline(String title, String deadline, boolean isDone) {
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

    /** Creates a deadline from a serialised string. */
    public static Deadline fromString(String s) throws DeadlineStringParseException {
        Pattern pattern = Pattern.compile("D\\s+@#@\\s+([DP])\\s+@#@\\s+(.+?)\\s+@#@\\s+(.+)");
        Matcher matcher = pattern.matcher(s);

        if (matcher.matches()) {
            boolean isDone = matcher.group(1).equals("D");
            String title = matcher.group(2);
            String deadline = matcher.group(3);
            return new Deadline(title, deadline, isDone);
        }

        throw new DeadlineStringParseException();
    }

    @Override
    public String toSerialisedString() {
        return String.format("D @#@ %s @#@ %s @#@ %s", this.getStatus() ? 'D' : 'P', this.getTitle(), this.deadline);
    }
}
