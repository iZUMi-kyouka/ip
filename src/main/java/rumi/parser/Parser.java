package rumi.parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rumi.command.Command;
import rumi.command.DeadlineCommand;
import rumi.command.DeleteCommand;
import rumi.command.EventCommand;
import rumi.command.ExitCommand;
import rumi.command.FindCommand;
import rumi.command.ListCommand;
import rumi.command.MarkCommand;
import rumi.command.ToDoCommand;
import rumi.command.UnknownUserCommandException;
import rumi.command.UnmarkCommand;
import rumi.tag.Tag;
import rumi.task.TaskList;
import rumi.ui.Ui;
import rumi.utils.Assert;

/**
 * Handles parsing of user command and returning the correct command type.
 */
public class Parser {
    private static final String EXIT_CMD_REGEX = "bye";
    private static final String LIST_CMD_REGEX = "list";
    private static final String MARK_CMD_REGEX = "mark\\s+(-?\\d+)";
    private static final String UNMARK_CMD_REGEX = "unmark\\s+(-?\\d+)";
    private static final String FIND_CMD_REGEX = "find\\s+(.+)";
    private static final String DELETE_CMD_REGEX = "delete\\s+(-?\\d+)";
    private static final String TODO_CMD_REGEX = "todo\\s+(.+?)(?:\\s+/tags\\s+(.+))?";
    private static final String DEADLINE_CMD_REGEX =
            "deadline\\s+(.+?)\\s+/by\\s+(.+?)(?:\\s+/tags\\s+(.+))?";
    private static final String EVENT_CMD_REGEX =
            "event\\s+(.+?)\\s+/from\\s+(.+?)\\s+/to\\s+(.+?)(?:\\s+/tags\\s+(.+))?";
    private static final String TAGS_REGEX = "(?:todo|deadline|event)(?:.+?)/tags\\s+(.+)";

    private static final Pattern UNMARK_CMD_PATTERN = Pattern.compile(UNMARK_CMD_REGEX);
    private static final Pattern MARK_CMD_PATTERN = Pattern.compile(MARK_CMD_REGEX);
    private static final Pattern DELETE_CMD_PATTERN = Pattern.compile(DELETE_CMD_REGEX);
    private static final Pattern FIND_COMMAND_PATTERN = Pattern.compile(FIND_CMD_REGEX);
    private static final Pattern TODO_CMD_PATTERN = Pattern.compile(TODO_CMD_REGEX);
    private static final Pattern DEADLINE_CMD_PATTERN = Pattern.compile(DEADLINE_CMD_REGEX);
    private static final Pattern EVENT_CMD_PATTERN = Pattern.compile(EVENT_CMD_REGEX);
    private static final Pattern TAGS_PATTERN = Pattern.compile(TAGS_REGEX);

    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates a new parser using the given task lists and ui reference
     */
    public Parser(TaskList tasks, Ui ui) {
        Assert.notNull(tasks, ui);

        this.tasks = tasks;
        this.ui = ui;
    }

    private ArrayList<Tag> parseTags(String command) {
        Matcher matcher = TAGS_PATTERN.matcher(command);
        ArrayList<Tag> tags = new ArrayList<>();

        if (!matcher.matches() || matcher.group(1) == null) {
            return tags;
        }

        for (String tag : matcher.group(1).split(",")) {
            tags.add(new Tag(tag.trim()));
        }

        System.out.println(tags);
        return tags;
    }

    /**
     * Parses a command and passes any arguments to the command handler.
     */
    public Command parse(String command) throws UnknownUserCommandException {
        ArrayList<Tag> tags = parseTags(command);

        if (command.equals(EXIT_CMD_REGEX)) {
            return new ExitCommand();
        } else if (command.equals(LIST_CMD_REGEX)) {
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
        } else if (command.matches(TODO_CMD_REGEX)) {
            Matcher matcher = TODO_CMD_PATTERN.matcher(command);
            if (matcher.matches()) {
                String title = matcher.group(1);
                return new ToDoCommand(this.tasks, this.ui, title, tags);
            }
        } else if (command.matches(DEADLINE_CMD_REGEX)) {
            Matcher matcher = DEADLINE_CMD_PATTERN.matcher(command);
            if (matcher.matches()) {
                String title = matcher.group(1);
                String dueDate = matcher.group(2);
                return new DeadlineCommand(this.tasks, this.ui, title, dueDate, tags);
            }
        } else if (command.matches(EVENT_CMD_REGEX)) {
            Matcher matcher = EVENT_CMD_PATTERN.matcher(command);
            if (matcher.matches()) {
                String title = matcher.group(1);
                String from = matcher.group(2);
                String to = matcher.group(3);
                return new EventCommand(this.tasks, this.ui, title, from, to, tags);
            }
        } else if (command.matches(FIND_CMD_REGEX)) {
            Matcher matcher = FIND_COMMAND_PATTERN.matcher(command);
            if (matcher.matches()) {
                String query = matcher.group(1);
                return new FindCommand(this.tasks, this.ui, query);
            }
        }

        throw new UnknownUserCommandException();
    }

    /** Helper method to compute Levenshtein distance between two strings */
    private int computeLevenshteinDistance(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost);
            }
        }
        return dp[len1][len2];
    }

    /** Returns the closest matching command given an invalid command. */
    private String getClosestCommand(String inputCommand) {
        String[] knownCommands =
                {"bye", "list", "mark", "unmark", "delete", "todo", "deadline", "event"};
        String[] tokens = inputCommand.split(" ");
        if (tokens.length == 0) {
            return "";
        }
        String firstToken = tokens[0];

        String closest = "";
        int minDistance = Integer.MAX_VALUE;

        for (String cmd : knownCommands) {
            int distance = computeLevenshteinDistance(firstToken, cmd);
            if (distance < minDistance) {
                minDistance = distance;
                closest = cmd;
            }
        }

        // You can add a threshold to avoid unrelated suggestions
        if (minDistance <= 4) { // max 2 edits difference
            return closest;
        }

        return "";
    }

    /**
     * Suggests the most related command that the user might intend on entering including a guide on
     * how to use the command based on the given command
     */
    public String suggestErrorMessage(String command) {
        String closestCommand = getClosestCommand(command);
        if (closestCommand.isEmpty()) {
            return ""; // no close match found
        }

        String msg = new String();

        switch (closestCommand) {
        case "bye" -> msg =
                "Did you mean 'bye'? To exit Rumi, simply type 'bye' without any additional characters.";
        case "list" -> msg =
                "Did you mean 'list'? To list all tasks, simply type 'list' without any additional characters.";
        case "mark" -> msg = "Did you mean 'mark <TASK_NUMBER>'?\n"
                + "To mark task number N as done, simply type 'mark N' without any additional characters.";
        case "unmark" -> msg = "Did you mean 'unmark <TASK_NUMBER>'?\n"
                + "To unmark task number N as done (returning it to pending state), "
                + "simply type 'mark N' without any additional characters.";
        case "delete" -> msg = "Did you mean 'delete <TASK_NUMBER>'?\n"
                + "To delete task number N, simply type 'delete N' without any additional characters.";
        case "todo" -> msg = "Did you mean 'todo <TASK_NAME>'?\n"
                + "To add a new todo with name NAME, simply type 'todo NAME' without any additional characters.";
        case "deadline" -> msg = "Did you mean 'deadline <TASK_NAME> /by <TASK_DUE_DATE>'?\n"
                + "To add a new deadline due by DUE_BY with name NAME, "
                + "simply type 'deadline NAME /by DUE_BY' without any additional characters.";
        case "event" -> msg =
                "Did you mean 'event <TASK_NAME> /from <START_TIME> /to <END_TIME>'?\n"
                        + "To add a new event from FROM to TO with name NAME, "
                        + "simply type 'event NAME /from FROM /to TO' without any additional characters.";
        default -> {
        }
        }

        return msg;
    }
}
