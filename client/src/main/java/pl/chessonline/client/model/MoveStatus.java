package pl.chessonline.client.model;

public enum MoveStatus {
    DONE {
        @Override
        boolean isDone() {
            return true;
        }
    };
    abstract boolean isDone();
}
