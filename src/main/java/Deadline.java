import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Deadline extends Task {
    private RumiDate deadline;

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

    public String toSerialisedString() {
        return String.format("D @#@ %s @#@ %s @#@ %s", this.getStatus() ? 'D' : 'P', this.getTitle(), this.deadline);
    }
}
