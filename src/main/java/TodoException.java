public class TodoException extends RumiException {
    TodoException() {
        super("Invalid syntax for todo declaration.");
    }
}
