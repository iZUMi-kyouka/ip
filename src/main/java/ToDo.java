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

    public static ToDo fromString(String s) {
        Pattern pattern = Pattern.compile("T\\s+@|@\\s+(.+?)\\s+");
        Matcher matcher = pattern.matcher(s);

        if (matcher.matches()) {
            String title = matcher.group(1);
            return new ToDo(title);
        }
    }
}
