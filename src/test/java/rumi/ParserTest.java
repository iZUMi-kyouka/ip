package rumi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import rumi.command.Command;
import rumi.command.DeadlineCommand;
import rumi.command.EventCommand;
import rumi.command.Parser;
import rumi.command.ToDoCommand;

public class ParserTest {

    @Test
    public void testParseTodoCommand() {
        String commandString = "todo laundry";
        Parser p = new Parser(null, null);
        Command parsedCommand = p.parse(commandString);
        Command expectedCommand = new ToDoCommand(null, null, "laundry");
        assertEquals(parsedCommand, expectedCommand);
    }

    @Test
    public void testParseDeadlineCommand() {
        String commandString = "deadline CS2103T peer review /by 10092025 10:30:00pm";
        Parser p = new Parser(null, null);
        Command parsedCommand = p.parse(commandString);
        Command expectedCommand =
                new DeadlineCommand(null, null, "CS2103T peer review", "10092025 10:30:00pm");
        assertEquals(parsedCommand, expectedCommand);
    }

    @Test
    public void testParseEventCommand() {
        String commandString = "event ZUTOMAYO concert /from 17042026 6:00pm /to 17042026 9:00pm";
        Parser p = new Parser(null, null);
        Command parsedCommand = p.parse(commandString);
        Command expectedCommand = new EventCommand(null, null, "ZUTOMAYO concert",
                "17042026 6:00pm", "17042026 9:00pm");
        assertEquals(parsedCommand, expectedCommand);
    }
}
