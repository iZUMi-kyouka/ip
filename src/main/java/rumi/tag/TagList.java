package rumi.tag;

import java.util.ArrayList;
import java.util.List;

/** Represents a list of tag that is based on an ArrayList of Tag. */
public class TagList extends ArrayList<Tag> {
    public TagList(ArrayList<Tag> tags) {
        super(tags);
    }

    /** Returns the pretty string representation of a tag list. */
    public String toStringPretty(List<Tag> list) {
        StringBuilder sb = new StringBuilder();
        for (Tag t : list) {
            sb.append(t.toString());
            sb.append(' ');
        }

        return sb.toString().trim();
    }

    /** Returns an ArrayList of Tag from a serialised string representation of a tag list. */
    public static TagList fromString(String s) {
        ArrayList<Tag> tags = new ArrayList<>();
        for (String tag : s.split(",")) {
            tags.add(new Tag(tag.trim()));
        }

        return new TagList(tags);
    }
}


