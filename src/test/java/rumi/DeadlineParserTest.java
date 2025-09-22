package rumi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import rumi.command.Command;
import rumi.command.DeadlineCommand;
import rumi.command.UnknownUserCommandException;
import rumi.parser.Parser;
import rumi.tag.TagList;
import rumi.task.Deadline;
import rumi.task.TaskList;
import rumi.ui.Ui;

public class DeadlineParserTest extends ParserTest {
    private static final Ui ui = new Ui(new Scanner(System.in));

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
        Deadline expectedDeadline = new Deadline("CS2103T peer review", "10930 2230");
        assertEquals(parsedDeadline, expectedDeadline);
    }

    @Test
    public void parseDeadlineCommand_validInputWithTag_expectedBehaviour() throws Exception {
        String commandString =
                "deadline CS2103T peer review /by 10/09/2030 10:30pm /tags urgent,need_help,school";

        TaskList tasks = new TaskList();
        Parser p = new Parser(tasks, ui);
        TagList expectedTags = makeTagList("urgent", "need_help", "school");

        Command parsedCommand = p.parse(commandString);
        Command expectedCommand = new DeadlineCommand(tasks, ui, "CS2103T peer review",
                "10/09/2030 10:30pm", expectedTags);
        assertEquals(parsedCommand, expectedCommand);

        parsedCommand.run();

        Deadline parsedDeadline = (Deadline) tasks.get(1);
        Deadline expectedDeadline =
                new Deadline("CS2103T peer review", "10-9-2030 1030pm", expectedTags);
        assertEquals(parsedDeadline, expectedDeadline);
    }

    @Test
    public void parseDeadlineCommand_invalidInput_exceptionThrown() {
        String commandString = "deadline CS2103T peer review /by10092026 10:30:00pm";

        TaskList tasks = new TaskList();
        Parser p = new Parser(tasks, ui);

        assertThrows(UnknownUserCommandException.class, () -> p.parse(commandString));
    }
}
