package rumi.ui;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

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
    public String readCommand() {
        String command = ""; // to fix later
        if (this.reader != null) {
            try {
                command = this.reader.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return command;
        }

        return this.scanner.nextLine();
    }

    /**
     * Displays an error message with [ERROR] prefix.
     *
     * @param error the error message
     */
    public void showWarning(String error) {
        printfln("[ERROR] %s", error);
    }

    /**
     * Displays a warning message with [WARNING] prefix.
     *
     * @param warning the warning message
     */
    public void showError(String warning) {
        printfln("[WARNING] %s", warning);
    }

    /**
     * Prints and returns the response, also sending it to the writer if available.
     *
     * @param s the response text
     * @return the response text
     */
    public String printResponse(String s) {
        Utils.printIndent(Utils.boxText(s));
        if (this.writer != null) {
            this.writer.offer(s);
        }
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
