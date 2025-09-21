package rumi.command;

import java.util.ArrayList;

import rumi.tag.Tag;
import rumi.task.TaskList;
import rumi.task.ToDo;
import rumi.ui.Ui;
import rumi.utils.Assert;

/**
 * Represents a command to create todo.
 */
public class ToDoCommand extends TaskCommand {

    /**
     * Creates a ToDo command with given a TaskList and a task number.
     */
    public ToDoCommand(TaskList tasks, Ui ui, String title) {
        this(tasks, ui, title, null);
    }

    /**
     * Creates a ToDo command with given a TaskList and a task number.
     */
    public ToDoCommand(TaskList tasks, Ui ui, String title, ArrayList<Tag> tags) {
        super(tasks, ui, title, tags);
        Assert.notNull(tasks, ui, title);
    }

    @Override
    public void run() {
        ToDo todo = new ToDo(title, tags);
        TaskList.TaskListAddOutcome outcome = this.tasks.addTask(todo);
        this.showOutcome(outcome, todo);
    }

    @Override
    public CommandType getType() {
        return CommandType.TODO;
    }

    @Override
    protected String getSuccessMessage() {
        return "Right away, Master! I've added this to your to-do list";
    }
}
