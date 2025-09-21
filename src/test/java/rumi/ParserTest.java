package rumi;

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
import rumi.task.TaskList;
import rumi.task.ToDo;
import rumi.ui.Ui;

public class ParserTest {
    private static final Ui ui = new Ui(new Scanner(System.in));

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
    public void parseTodoCommand_invalidInput_exceptionThrown() throws Exception {
        String commandString = "todo ";

        TaskList tasks = new TaskList();
        Parser p = new Parser(tasks, ui);

        assertThrows(UnknownUserCommandException.class, () -> p.parse(commandString));
    }

    @Test
    public void testParseDeadlineCommand() {
        String commandString = "deadline CS2103T peer review /by 10092025 10:30:00pm";

        TaskList tasks = new TaskList();
        Parser p = new Parser(tasks, ui);

        Command parsedCommand = p.parse(commandString);
        Command expectedCommand =
                new DeadlineCommand(tasks, ui, "CS2103T peer review", "10092025 10:30:00pm");
        assertEquals(parsedCommand, expectedCommand);
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
