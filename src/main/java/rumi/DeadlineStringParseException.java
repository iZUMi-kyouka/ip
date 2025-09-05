package rumi;
/** Exceptions representing failure to parse a Deadline from String */
public class DeadlineStringParseException extends IllegalArgumentException {
    DeadlineStringParseException() {
        super("Invalid Deadline string");
    }
}
