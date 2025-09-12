package rumi.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rumi.task.TaskList;
import rumi.ui.Ui;

/**
 * Handles parsing of user command and returning the correct command type.
 */
public class Parser {
    private static final String UNMARK_CMD_REGEX = "unmark\\s+(-?\\d+)";
    private static final String MARK_CMD_REGEX = "mark\\s+(-?\\d+)";
    private static final String DELETE_CMD_REGEX = "delete\\s+(-?\\d+)";
    private static final String FIND_COMMAND_REGEX = "find\\s+(.+)";

    private static final String TODO_COMMAND_REGEX = "todo\\s+(.+)";
    private static final String DEADLINE_COMMAND_REGEX = "deadline\\s+(.+?)\\s+/by\\s+(.+)";
    private static final String EVENT_COMMAND_REGEX =
            "event\\s+(.+?)\\s+/from\\s+(.+?)\\s+/to\\s+(.+)";

    private static final Pattern UNMARK_CMD_PATTERN = Pattern.compile(UNMARK_CMD_REGEX);
    private static final Pattern MARK_CMD_PATTERN = Pattern.compile(MARK_CMD_REGEX);
    private static final Pattern DELETE_CMD_PATTERN = Pattern.compile(DELETE_CMD_REGEX);
    private static final Pattern FIND_COMMAND_PATTERN = Pattern.compile(FIND_COMMAND_REGEX);

    private static final Pattern TODO_COMMAND_PATTERN = Pattern.compile(TODO_COMMAND_REGEX);
    private static final Pattern DEADLINE_COMMAND_PATTERN = Pattern.compile(DEADLINE_COMMAND_REGEX);
    private static final Pattern EVENT_COMMAND_PATTERN = Pattern.compile(EVENT_COMMAND_REGEX);


    private final TaskList tasks;
    private final Ui ui;


    /**
     * Creates a new parser using the given task lists and ui reference
     */
    public Parser(TaskList tasks, Ui ui) {
        assert tasks != null && ui != null;
        this.tasks = tasks;
        this.ui = ui;
    }

    /**
     * Parses a command and passes any arguments to the command handler.
     */
    public Command parse(String command) throws UnknownUserCommandException {
        if (command.equals("bye")) {
            return new ExitCommand();
        } else if (command.equals("list")) {
            return new ListCommand(this.tasks, this.ui);
        } else if (command.matches(MARK_CMD_REGEX)) {
            Matcher matcher = MARK_CMD_PATTERN.matcher(command);
            if (matcher.matches()) {
                String taskNo = matcher.group(1);
                return new MarkCommand(this.tasks, this.ui, taskNo);
            }
        } else if (command.matches(UNMARK_CMD_REGEX)) {
            Matcher matcher = UNMARK_CMD_PATTERN.matcher(command);
            if (matcher.matches()) {
                String taskNo = matcher.group(1);
                return new UnmarkCommand(this.tasks, this.ui, taskNo);
            }
        } else if (command.matches(DELETE_CMD_REGEX)) {
            Matcher matcher = DELETE_CMD_PATTERN.matcher(command);
            if (matcher.matches()) {
                String taskNo = matcher.group(1);
                return new DeleteCommand(this.tasks, this.ui, taskNo);
            }
        } else if (command.matches(TODO_COMMAND_REGEX)) {
            Matcher matcher = TODO_COMMAND_PATTERN.matcher(command);
            if (matcher.matches()) {
                String title = matcher.group(1);
                return new ToDoCommand(this.tasks, this.ui, title);
            }
        } else if (command.matches(DEADLINE_COMMAND_REGEX)) {
            Matcher matcher = DEADLINE_COMMAND_PATTERN.matcher(command);
            if (matcher.matches()) {
                String title = matcher.group(1);
                String dueDate = matcher.group(2);
                return new DeadlineCommand(this.tasks, this.ui, title, dueDate);
            }
        } else if (command.matches(EVENT_COMMAND_REGEX)) {
            Matcher matcher = EVENT_COMMAND_PATTERN.matcher(command);
            if (matcher.matches()) {
                String title = matcher.group(1);
                String from = matcher.group(2);
                String to = matcher.group(3);
                return new EventCommand(this.tasks, this.ui, title, from, to);
            }
        } else if (command.matches(FIND_COMMAND_REGEX)) {
            Matcher matcher = FIND_COMMAND_PATTERN.matcher(command);
            if (matcher.matches()) {
                String query = matcher.group(1);
                return new FindCommand(this.tasks, this.ui, query);
            }
        }

        throw new UnknownUserCommandException();
    }
}
