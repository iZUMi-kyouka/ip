package rumi.ui;

import java.util.Scanner;

import rumi.utils.Utils;

/**
 * Handles getting input from user and printing output to the terminal
 */
public class Ui {

    private final Scanner scanner;

    public Ui(Scanner scanner) {
        this.scanner = scanner;
    }

    public static void printfln(String fmt, Object... o) {
        System.out.printf(fmt + "\n", o);
    }

    public String readCommand() {
        return this.scanner.nextLine();
    }

    public void showWarning(String error) {
        printfln("[ERROR] %s", error);
    }

    public void showError(String warning) {
        printfln("[WARNING] %s", warning);
    }

    public void printResponse(String s) {
        Utils.printIndent(Utils.boxText(s));
    }
}
