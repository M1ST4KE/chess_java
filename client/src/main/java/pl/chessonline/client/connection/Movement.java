package pl.chessonline.client.connection;

public class Movement {
    private final int from;
    private final int to;

    public Movement(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("{\"from\": \"%d\", \"to\": \"%d\"}", from, to);
}
}
