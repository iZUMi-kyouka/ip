package rumi;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import rumi.command.Command;
import rumi.command.EventCommand;
import rumi.command.UnknownUserCommandException;
import rumi.parser.Parser;
import rumi.tag.TagList;
import rumi.task.Event;
import rumi.task.TaskList;
import rumi.ui.Ui;

public class EventParserTest extends ParserTest {
    private static final Ui ui = new Ui(new Scanner(System.in));

    @Test
    public void parseEventCommand_validInputNoTags_expectedBehaviour() throws Exception {
        String commandString = "event ZUTOMAYO concert /from 170426 6pm /to 17-04-2026 09:00pm";

        TaskList tasks = new TaskList();
        Parser p = new Parser(tasks, ui);

        Command parsedCommand = p.parse(commandString);
        Command expectedCommand =
                new EventCommand(tasks, ui, "ZUTOMAYO concert", "170426 6pm", "17-04-2026 09:00pm");
        assertEquals(parsedCommand, expectedCommand);

        parsedCommand.run();

        Event parsedDeadline = (Event) tasks.get(1);
        Event expectedDeadline = new Event("ZUTOMAYO concert", "17/04/26 1800", "1742026 9pm");
        assertEquals(parsedDeadline, expectedDeadline);
    }

    @Test
    public void parseEventCommand_validInputWithTags_expectedBehaviour() throws Exception {
        String commandString =
                "event ZUTOMAYO concert /from 170426 6pm /to 17-04-2026 09:00pm /tags music, fun";

        TaskList tasks = new TaskList();
        Parser p = new Parser(tasks, ui);
        TagList expectedTags = makeTagList("music", "fun");

        Command parsedCommand = p.parse(commandString);
        Command expectedCommand = new EventCommand(tasks, ui, "ZUTOMAYO concert", "170426 6pm",
                "17-04-2026 09:00pm", expectedTags);
        assertEquals(parsedCommand, expectedCommand);

        parsedCommand.run();

        Event parsedDeadline = (Event) tasks.get(1);
        Event expectedDeadline =
                new Event("ZUTOMAYO concert", "17/04/26 1800", "1742026 9pm", expectedTags);
        assertEquals(parsedDeadline, expectedDeadline);
    }

    @Test
    public void parseEventCommand_invalidInput_exceptionThrown() {
        String commandString1 = "event ZUTOMAYO concert /from 21042026 /tags music";

        TaskList tasks = new TaskList();
        Parser p = new Parser(tasks, ui);

        assertThrows(UnknownUserCommandException.class, () -> p.parse(commandString1));

        String commandString2 = "event ZUTOMAYO concert /to tommorow /tags not_urgent";
        assertThrows(UnknownUserCommandException.class, () -> p.parse(commandString2));

        String commandString3 = "event ZUTOMAYO concert /to 22092026 /from 21092026";
        assertThrows(UnknownUserCommandException.class, () -> p.parse(commandString3));
    }
}
