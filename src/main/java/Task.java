import java.util.Objects;

public class Task {
    private String title;
    private boolean isDone;

    public Task(String title) {
        assert title != null;
        this.title = title;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmarkAsDone() {
        this.isDone = false;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean getStatus() {
        return this.isDone;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.isDone ? 'X' : ' ', this.title);
    }

    public String toSerialisedString() {
        return String.format("! @#@ %s @#@ %s", this.title, this.isDone ? 'D' : 'P');
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Task task)) return false;

        return this.isDone == task.isDone && this.title.equals(task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.isDone);
    }
}
