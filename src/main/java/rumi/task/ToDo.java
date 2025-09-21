package rumi.task;

import java.util.ArrayList;

import rumi.tag.Tag;

/** Represents a general to-do item. */
public class ToDo extends Task {
    public ToDo(String title) {
        super(title);
        assert !title.isEmpty();

    }

    public ToDo(String title, ArrayList<Tag> tags) {
        this(title, false, tags);
    }

    public ToDo(String title, boolean isDone, ArrayList<Tag> tags) {
        super(title, tags.toArray(new Tag[0]));

        assert !title.isEmpty();
        if (isDone) {
            this.markAsDone();
        }
    }

    ToDo(String title, boolean isDone) {
        this(title, isDone, null);
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
        Task t = Task.fromString(s);
        return new ToDo(t);
    }

    @Override
    public String toSerialisedString() {
        return String.format("T @#@ %s @#@ %s @#@ TAGS:%s", this.getStatus() ? 'D' : 'P',
                this.getTitle(), Tag.serialiseTagList(this.tags));
    }
}
