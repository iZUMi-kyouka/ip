package rumi.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A flexible representation of date that is either based on String or LocalDateTime.
 */
public class RumiDate {

    private static final DateTimeFormatter DATETIME_OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-YYYY hh:mma");
    private final String stringDate;
    private LocalDateTime parsedDate;

    RumiDate(String s) {
        this.stringDate = s;
    }

    /**
     * Tries to parse the input string into a LocalDateTime from a variety of user input. If this is
     * not possible, a RumiDate object that internally represents time as a string is returned.
     */
    public static RumiDate fromString(String s) {
        final RumiDate parsedDate = new RumiDate(s);
        String validDateTimePattern = "^([12][0-9]|3[01]|0?[1-9])" // day - reordered
                + "(?:([-./ ])(0?[1-9]|1[0-2])\\2(\\d{4}|\\d{2})" // with separator, 4-digit year first
                + "|" // OR
                + "(0?[1-9]|1[0-2])(\\d{4}|\\d{2}))" // no separator, 4-digit year first
                + "(?:\\s+" // optional time section
                + "([01]?[0-9]|2[0-3])" // hours
                + "(?:" + "(?:([:.-])([0-5][0-9])(?:\\7([0-5][0-9]))?)" // with separator
                + "|" + "([0-5][0-9])([0-5][0-9])?" // no separator
                + ")?" + "\\s*([AaPp][Mm])?" // optional AM/PM
                + ")?$";

        Pattern pattern = Pattern.compile(validDateTimePattern);
        Matcher matcher = pattern.matcher(s);

        if (matcher.matches()) {
            LocalDateTime dt = getLocalDateTime(matcher);
            parsedDate.parsedDate = dt;

        }

        return parsedDate;
    }

    private static LocalDateTime getLocalDateTime(Matcher matcher) {
        final int defaultHr = 23;
        final int defaultMin = 00;
        final int defaultSec = 00;

        // Regex groups mapping:
        // 1 = day
        // 2 = date separator (if used)
        // 3 = month (when separator branch matched)
        // 4 = year (when separator branch matched)
        // 5 = month (when no-separator branch matched)
        // 6 = year (when no-separator branch matched)
        // 7 = hour
        // 8 = time separator (if used)
        // 9 = minute (with sep)
        // 10 = second (with sep)
        // 11 = minute (no sep)
        // 12 = second (no sep)
        // 13 = AM/PM

        int day = Integer.parseInt(matcher.group(1));

        // Handle month and year
        int month;
        String yearStr;
        if (matcher.group(3) != null) { // Date separator is present
            month = Integer.parseInt(matcher.group(3));
            yearStr = matcher.group(4);
        } else { // No date separator
            month = Integer.parseInt(matcher.group(5));
            yearStr = matcher.group(6);
        }

        // Handle year digit variation
        int year = Integer.parseInt(yearStr);
        if (yearStr.length() == 2) {
            year += 2000;
        }

        // Handle hour
        int hour = defaultHr;
        boolean hasHour = false;
        if (matcher.group(7) != null) {
            hour = Integer.parseInt(matcher.group(7));
            hasHour = true;
        }

        // Minute / second: handle two forms (with sep -> groups 8/9/10) or (no sep -> groups 11/12)
        int minute = defaultMin;
        int second = defaultSec;

        if (matcher.group(8) != null) { // Time separator is present
            minute = Integer.parseInt(matcher.group(9));
            if (matcher.group(10) != null) {
                second = Integer.parseInt(matcher.group(10));
            }
        } else if (matcher.group(11) != null) { // No time separator
            minute = Integer.parseInt(matcher.group(11));
            if (matcher.group(12) != null) {
                second = Integer.parseInt(matcher.group(12));
            }
        }

        // AM/PM (apply only if hour was actually present)
        String ampm = matcher.group(13);
        if (ampm != null && hasHour) {
            if (ampm.equalsIgnoreCase("AM") && hour == 12) {
                hour = 0;
            } else if (ampm.equalsIgnoreCase("PM") && hour != 12) {
                hour += 12;
            }
        }

        return LocalDateTime.of(year, month, day, hour, minute, second);
    }

    @Override
    public String toString() {
        return parsedDate != null ? parsedDate.format(DATETIME_OUTPUT_FORMAT) : stringDate;
    }
}
