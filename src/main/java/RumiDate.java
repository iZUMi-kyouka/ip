import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RumiDate {
    private String stringDate;
    private LocalDateTime parsedDate;

    RumiDate(String s) {
        this.stringDate = s;
    }

    public static RumiDate fromString(String s) {
        final RumiDate parsedDate = new RumiDate(s);
        String validDateTimePattern = "^(0?[1-9]|[12][0-9]|3[01])" +   // day
                "([-.,/ ])" +                     // date separator (group 2)
                "(0?[1-9]|1[0-2])" +              // month
                "\\2" +                           // same date separator
                "(\\d{2}|\\d{4})" +               // year
                "\\s+" +                           // space between date and time
                "([01]?[0-9]|2[0-3])" +           // hour
                "(?:" +
                "([:.\\-])([0-5][0-9])" +         // optional minute with separator
                "(?:" +
                "\\7([0-5][0-9])" +               // optional second with same separator
                ")?" +
                ")?" +
                "\\s*([AaPp][Mm])?$";             // optional AM/PM

        Pattern pattern = Pattern.compile(validDateTimePattern);
        Matcher matcher = pattern.matcher(s);

        if (matcher.matches()) {
            LocalDateTime dt = getLocalDateTime(matcher);
            parsedDate.parsedDate = dt;

        }

        return parsedDate;
    }

    private static LocalDateTime getLocalDateTime(Matcher matcher) {
        int day = Integer.parseInt(matcher.group(1));
        int month = Integer.parseInt(matcher.group(3));
        int year = Integer.parseInt(matcher.group(4));

        int hour = Integer.parseInt(matcher.group(5));
        String ampm = matcher.group(9);

        if (ampm != null) {
            if (ampm.equalsIgnoreCase("AM") && hour == 12) {
                hour = 0;
            } else if (ampm.equalsIgnoreCase("PM") && hour != 12) {
                hour += 12;
            }
        }

        int minute = matcher.group(7) != null ? Integer.parseInt(matcher.group(7)) : 0;
        int second = matcher.group(8) != null ? Integer.parseInt(matcher.group(8)) : 0;

        LocalDateTime dt = LocalDateTime.of(year, month, day, hour, minute, second);
        return dt;
    }
}
