package rumi.task;

import java.time.DateTimeException;

/** Exceptions representing illegal deadline or event creation with datetime that is in the past. */
public class InvalidTaskDateTimeException extends DateTimeException {

    public InvalidTaskDateTimeException() {
        super("Datetime cannot be in the past.");
    }

}
