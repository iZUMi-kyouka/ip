public class Event extends Task {
    private String from;
    private String to;

    Event(String title, String from, String to) {
        super(title);
        this.from = from;
        this.to = to;

    }
}
