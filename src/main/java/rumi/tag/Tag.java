package rumi.tag;

import java.util.List;
import java.util.Objects;

/** Represents a tag used to add additional information to a task. */
public class Tag {
    private final String tagName;

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return String.format("[#%s]", this.tagName);
    }

    /** Returns the serialised string representation of a tag list. */
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

    public String getTagName() {
        return this.tagName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.tagName);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tag)) {
            return false;
        }

        Tag t = (Tag) o;
        return this.tagName.equals(t.tagName);
    }
}
