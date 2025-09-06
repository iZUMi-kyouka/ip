package rumi;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Represents a general to-do item. */
public class ToDo extends Task {
    public ToDo(String title) {
        super(title);
    }

    ToDo(String title, boolean isDone) {
        super(title);
        if (isDone) {
            this.markAsDone();
        }
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }

    /** Creates a to-do from a serialised string representing a to-do. */
    public static ToDo fromString(String s) throws ToDoStringParseException {
        Pattern pattern = Pattern.compile("T\\s+@#@\\s+([DP])\\s+@#@\\s+(.+)");
        Matcher matcher = pattern.matcher(s);

        if (matcher.matches()) {
            boolean isDone = matcher.group(1).equals("D");
            String title = matcher.group(2);
            return new ToDo(title, isDone);
        }

        throw new ToDoStringParseException();
    }

    @Override
    public String toSerialisedString() {
        return String.format("T @#@ %s @#@ %s", this.getStatus() ? 'D' : 'P', this.getTitle());
    }
}
