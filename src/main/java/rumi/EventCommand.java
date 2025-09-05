package rumi;

import rumi.ui.Ui;

/**
 * Represents an `event` command.
 */
public class EventCommand extends Command {

    private final TaskList tasks;
    private final Ui ui;
    private final String title;
    private final String from;
    private final String to;

    /**
     * Creates a EventCommand with given a TaskList and a task number.
     */
    public EventCommand(TaskList tasks, Ui ui, String title, String from, String to) {
        this.tasks = tasks;
        this.ui = ui;
        this.title = title;
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        Event event = new Event(title, from, to);
        tasks.add(event);
        this.ui.printResponse(String.format(
                "Noted! I've scheduled this delightful event for you, Master~\n  %s\n"
                + "Everything is perfectly arranged~\nYou now have %d task(s)  in the list.",
                event, tasks.size()));
    }

    @Override
    public CommandType getType() {
        return CommandType.EVENT;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EventCommand)) {
            return false;
        }

        EventCommand command = (EventCommand) o;
        return command.title.equals(this.title) && command.from.equals(this.from) && command.to.equals(this.to);
    }
}
