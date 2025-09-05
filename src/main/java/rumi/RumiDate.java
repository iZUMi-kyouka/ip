package rumi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A flexible representation of date that is either based on String or
 * LocalDateTime.
 */
public class RumiDate {

    private final String stringDate;
    private LocalDateTime parsedDate;
    private final DateTimeFormatter DATETIME_OUTPUT_FORMAT = DateTimeFormatter.ofPattern("dd-MM-YYYY hh:mma");

    RumiDate(String s) {
        this.stringDate = s;
    }

    /**
     * Tries to parse the input string into a LocalDateTime. If this is not
     * possible, a RumiDate object that internally represents time as a string
     * is returned.
     */
    public static RumiDate fromString(String s) {
        final RumiDate parsedDate = new RumiDate(s);
        String validDateTimePattern
                = "^(0?[1-9]|[12][0-9]|3[01])"
                + // day
                "([-.,/ ])"
                + // date separator (group 2)
                "(0?[1-9]|1[0-2])"
                + // month
                "\\2"
                + // same date separator
                "(\\d{2}|\\d{4})"
                + // year
                "(?:\\s+"
                + // optional space before time (entire time optional)
                "([01]?[0-9]|2[0-3])"
                + // hour (if present)
                "(?:"
                + "([:.\\-])([0-5][0-9])"
                + // optional minute with separator
                "(?:"
                + "\\7([0-5][0-9])"
                + // optional second
                ")?"
                + ")?"
                + "\\s*([AaPp][Mm])?"
                + // optional AM/PM
                ")?$"; // end of optional time

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

        int hour = matcher.group(5) != null ? Integer.parseInt(matcher.group(5)) : 23;
        String ampm = matcher.group(9);

        if (ampm != null) {
            if (ampm.equalsIgnoreCase("AM") && hour == 12) {
                hour = 0;
            } else if (ampm.equalsIgnoreCase("PM") && hour != 12) {
                hour += 12;
            }
        }

        int minute = matcher.group(7) != null ? Integer.parseInt(matcher.group(7)) : 59;
        int second = matcher.group(8) != null ? Integer.parseInt(matcher.group(8)) : 59;

        LocalDateTime dt = LocalDateTime.of(year, month, day, hour, minute, second);
        return dt;
    }

    @Override
    public String toString() {
        return parsedDate != null
                ? parsedDate.format(DATETIME_OUTPUT_FORMAT)
                : stringDate;
    }
}
