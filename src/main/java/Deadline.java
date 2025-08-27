import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Deadline extends Task {
    private String deadline;

    Deadline(String title, String deadline) {
        super(title);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), this.deadline);
    }

    public static Deadline fromString(String s) throws DeadlineStringParseException {
        Pattern pattern = Pattern.compile("D\\s+@#@\\s+(.+?)\\s+@#@\\s+(.+?)");
        Matcher matcher = pattern.matcher(s);

        if (matcher.matches()) {
            String title = matcher.group(1);
            String deadline = matcher.group(2);
            return new Deadline(title, deadline);
        }

        throw new DeadlineStringParseException();
    }

    public String toSerialisedString() {
        return String.format("D @#@ %s @#@ %s", super.getTitle(), this.deadline);
    }
}
