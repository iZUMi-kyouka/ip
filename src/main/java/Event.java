public class Event extends Task {
    private String from;
    private String to;

    Event(String title, String from, String to) {
        super(title);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("%s (from: %s --> to: %s)", super.toString(), this.from, this.to);
    }
}
