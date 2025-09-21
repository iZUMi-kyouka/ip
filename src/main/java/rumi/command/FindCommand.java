package rumi.command;

import rumi.task.TaskList;
import rumi.ui.Ui;
import rumi.utils.Assert;

/** Represents a command to find tasks from the task name. */
public class FindCommand extends Command {
    private final TaskList tasks;
    private final Ui ui;
    private final String query;

    /** Constructs a 'find' command from the given tasks, ui, and query */
    public FindCommand(TaskList tasks, Ui ui, String query) {
        Assert.notNull(tasks, ui, query);

        this.tasks = tasks;
        this.ui = ui;
        this.query = query;
    }

    @Override
    public void run() {
        TaskList tasksFound = this.tasks.find(this.query);
        if (tasksFound.isEmpty()) {
            this.ui.printResponsef(
                    "Oh no! I couldn't find any task with the word \"%s\", Master...\n"
                            + "Are you certain that it exists?",
                    this.query);
        } else {
            this.ui.printResponsef("Here are %s tasks that you are looking for, Master~\n%s",
                    tasks.size(), tasksFound);
        }
    }

    @Override
    public CommandType getType() {
        return CommandType.FIND;
    }
}
