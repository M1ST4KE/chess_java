package pl.chessonline.client.model;

public abstract class Move {
    final Board board;
    final Piece movePiece;
    final int destinationCoordinate;

    private Move(final Board board, final Piece movePiece, final int destinationCoordinate){
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movePiece = movePiece;
    }

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public static final class MajorMove extends Move {

        public MajorMove(final Board board, final Piece movePiece, final int destinationCoordinate) {
            super(board, movePiece, destinationCoordinate);
        }

    }

    public static final class AttackMove extends Move {

        final Piece attackPiece;
        public AttackMove(final Board board, final Piece movePiece,
                   final int destinationCoordinate, final Piece attackPiece) {
            super(board, movePiece, destinationCoordinate);
            this.attackPiece = attackPiece;
        }



    }
}
