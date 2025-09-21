package rumi.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rumi.tag.Tag;
import rumi.utils.Assert;

/** Represents a task in the task list. */
public class Task {
    protected ArrayList<Tag> tags = new ArrayList<>();
    private final String title;
    private boolean isDone;

    /** Constructs a task with the given title */
    public Task(String title) {
        assert title != null;

        this.title = title;
        this.isDone = false;
    }

    /** Constructs a task with the given title and status */
    private Task(String title, boolean isDone) {
        assert title != null;

        this.title = title;
        this.isDone = isDone;
    }

    /** Constructs a task with the same attributes as the given task */
    protected Task(Task t) {
        this.title = t.title;
        this.isDone = t.isDone;
        this.tags = t.tags;
    }

    /** Constructs a task with the given title and tag(s) */
    public Task(String title, Tag... tags) {
        assert title != null;

        this.title = title;
        this.isDone = false;
        if (tags != null) {
            this.tags.addAll(Arrays.asList(tags));
        }
    }

    public void markAsDone() {
        this.isDone = true;
    }

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

    /** Serialises this task as a string. */
    public String toSerialisedString() {
        return String.format("! @#@ %s @#@ %s", this.title, this.isDone ? 'D' : 'P');
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Task task)) {
            return false;
        }

        return this.isDone == task.isDone && this.title.equals(task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.isDone);
    }

    /** Constructs a generic task from a serialised string representing of any task. */
    public static Task fromString(String s) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile("^(.+?)(?:\\s+@#@\\s+TAGS:(.+))?$");
        Matcher matcher = pattern.matcher(s);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }

        String taskPart = matcher.group(1);
        String tagsPart = matcher.group(2);
        ArrayList<Tag> tags = new ArrayList<>();

        if (tagsPart != null) {
            for (String tagName : tagsPart.split(",")) {
                tags.add(new Tag(tagName));
            }
        }

        pattern = Pattern.compile("^(?:.+?)\\s+@#@\\s+([DP])\\s+@#@\\s+(.+?)(?:\\s+@#@\\s+.+)?");
        matcher = pattern.matcher(taskPart);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }

        String status = matcher.group(1);
        String title = matcher.group(2);
        Assert.notNull(status, title);

        Task t = new Task(title, status.equals("D"));
        t.tags = tags;
        return t;
    }
}
