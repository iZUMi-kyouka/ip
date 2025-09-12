package rumi.command;

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

    @Override
    public void run() {
        Deadline deadline = new Deadline(title, dueDate);

        switch (this.tasks.addTask(deadline)) {
        case ADDED -> this.ui.printResponsef(
                "Right away, Master! I've added this to your to-do list:\n"
                        + "    %s\nYou now have %d task(s) awaiting your attention~",
                deadline, tasks.size());
        case DUPLICATE -> this.ui.printResponsef(
                "The task that you've just added seems to be duplicates of the following tasks:\n"
                        + "    %s\nYou now have %d task(s) awaiting your attention~",
                Utils.indentLines(this.tasks.findPossibleDuplicates(title, deadline).toString(), 1),
                tasks.size());
        default -> {
        }
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
