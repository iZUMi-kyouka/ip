package rumi;

/** A custom exception used for Rumi-specific exceptions. */
public class RumiException extends Exception {
    RumiException(String message) {
        super(message);
    }
}
