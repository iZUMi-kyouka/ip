import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event extends Task {
    private String from;
    private String to;

    Event(String title, String from, String to) {
        super(title);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s --> to: %s)", super.toString(), this.from, this.to);
    }

    public static Event fromString(String s) throws EventStringParseException {
        Pattern pattern = Pattern.compile("E\\s+@#@\\s+(.+?)\\s+@#@\\s+(.+?)\\s+@#@\\s+(.+?)");
        Matcher matcher = pattern.matcher(s);

        if (matcher.matches()) {
            String title = matcher.group(1);
            String from = matcher.group(2);
            String to = matcher.group(3);
            return new Event(title, from, to);
        }

        throw new EventStringParseException();
    }

    public String toSerialisedString() {
        return String.format("E @#@ %s @#@ %s @#@ %s", super.getTitle(), this.from, this.to);
    }

}
