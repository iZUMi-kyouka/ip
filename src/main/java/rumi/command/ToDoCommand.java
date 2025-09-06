package rumi.command;

import rumi.task.TaskList;
import rumi.task.ToDo;
import rumi.ui.Ui;

/**
 * Represents a `todo` command.
 */
public class ToDoCommand extends Command {

    private final TaskList tasks;
    private final Ui ui;
    private final String title;

    /**
     * Creates a DeleteCommand with given a TaskList and a task number.
     */
    public ToDoCommand(TaskList tasks, Ui ui, String title) {
        this.tasks = tasks;
        this.ui = ui;
        this.title = title;
    }

    @Override
    public void run() {
        ToDo todo = new ToDo(title);
        this.tasks.add(todo);

        this.ui.printResponse(String.format(
                "Right away, Master! I've added this to your to-do list:\n"
                        + "    %s\nYou now have %d task(s) awaiting your attention~",
                todo, tasks.size()));
    }

    @Override
    public CommandType getType() {
        return CommandType.TODO;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ToDoCommand)) {
            return false;
        }

        ToDoCommand command = (ToDoCommand) o;
        return command.title.equals(command.title);
    }
}
