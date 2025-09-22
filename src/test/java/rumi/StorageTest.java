package rumi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import rumi.storage.Storage;
import rumi.tag.Tag;
import rumi.tag.TagList;
import rumi.task.Deadline;
import rumi.task.Event;
import rumi.task.Task;
import rumi.task.TaskList;
import rumi.task.ToDo;

public class StorageTest {
    private static final String FILENAME_INPUT_VALID = ".rumi_data_SAVEFILE_TEST_INPUT";
    private static final String FILENAME_INPUT_PARTIALLY_INVALID =
            ".rumi_data_SAVEFILE_TEST_INPUT_PARTIALLY_INVALID";
    private static final String FILENAME_EXPECTED_OUTPUT =
            ".rumi_data_SAVEFILE_TEST_OUTPUT_EXPECTED";
    private static final String FILENAME_OUTPUT = ".rumi_data_SAVEFILE_TEST_OUTPUT";
    private static final String TEST_RESOURCES_PATH = "src/test/resources/";

    @Test
    public void loadTasksFromStorage_validSaveFile_expectedBehaviour() {
        Storage storage = new Storage(TEST_RESOURCES_PATH + FILENAME_INPUT_VALID);
        TaskList loadedTasks = storage.loadTasks();
        TaskList expectedTasks = new TaskList();

        TagList expectedTags = ParserTest.makeTagList("urgent");
        expectedTasks.add(new ToDo("shower", true, expectedTags));

        expectedTags = ParserTest.makeTagList("expensive", "social");
        expectedTasks.add(new Deadline(
                new Task("buy monitor", false, expectedTags.toArray(new Tag[0])), "30925 2255"));

        expectedTags = ParserTest.makeTagList("anime");
        expectedTasks.add(new Event(new Task("AFASG25", true, expectedTags.toArray(new Tag[0])),
                "121225 9am", "141225 2345"));

        assertEquals(loadedTasks, expectedTasks);
    }

    @Test
    public void loadTasksFromStorage_partiallyInvalidSaveFile_expectedBehaviour() {
        Storage storage = new Storage(TEST_RESOURCES_PATH + FILENAME_INPUT_PARTIALLY_INVALID);
        TaskList loadedTasks = storage.loadTasks();
        TaskList expectedTasks = new TaskList();

        TagList expectedTags = ParserTest.makeTagList("urgent");
        expectedTasks.add(new ToDo("shower", true, expectedTags));

        assertEquals(expectedTasks, loadedTasks);
    }

    @Test
    public void saveTasksToStorage_validSaveFile_expectedBehaviour() throws Exception {
        Storage storage = new Storage(TEST_RESOURCES_PATH + FILENAME_OUTPUT);
        TaskList expectedTasks = new TaskList();

        TagList expectedTags = ParserTest.makeTagList("urgent");
        expectedTasks.add(new ToDo("shower", true, expectedTags));

        expectedTags = ParserTest.makeTagList("expensive", "social");
        expectedTasks.add(new Deadline(
                new Task("buy monitor", false, expectedTags.toArray(new Tag[0])), "30925 2255"));

        expectedTags = ParserTest.makeTagList("anime");
        expectedTasks.add(new Event(new Task("AFASG25", true, expectedTags.toArray(new Tag[0])),
                "121225 9am", "141225 2345"));

        storage.saveTasks(expectedTasks);

        TaskList loadedTasks = storage.loadTasks();
        assertEquals(expectedTasks, loadedTasks);
        assertEquals(Files.mismatch(Path.of(TEST_RESOURCES_PATH + FILENAME_EXPECTED_OUTPUT),
                Path.of(TEST_RESOURCES_PATH + FILENAME_OUTPUT)), -1);
    }
}
