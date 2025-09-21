package rumi.command;

import rumi.exception.RumiException;

/**
 * Handles parsing of user commands, and executing the logic of the particular command.
 */
public abstract class Command {

    /**
     * Executes this command.
     *
     * @throws RumiException If an error occurs during command execution.
     */
    public void run() throws RumiException {};


    /**
     * Returns the type classification of this command.
     *
     * @return The CommandType enum value representing this command's type.
     */
    public abstract CommandType getType();
}
