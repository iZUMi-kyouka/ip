package rumi.command;

import java.time.DateTimeException;

import rumi.RumiException;
import rumi.task.Deadline;
import rumi.task.TaskList;
import rumi.ui.Ui;
import rumi.utils.Assert;

/**
 * Represents a `deadline` command.
 */
public class DeadlineCommand extends TaskCommand {

    private final String dueDate;

    /**
     * Creates a DeadlineCommand with given a TaskList and a task number.
     */
    public DeadlineCommand(TaskList tasks, Ui ui, String title, String dueDate) {
        super(tasks, ui, title);
        Assert.notNull(tasks, ui, title, dueDate);

        this.dueDate = dueDate;
    }

    @Override
    public void run() throws RumiException {
        try {
            Deadline deadline = new Deadline(title, dueDate);
            TaskList.TaskListAddOutcome outcome = this.tasks.addTask(deadline);
            this.showOutcome(outcome, deadline);
        } catch (DateTimeException e) {
            throw RumiException.INVALID_DATE_EXCEPTION;
        }
    }

    @Override
    public CommandType getType() {
        return CommandType.DEADLINE;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DeadlineCommand)) {
            return false;
        }

        DeadlineCommand command = (DeadlineCommand) o;
        return command.title.equals(this.title) && command.dueDate.equals(this.dueDate);
    }

    @Override
    protected String getSuccessMessage() {
        return "Right away, Master! I've added this to your to-do list";
    }
}
