package rumi.task;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rumi.tag.Tag;
import rumi.tag.TagList;
import rumi.utils.Assert;

/**
 * Represents a task added by the user.
 */
public class Task {
    private static final String TASK_TAG_SEPARATOR_REGEX = "^(.+?)(?:\\s+@#@\\s+TAGS:(.+))?$";
    private static final String TASK_REGEX =
            "^(?:.+?)\\s+@#@\\s+([DP])\\s+@#@\\s+(.+?)(?:\\s+@#@\\s+.+)?";
    private static final Pattern TASK_TAG_PATTERN = Pattern.compile(TASK_TAG_SEPARATOR_REGEX);
    private static final Pattern TASK_PATTERN = Pattern.compile(TASK_REGEX);

    protected final TagList tags = new TagList();
    private final String title;
    private boolean isDone;

    /**
     * Constructs a pending task with the given title
     */
    public Task(String title) {
        this(title, false);
    }

    /**
     * Constructs a task with the given title and status
     */
    private Task(String title, boolean isDone) {
        this(title, isDone, (Tag[]) null);
    }

    /**
     * Constructs a task with the same attributes as the given task
     */
    protected Task(Task t) {
        this.title = t.title;
        this.isDone = t.isDone;
        this.tags.addAll(t.tags);
    }

    /**
     * Cnstructs a pending task with the given title and tags.
     */
    public Task(String title, Tag... tags) {
        this(title, false, tags);
    }

    /**
     * Constructs a task with the given title, status, and tag(s)
     */
    public Task(String title, boolean isDone, Tag... tags) {
        Assert.nonEmptyString(title);

        this.title = title;
        this.isDone = isDone;
        if (tags != null) {
            this.tags.addAll(Arrays.asList(tags));
        }
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Unmark a done task, reverting it back to pending state.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean getStatus() {
        return this.isDone;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.isDone ? 'X' : ' ', this.title);
    }

    /**
     * Serialises this task as a string.
     */
    public String toSerialisedString() {
        return String.format("! @#@ %s @#@ %s", this.title, this.isDone ? 'D' : 'P');
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Task task) || obj == null) {
            return false;
        }

        return this.isDone == task.isDone && this.title.equals(task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.isDone);
    }

    private static TagList parseTagsFromSerialisedTask(String s)
            throws IllegalArgumentException {
        Matcher matcher = TASK_TAG_PATTERN.matcher(s);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }

        String tagsPart = matcher.group(2);
        TagList tags = new TagList();

        if (tagsPart != null) {
            for (String tagName : tagsPart.split(",")) {
                tags.add(new Tag(tagName));
            }
        }

        return tags;
    }

    private static Task parseTaskFromSerialisedTask(String s) {
        Matcher matcher = TASK_TAG_PATTERN.matcher(s);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }

        String taskPart = matcher.group(1);
        matcher = TASK_PATTERN.matcher(taskPart);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }

        String status = matcher.group(1);
        String title = matcher.group(2);
        Assert.notNull(status, title);

        return new Task(title, status.equals("D"));
    }

    /**
     * Constructs a generic task from a serialised string representation of any task.
     */
    public static Task fromString(String s) throws IllegalArgumentException {
        TagList tags = Task.parseTagsFromSerialisedTask(s);
        Task task = Task.parseTaskFromSerialisedTask(s);
        task.tags.addAll(tags);

        return task;
    }
}
