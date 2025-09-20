package rumi.command;

import java.time.DateTimeException;

import rumi.RumiException;
import rumi.task.Deadline;
import rumi.task.TaskList;
import rumi.ui.Ui;
import rumi.utils.Assert;
import rumi.utils.Utils;

/**
 * Represents a `deadline` command.
 */
public class DeadlineCommand extends Command {

    private final TaskList tasks;
    private final Ui ui;
    private final String title;
    private final String dueDate;

    /**
     * Creates a DeadlineCommand with given a TaskList and a task number.
     */
    public DeadlineCommand(TaskList tasks, Ui ui, String title, String dueDate) {
        Assert.notNull(tasks, ui, title, dueDate);

        this.tasks = tasks;
        this.ui = ui;
        this.title = title;
        this.dueDate = dueDate;
    }

    private void showOutcome(TaskList.TaskListAddOutcome outcome, Deadline deadline) {
        String formatStr = "";
        boolean isDuplicate = outcome.equals(TaskList.TaskListAddOutcome.DUPLICATE);
        formatStr = "Right away, Master! I've added this to your to-do list:\n" + "    %s";
        if (isDuplicate) {
            formatStr +=
                    "\n\nOops! The task that you've just added seems to be duplicates of the following task(s):\n"
                            + "    %s";
        }

        formatStr += "\n\nYou now have %d task(s) awaiting your attention~";
        if (isDuplicate) {
            String duplicateTaskList = Utils
                    .indentLines(this.tasks.findPossibleDuplicates(title, deadline).toString(), 1);
            this.ui.printResponsef(formatStr, deadline, duplicateTaskList, this.tasks.size());
            return;
        }

        this.ui.printResponsef(formatStr, deadline, this.tasks.size());
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
}
