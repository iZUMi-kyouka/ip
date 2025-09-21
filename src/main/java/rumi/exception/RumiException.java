package rumi.exception;

/**
 * A custom exception used for Rumi-specific exceptions.
 */
public class RumiException extends Exception {
    public static final RumiException INVALID_DATE_FORMAT_EXCEPTION = new RumiException(
            "The date you entered is invalid.\nPlease enter date in the following format: "
                    + "DD(s)MM(s)YY(YY) (HH(s)MM(s)(SS)(am/pm)),\n"
                    + "where '(..)' is optional, and 's' is either '/', '-', or ' '. \n"
                    + "For example: 1672025 624pm");
    public static final RumiException INVALID_DATETIME_IN_THE_PAST_EXCEPTION = new RumiException(
            "The date you entered is invalid as it is in the past.\nPlease enter a date and time that has not passed.");

    public RumiException(String message) {
        super(message);
    }

    public RumiException(String format, Object... o) {
        super(String.format(format, o));
    }
}
