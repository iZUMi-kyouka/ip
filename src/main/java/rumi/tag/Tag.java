package rumi.tag;

import java.util.ArrayList;
import java.util.List;

/** Represents a tag used to add additional information to a task. */
public class Tag {
    private final String tagName;

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return String.format("[ #%s ]", this.tagName);
    }

    /** Returns the string representation of a list of tag */
    public static String serialiseTagList(List<Tag> list) {
        if (list.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Tag t : list) {
            sb.append(t.tagName);
            sb.append(',');
        }

        return sb.toString().substring(0, sb.length() - 1);
    }

    public static ArrayList<Tag> tagListFromString(String s) {
        ArrayList<Tag> tags = new ArrayList<>();
        for (String tag : s.split(",")) {
            tags.add(new Tag(tag.trim()));
        }

        return tags;
    }

    /** Returns the string representation of a list of tag */
    public static String stringifyTagList(List<Tag> list) {
        StringBuilder sb = new StringBuilder();
        for (Tag t : list) {
            sb.append(t.toString());
            sb.append(' ');
        }

        return sb.toString().trim();
    }
}
