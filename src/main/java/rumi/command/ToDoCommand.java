package rumi.command;

import rumi.task.TaskList;
import rumi.task.ToDo;
import rumi.ui.Ui;
import rumi.utils.Assert;

/**
 * Represents a `todo` command.
 */
public class ToDoCommand extends TaskCommand {

    /**
     * Creates a DeleteCommand with given a TaskList and a task number.
     */
    public ToDoCommand(TaskList tasks, Ui ui, String title) {
        super(tasks, ui, title);
        Assert.notNull(tasks, ui, title);
    }

    @Override
    public void run() {
        ToDo todo = new ToDo(title);
        TaskList.TaskListAddOutcome outcome = this.tasks.addTask(todo);
        this.showOutcome(outcome, todo);
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

    @Override
    protected String getSuccessMessage() {
        return "Right away, Master! I've added this to your to-do list";
    }
}
