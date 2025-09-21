package rumi.command;

import rumi.RumiException;

/** Handles parsing of user commands. */
public abstract class Command {

    /** Runs this command. */
    public void run() throws RumiException {};

    /** Returns the type of this command. */
    public abstract CommandType getType();
}
