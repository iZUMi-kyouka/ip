package rumi.ui;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

import rumi.exception.RumiException;
import rumi.utils.Utils;

/**
 * Handles getting input from user and printing output to the terminal
 */
public class Ui {

    private final Scanner scanner;
    private final BlockingQueue<String> reader;
    private final BlockingQueue<String> writer;

    /**
     * Constructs a UI instance that reads from and writes to a BlockingQueue for asynchronous
     * operation.
     */
    public Ui(BlockingQueue<String> reader, BlockingQueue<String> writer) {
        this.reader = reader;
        this.writer = writer;
        this.scanner = null;
    }

    /**
     * Constructs a UI instance that reads from the scanner and writes to the standard output for a
     * fully terminal CLI operation.
     */
    public Ui(Scanner scanner) {
        this.scanner = scanner;
        this.reader = null;
        this.writer = null;
    }

    /**
     * Prints a formatted string with a newline.
     *
     * @param fmt format string
     * @param o arguments referenced by the format specifiers
     */
    public static void printfln(String fmt, Object... o) {
        System.out.printf(fmt + "\n", o);
    }

    /**
     * Reads a command from the reader if available, otherwise from the scanner.
     *
     * @return the next command string
     */
    public String readCommand() throws RumiException {
        String command = "";
        if (this.reader != null) {
            try {
                command = this.reader.take();
            } catch (InterruptedException e) {
                throw new RumiException("Failed to read user command: %s", e.getMessage());
            }

            return command;
        }

        return this.scanner.nextLine();
    }

    private void showOutput(String s) {
        System.out.print(s);
        showInGui(s);
    }

    private void showInGui(String s) {
        if (this.writer != null) {
            this.writer.offer(s);
        }
    }

    /**
     * Displays an warning message with [WARNING] prefix to the terminal.
     *
     * @param warning the error message
     */
    public void showWarning(String warning) {
        String w = String.format("[WARNING] %s", warning);
        showOutput(w);
    }

    /**
     * Displays an error message with [ERROR] prefix to the terminal.
     *
     * @param error the warning message
     */
    public void showError(String error) {
        String e = String.format("[ERROR] %s", error);
        showOutput(e);
    }

    /**
     * Prints and returns the response, also sending it to the writer if available.
     *
     * @param s the response text
     * @return the response text
     */
    public String printResponse(String s) {
        Utils.printIndent(Utils.boxText(s));
        showInGui(s);
        return s;
    }

    /**
     * Formats and prints a response string.
     *
     * @param fmt format string
     * @param o format arguments
     */
    public void printResponsef(String fmt, Object... o) {
        this.printResponse(String.format(fmt, o));
    }

}
