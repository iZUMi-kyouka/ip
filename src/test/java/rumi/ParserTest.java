package rumi;

import rumi.tag.Tag;
import rumi.tag.TagList;

public class ParserTest {
    public static TagList makeTagList(String... tagNames) {
        TagList tags = new TagList();
        for (String tagName : tagNames) {
            tags.add(new Tag(tagName));
        }

        return tags;
    }
}
