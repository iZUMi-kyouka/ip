package rumi.tag;

import java.util.ArrayList;

/** Represents a list of tag that is based on an ArrayList of Tag. */
public class TagList extends ArrayList<Tag> {

    public TagList() {
        super();
    }

    public TagList(ArrayList<Tag> tags) {
        super(tags);
    }

    /** Returns the serialised string representation of a tag list. */
    public String toSerialisedString() {
        if (this.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Tag t : this) {
            sb.append(t.getTagName());
            sb.append(',');
        }

        return sb.toString().substring(0, sb.length() - 1);
    }

    /**
     * Returns the pretty string representation of this TagList instance. NOTE: Currently UNUSED due
     * to lack of time for implementation.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Tag t : this) {
            sb.append(t.toString());
            sb.append(' ');
        }

        return sb.toString().trim();
    }

    /** Returns a TagList from the given serialised string representation of a tag list. */
    public static TagList fromString(String s) {
        ArrayList<Tag> tags = new ArrayList<>();
        for (String tag : s.split(",")) {
            tags.add(new Tag(tag.trim()));
        }

        return new TagList(tags);
    }
}


