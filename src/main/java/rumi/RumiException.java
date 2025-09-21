package rumi;

/** A custom exception used for Rumi-specific exceptions. */
public class RumiException extends Exception {
    public static final RumiException INVALID_DATE_EXCEPTION = new RumiException(
            "The date you entered is invalid.\nPlease enter date in the following format: "
                    + "DD(s)MM(s)YY(YY) (HH(s)MM(s)(SS)(am/pm)),\n"
                    + "where '(..)' is optional, and 's' is eiteher '/', '-', or ' '. \n"
                    + "For example: 1672025 624pm");

    public RumiException(String message) {
        super(message);
    }
}
