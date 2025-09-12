package rumi.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rumi.task.TaskList;
import rumi.ui.Ui;

/**
 * Handles parsing of user command and returning the correct command type.
 */
public class Parser {

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
        } else if (command.matches("mark\\s+-?\\d+")) {
            String taskNo = command.split(" ")[1];
            return new MarkCommand(this.tasks, this.ui, taskNo);
        } else if (command.matches("delete\\s+-?\\d+")) {
            String taskNo = command.split(" ")[1];
            return new DeleteCommand(this.tasks, this.ui, taskNo);
        } else if (command.matches("todo\\s+(.+)")) {
            Pattern pattern = Pattern.compile("todo\\s+(.+)");
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                String title = matcher.group(1);
                return new ToDoCommand(this.tasks, this.ui, title);
            }
        } else if (command.matches("deadline\\s+(.+?)\\s+/by\\s+(.+)")) {
            Pattern pattern = Pattern.compile("deadline\\s+(.+?)\\s+/by\\s+(.+)");
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                String title = matcher.group(1);
                String dueDate = matcher.group(2);
                return new DeadlineCommand(this.tasks, this.ui, title, dueDate);
            }
        } else if (command.matches("event\\s+(.+?)\\s+/from\\s+(.+?)\\s+/to\\s+(.+)")) {
            Pattern pattern = Pattern.compile("event\\s+(.+?)\\s+/from\\s+(.+?)\\s+/to\\s+(.+)");
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                String title = matcher.group(1);
                String from = matcher.group(2);
                String to = matcher.group(3);
                return new EventCommand(this.tasks, this.ui, title, from, to);
            }
        } else if (command.matches("find\\s+(.+)")) {
            Pattern pattern = Pattern.compile("find\\s+(.+)");
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                String query = matcher.group(1);
                return new FindCommand(this.tasks, this.ui, query);
            }
        }

        throw new UnknownUserCommandException();
    }
}
