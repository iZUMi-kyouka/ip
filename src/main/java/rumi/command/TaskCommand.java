package rumi.command;

import java.util.ArrayList;

import rumi.tag.Tag;
import rumi.tag.TagList;
import rumi.task.Task;
import rumi.task.TaskList;
import rumi.ui.Ui;
import rumi.utils.Comparator;
import rumi.utils.Utils;

/**
 * Represents a class of command that creates tasks.
 */
abstract class TaskCommand extends Command {
    private static final String FORMAT_DUPLICATE_WARNING =
            "\n\nOops! The task that you've just added seems to be duplicates of the following task(s):\n"
                    + "    %s";
    private static final String FORMAT_NEW_TASK_COUNT =
            "\n\nYou now have %d task(s) awaiting your attention~";

    protected final TaskList tasks;
    protected final Ui ui;
    protected final String title;
    protected final TagList tags = new TagList();

    /**
     * Creates a TaskCommand with the specified TaskList, Ui, and title.
     */
    protected TaskCommand(TaskList tasks, Ui ui, String title) {
        this.tasks = tasks;
        this.ui = ui;
        this.title = title;
    }


    /**
     * Creates a TaskCommand with the specified task list, Ui, title, and tags. If the provided tag
     * list is null, no tags are added to the command.
     *
     * @param tags The list of tags to associate with the task, or null if no tags.
     */
    protected TaskCommand(TaskList tasks, Ui ui, String title, TagList tags) {
        this.tasks = tasks;
        this.ui = ui;
        this.title = title;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }


    /**
     * Returns the success message to display when this particular command executes successfully.
     *
     * @return The success message string for this particular command type.
     */
    protected abstract String getSuccessMessage();


    /**
     * Displays the outcome of adding a task to the task list. Shows the success message, task
     * details, and current task count. If the task is a duplicate, also displays information about
     * potential duplicates.
     *
     * @param outcome The result of the task addition operation.
     * @param task The task that was added to the task list.
     */
    protected void showOutcome(TaskList.TaskListAddOutcome outcome, Task task) {
        String formatStr;
        boolean isDuplicate = outcome.equals(TaskList.TaskListAddOutcome.DUPLICATE);

        formatStr = this.getSuccessMessage() + "\n" + "    %s";
        if (isDuplicate) {
            formatStr += FORMAT_DUPLICATE_WARNING;
        }

        formatStr += FORMAT_NEW_TASK_COUNT;

        if (isDuplicate) {
            String duplicateTaskList =
                    Utils.indentLines(this.tasks.findPossibleDuplicates(title, task).toString(), 1);
            this.ui.printResponsef(formatStr, task, duplicateTaskList, this.tasks.size());
            return;
        }

        this.ui.printResponsef(formatStr, task, this.tasks.size());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TaskCommand) || o == null) {
            return false;
        }

        TaskCommand t = (TaskCommand) o;
        return Comparator.allEqual(new Object[] {this.tags, this.title},
                new Object[] {t.tags, t.title});
    }
}
