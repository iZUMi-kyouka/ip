package rumi.task;

import java.util.ArrayList;

import rumi.tag.Tag;

/** Represents a general to-do item. */
public class ToDo extends Task {
    /** Constructs a pending ToDo with the given title and tags. */
    public ToDo(String title, ArrayList<Tag> tags) {
        this(title, false, tags);
    }

    /** Constructs a ToDo with the given title, status, and tags. */
    public ToDo(String title, boolean isDone, ArrayList<Tag> tags) {
        super(title, tags.toArray(new Tag[0]));

        assert !title.isEmpty();
        if (isDone) {
            this.markAsDone();
        }
    }

    ToDo(Task t) {
        super(t);
    }

    @Override
    public String toString() {
        System.out.println(this.tags);
        return String.format("[T]%s %s", super.toString(), Tag.stringifyTagList(this.tags));
    }

    /** Creates a to-do from a serialised string representing a to-do. */
    public static ToDo fromString(String s) throws ToDoStringParseException {
        Task t;

        try {
            t = Task.fromString(s);
        } catch (IllegalArgumentException e) {
            throw new ToDoStringParseException();
        }

        return new ToDo(t);
    }

    @Override
    public String toSerialisedString() {
        return String.format("T @#@ %s @#@ %s @#@ TAGS:%s", this.getStatus() ? 'D' : 'P',
                this.getTitle(), Tag.serialiseTagList(this.tags));
    }
}
