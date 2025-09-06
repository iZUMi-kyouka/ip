package rumi;

import rumi.ui.Ui;

/** Represents a find command. */
public class FindCommand extends Command {
    private final TaskList tasks;
    private final Ui ui;
    private final String query;

    FindCommand(TaskList tasks, Ui ui, String query) {
        this.tasks = tasks;
        this.ui = ui;
        this.query = query;
    }

    @Override
    public void run() {
        TaskList queryResult = this.tasks.find(this.query);
        if (queryResult.isEmpty()) {
            this.ui.printResponsef(
                    "Oh no! I couldn't find any task with the word \"%s\", Master... "
                    + "Are you certain that it exists?",
                    this.query);
        } else {
            this.ui.printResponsef(
                    "Here are %s tasks that you are looking for, Master~\n%s",
                    tasks.size(), tasks);
        }
    }

    @Override
    public CommandType getType() {
        return CommandType.FIND;
    }
}
