package rumi;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import rumi.command.Command;
import rumi.command.DeadlineCommand;
import rumi.command.EventCommand;
import rumi.command.ToDoCommand;
import rumi.command.UnknownUserCommandException;
import rumi.parser.Parser;
import rumi.tag.Tag;
import rumi.task.Deadline;
import rumi.task.TaskList;
import rumi.task.ToDo;
import rumi.ui.Ui;
import rumi.utils.RumiDate;

public class ParserTest {
    private static final Ui ui = new Ui(new Scanner(System.in));

    private ArrayList<Tag> makeTagList(String... tagNames) {
        ArrayList<Tag> tags = new ArrayList<>();
        for (String tagName : tagNames) {
            tags.add(new Tag(tagName));
        }

        return tags;
    }

    @Test
    public void parseTodoCommand_validInputNoTag_expectedBehaviour() throws Exception {
        String commandString = "todo laundry";

        TaskList tasks = new TaskList();
        Parser p = new Parser(tasks, ui);

        Command parsedCommand = p.parse(commandString);
        Command expectedCommand = new ToDoCommand(tasks, ui, "laundry");
        assertEquals(parsedCommand, expectedCommand);

        parsedCommand.run();

        ToDo parsedToDo = (ToDo) tasks.get(1);
        ToDo expectedToDo = new ToDo("laundry", null);
        assertEquals(parsedToDo, expectedToDo);
    }

    @Test
    public void parseTodoCommand_validInputWithTag_expectedBehaviour() throws Exception {
        String commandString = "todo laundry /tags testTag1,testTag2";

        TaskList tasks = new TaskList();
        Parser p = new Parser(tasks, ui);

        ArrayList<Tag> expectedTags = makeTagList("testTag1", "testTag2");

        Command parsedCommand = p.parse(commandString);
        Command expectedCommand = new ToDoCommand(tasks, ui, "laundry", expectedTags);
        assertEquals(parsedCommand, expectedCommand);

        parsedCommand.run();

        ToDo parsedToDo = (ToDo) tasks.get(1);
        ToDo expectedToDo = new ToDo("laundry", expectedTags);
        assertEquals(parsedToDo, expectedToDo);
    }

    @Test
    public void parseTodoCommand_invalidInput_exceptionThrown() throws Exception {
        String commandString = "todo ";

        TaskList tasks = new TaskList();
        Parser p = new Parser(tasks, ui);

        assertThrows(UnknownUserCommandException.class, () -> p.parse(commandString));
    }

    @Test
    public void parseDeadlineCommand_validInputNoTag_expectedBehaviour() throws Exception {
        String commandString = "deadline CS2103T peer review /by 10-09-2030 1030pm";

        TaskList tasks = new TaskList();
        Parser p = new Parser(tasks, ui);

        Command parsedCommand = p.parse(commandString);
        Command expectedCommand =
                new DeadlineCommand(tasks, ui, "CS2103T peer review", "10-09-2030 1030pm");
        assertEquals(parsedCommand, expectedCommand);

        parsedCommand.run();

        Deadline parsedDeadline = (Deadline) tasks.get(1);
        Deadline expectedDeadline = new Deadline("CS2103T peer review", "10-09-2030 1030pm");
        assertEquals(parsedDeadline, expectedDeadline);
    }

    @Test
    public void parseDeadlineCommand_validInputWithTag_expectedBehaviour() throws Exception {
        String commandString =
                "deadline CS2103T peer review /by 10092030 1030pm /tags urgent,need_help,school";

        TaskList tasks = new TaskList();
        Parser p = new Parser(tasks, ui);
        ArrayList<Tag> expectedTags = makeTagList("urgent", "need_help", "school");

        Command parsedCommand = p.parse(commandString);
        Command expectedCommand = new DeadlineCommand(tasks, ui, "CS2103T peer review",
                "10092030 1030pm", expectedTags);
        assertEquals(parsedCommand, expectedCommand);

        parsedCommand.run();

        Deadline parsedDeadline = (Deadline) tasks.get(1);
        Deadline expectedDeadline =
                new Deadline("CS2103T peer review", "10092030 2230", expectedTags);
        assertEquals(parsedDeadline, expectedDeadline);
    }

    @Test
    public void parseDeadlineCommand_invalidInput_exceptionThrown() {
        String commandString = "deadline CS2103T peer review /by10092025 10:30:00pm";

        TaskList tasks = new TaskList();
        Parser p = new Parser(tasks, ui);

        assertThrows(UnknownUserCommandException.class, () -> p.parse(commandString));
    }

    @Test
    public void testParseEventCommand() {
        String commandString = "event ZUTOMAYO concert /from 17042026 6:00pm /to 17042026 9:00pm";

        TaskList tasks = new TaskList();
        Parser p = new Parser(tasks, ui);

        Command parsedCommand = p.parse(commandString);
        Command expectedCommand = new EventCommand(tasks, ui, "ZUTOMAYO concert", "17042026 6:00pm",
                "17042026 9:00pm");
        assertEquals(parsedCommand, expectedCommand);
    }
}
