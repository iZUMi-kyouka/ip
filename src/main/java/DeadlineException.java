
/** Exceptions related to invalid deadline command */
public class DeadlineException extends RumiException {

    DeadlineException() {
        super("Invalid syntax for deadline declaration.");
    }
}
