import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToDo extends Task {
    private final String SEPARATOR = " @|@ ";

    ToDo(String title) {
        super(title);
    }

    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }

    public static ToDo fromString(String s) throws ToDoStringParseException {
        Pattern pattern = Pattern.compile("T\\s+@#@\\s+([DP])\\s+@#@\\s+(.+)");
        Matcher matcher = pattern.matcher(s);

        if (matcher.matches()) {
            boolean isDone = matcher.group(1).equals("D");
            String title = matcher.group(2);
            return new ToDo(title);
        }

        throw new ToDoStringParseException();
    }

    public String toSerialisedString() {
        return String.format("T @#@ %s @#@ %s", super.getTitle(), super.getStatus() ? 'D' : 'P');
    }
}
