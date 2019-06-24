package pl.chessonline.server;

import java.lang.IllegalArgumentException;

public class Movement {
    private final int from;
    private final int to;

    public Movement(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public void veritify() throws IllegalArgumentException {
        if (from == to || from < 0 || from > 63 || to < 0 || to > 63)
            throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return String.format("{\"from\": \"%d\", \"to\": \"%d\"}", from, to);
    }
}
