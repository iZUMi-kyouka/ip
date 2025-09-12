package rumi.command;

/** Handles parsing of user commands. */
public abstract class Command {

    public void run() {};

    public abstract CommandType getType();
}
