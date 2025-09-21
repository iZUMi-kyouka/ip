package rumi.parser;

import java.util.ArrayList;
import java.util.Optional;
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
    private static final String CMD_REGEX_EXIT = "bye";
    private static final String CMD_REGEX_LIST = "list";
    private static final String CMD_REGEX_MARK = "mark\\s+(-?\\d+)";
    private static final String CMD_REGEX_UNMARK = "unmark\\s+(-?\\d+)";
    private static final String CMD_REGEX_FIND = "find\\s+(.+)";
    private static final String CMD_REGEX_DELETE = "delete\\s+(-?\\d+)";
    private static final String CMD_REGEX_TODO = "todo\\s+(.+?)(?:\\s+/tags\\s+(.+))?";
    private static final String CMD_REGEX_DEADLINE =
            "deadline\\s+(.+?)\\s+/by\\s+(.+?)(?:\\s+/tags\\s+(.+))?";
    private static final String CMD_REGEX_EVENT =
            "event\\s+(.+?)\\s+/from\\s+(.+?)\\s+/to\\s+(.+?)(?:\\s+/tags\\s+(.+))?";
    private static final String REGEX_TAGS = "(?:todo|deadline|event)(?:.+?)/tags\\s+(.+)";

    private static final Pattern CMD_PATTERN_UNMARK = Pattern.compile(CMD_REGEX_UNMARK);
    private static final Pattern CMD_PATTERN_MARK = Pattern.compile(CMD_REGEX_MARK);
    private static final Pattern CMD_PATTERN_DELETE = Pattern.compile(CMD_REGEX_DELETE);
    private static final Pattern CMD_PATTERN_FIND = Pattern.compile(CMD_REGEX_FIND);
    private static final Pattern CMD_PATTERN_TODO = Pattern.compile(CMD_REGEX_TODO);
    private static final Pattern CMD_PATTERN_DEADLINE = Pattern.compile(CMD_REGEX_DEADLINE);
    private static final Pattern CMD_PATTERN_EVENT = Pattern.compile(CMD_REGEX_EVENT);
    private static final Pattern PATTERN_TAGS = Pattern.compile(REGEX_TAGS);

    private static final String ERROR_MSG_BYE =
            "Did you mean 'bye'? To exit Rumi, simply type 'bye' without any additional characters.";
    private static final String ERROR_MSG_LIST =
            "Did you mean 'list'? To list all tasks, simply type 'list' without any additional characters.";
    private static final String ERROR_MSG_MARK = "Did you mean 'mark <TASK_NUMBER>'?\n"
            + "To mark task number N as done, simply type 'mark N' without any additional characters.";
    private static final String ERROR_MSG_UNMARK = "Did you mean 'unmark <TASK_NUMBER>'?\n"
            + "To unmark task number N as done (returning it to pending state), "
            + "simply type 'unmark N' without any additional characters.";
    private static final String ERROR_MSG_DELETE = "Did you mean 'delete <TASK_NUMBER>'?\n"
            + "To delete task number N, simply type 'delete N' without any additional characters.";
    private static final String ERROR_MSG_TODO = "Did you mean 'todo <TASK_NAME>'?\n"
            + "To add a new todo with name NAME, simply type 'todo NAME' without any additional characters.";
    private static final String ERROR_MSG_DEADLINE =
            "Did you mean 'deadline <TASK_NAME> /by <TASK_DUE_DATE>'?\n"
                    + "To add a new deadline due by DUE_BY with name NAME, "
                    + "simply type 'deadline NAME /by DUE_BY' without any additional characters.";
    private static final String ERROR_MSG_EVENT =
            "Did you mean 'event <TASK_NAME> /from <START_TIME> /to <END_TIME>'?\n"
                    + "To add a new event from FROM to TO with name NAME, "
                    + "simply type 'event NAME /from FROM /to TO' without any additional characters.";

    private static final String[] VALID_COMMANDS =
            {"bye", "list", "mark", "unmark", "delete", "todo", "deadline", "event"};

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
        assert command != null;

        Matcher matcher = PATTERN_TAGS.matcher(command);
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
        assert command != null;

        ArrayList<Tag> tags = parseTags(command);

        if (command.equals(CMD_REGEX_EXIT)) {
            return new ExitCommand();
        } else if (command.equals(CMD_REGEX_LIST)) {
            return new ListCommand(this.tasks, this.ui);
        } else if (command.matches(CMD_REGEX_MARK)) {
            Matcher matcher = CMD_PATTERN_MARK.matcher(command);
            if (matcher.matches()) {
                String taskNo = matcher.group(1);
                return new MarkCommand(this.tasks, this.ui, taskNo);
            }
        } else if (command.matches(CMD_REGEX_UNMARK)) {
            Matcher matcher = CMD_PATTERN_UNMARK.matcher(command);
            if (matcher.matches()) {
                String taskNo = matcher.group(1);
                return new UnmarkCommand(this.tasks, this.ui, taskNo);
            }
        } else if (command.matches(CMD_REGEX_DELETE)) {
            Matcher matcher = CMD_PATTERN_DELETE.matcher(command);
            if (matcher.matches()) {
                String taskNo = matcher.group(1);
                return new DeleteCommand(this.tasks, this.ui, taskNo);
            }
        } else if (command.matches(CMD_REGEX_TODO)) {
            Matcher matcher = CMD_PATTERN_TODO.matcher(command);
            if (matcher.matches()) {
                String title = matcher.group(1);
                return new ToDoCommand(this.tasks, this.ui, title, tags);
            }
        } else if (command.matches(CMD_REGEX_DEADLINE)) {
            Matcher matcher = CMD_PATTERN_DEADLINE.matcher(command);
            if (matcher.matches()) {
                String title = matcher.group(1);
                String dueDate = matcher.group(2);
                return new DeadlineCommand(this.tasks, this.ui, title, dueDate, tags);
            }
        } else if (command.matches(CMD_REGEX_EVENT)) {
            Matcher matcher = CMD_PATTERN_EVENT.matcher(command);
            if (matcher.matches()) {
                String title = matcher.group(1);
                String from = matcher.group(2);
                String to = matcher.group(3);
                return new EventCommand(this.tasks, this.ui, title, from, to, tags);
            }
        } else if (command.matches(CMD_REGEX_FIND)) {
            Matcher matcher = CMD_PATTERN_FIND.matcher(command);
            if (matcher.matches()) {
                String query = matcher.group(1);
                return new FindCommand(this.tasks, this.ui, query);
            }
        }

        throw new UnknownUserCommandException();
    }

    /** Computes the Levenshtein distance between two strings. */
    private int computeLevenshteinDistance(String s1, String s2) {
        Assert.notNull(s1, s2);

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
        assert inputCommand != null;

        String[] tokens = inputCommand.split(" ");
        if (tokens.length == 0) {
            return "";
        }
        String firstToken = tokens[0];

        String closest = "";
        int minDistance = Integer.MAX_VALUE;

        for (String cmd : VALID_COMMANDS) {
            int distance = computeLevenshteinDistance(firstToken, cmd);
            if (distance < minDistance) {
                minDistance = distance;
                closest = cmd;
            }
        }

        // Edit distance threshold
        if (minDistance <= 4) {
            return closest;
        }

        return "";
    }

    /**
     * Suggests the most related command that the user might intend on entering including a guide on
     * how to use the command based on the given command
     */
    public Optional<String> suggestErrorMessage(String command) {
        String closestCommand = getClosestCommand(command);
        if (closestCommand.isEmpty()) {
            return Optional.empty();
        }

        String msg = switch (closestCommand) {
        case "bye" -> ERROR_MSG_BYE;
        case "list" -> ERROR_MSG_LIST;
        case "mark" -> ERROR_MSG_MARK;
        case "unmark" -> ERROR_MSG_UNMARK;
        case "delete" -> ERROR_MSG_DELETE;
        case "todo" -> ERROR_MSG_TODO;
        case "deadline" -> ERROR_MSG_DEADLINE;
        case "event" -> ERROR_MSG_EVENT;
        default -> "";
        };

        return msg.isEmpty() ? Optional.empty() : Optional.of(msg);
    }
}
