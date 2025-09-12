package rumi.command;

import rumi.task.TaskList;
import rumi.task.ToDo;
import rumi.ui.Ui;
import rumi.utils.Assert;
import rumi.utils.Utils;

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
        Assert.notNull(tasks, ui, title);

        this.tasks = tasks;
        this.ui = ui;
        this.title = title;
    }

    @Override
    public void run() {
        ToDo todo = new ToDo(title);
        switch (this.tasks.addTask(todo)) {
        case ADDED -> this.ui.printResponsef(
                "Right away, Master! I've added this to your to-do list:\n"
                        + "    %s\nYou now have %d task(s) awaiting your attention~",
                todo, tasks.size());
        case DUPLICATE -> this.ui.printResponsef(
                "The task that you've just added seems to be duplicates of the following tasks:\n"
                        + "%s\nYou now have %d task(s) awaiting your attention~",
                Utils.indentLines(this.tasks.findPossibleDuplicates(title, todo).toString(), 1),
                tasks.size());
        default -> {
        }
        }
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
