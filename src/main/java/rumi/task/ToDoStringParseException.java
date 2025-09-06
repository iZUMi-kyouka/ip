package rumi.task;

/** Exception representing failure to parse a ToDo from string. */
public class ToDoStringParseException extends IllegalArgumentException {
    ToDoStringParseException() {
        super("Invalid ToDo string");
    }
}
