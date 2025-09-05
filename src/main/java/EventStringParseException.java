/** Exceptions representing failure to parse an Event from String */
public class EventStringParseException extends IllegalArgumentException {
    EventStringParseException() {
        super("Invalid Event string");
    }
}
