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

    public Ui(BlockingQueue<String> reader, BlockingQueue<String> writer) {
        this.reader = reader;
        this.writer = writer;
        this.scanner = null;
    }

    public Ui(Scanner scanner) {
        this.scanner = scanner;
        this.reader = null;
        this.writer = null;
    }

    public static void printfln(String fmt, Object... o) {
        System.out.printf(fmt + "\n", o);
    }

    public String readCommand() {
        String command = ""; // to fix later
        if (this.reader != null) {
            try {
                command = this.reader.take();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return command;
        }

        return this.scanner.nextLine();
    }

    public void showWarning(String error) {
        printfln("[ERROR] %s", error);
    }

    public void showError(String warning) {
        printfln("[WARNING] %s", warning);
    }

    public String printResponse(String s) {
        Utils.printIndent(Utils.boxText(s));
        if (this.writer != null) {
            this.writer.offer(s);
        }
        return s;
    }

    public void printResponsef(String fmt, Object... o) {
        printResponse(String.format(fmt, o));
    }
}
