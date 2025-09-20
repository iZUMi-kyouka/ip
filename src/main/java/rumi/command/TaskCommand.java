package rumi.command;

import rumi.task.Task;
import rumi.task.TaskList;
import rumi.ui.Ui;
import rumi.utils.Utils;

/** Represents commands to add tasks */
abstract class TaskCommand extends Command {
    protected final TaskList tasks;
    protected final Ui ui;
    protected final String title;

    protected TaskCommand(TaskList tasks, Ui ui, String title) {
        this.tasks = tasks;
        this.ui = ui;
        this.title = title;
    }

    protected abstract String getSuccessMessage();

    protected void showOutcome(TaskList.TaskListAddOutcome outcome, Task task) {
        String formatStr = "";
        boolean isDuplicate = outcome.equals(TaskList.TaskListAddOutcome.DUPLICATE);
        formatStr = this.getSuccessMessage() + "\n" + "    %s";
        if (isDuplicate) {
            formatStr +=
                    "\n\nOops! The task that you've just added seems to be duplicates of the following task(s):\n"
                            + "    %s";
        }

        formatStr += "\n\nYou now have %d task(s) awaiting your attention~";
        if (isDuplicate) {
            String duplicateTaskList =
                    Utils.indentLines(this.tasks.findPossibleDuplicates(title, task).toString(), 1);
            this.ui.printResponsef(formatStr, task, duplicateTaskList, this.tasks.size());
            return;
        }

        this.ui.printResponsef(formatStr, task, this.tasks.size());
    }
}
