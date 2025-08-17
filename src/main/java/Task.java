public class Task {
    private String title;
    private boolean isDone;

    public Task(String title) {
        this.title = title;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmarkAsDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.isDone ? 'X' : ' ', this.title);
    }
}
