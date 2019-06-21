package pl.chessonline.client.model;

import static pl.chessonline.client.model.Board.*;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    private Move(final Board board, final Piece movedPiece, final int destinationCoordinate){
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = movedPiece;
    }

    public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public abstract Board execute();

    public static final class MajorMove extends Move {

        public MajorMove(final Board board, final Piece movePiece, final int destinationCoordinate) {
            super(board, movePiece, destinationCoordinate);
        }

        @Override
        public Board execute() {

            final Builder builder= new Builder();

            for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if(!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }

            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

            return builder.build();
        }

    }

    public static final class AttackMove extends Move {

        final Piece attackPiece;
        public AttackMove(final Board board, final Piece movePiece,
                   final int destinationCoordinate, final Piece attackPiece) {
            super(board, movePiece, destinationCoordinate);
            this.attackPiece = attackPiece;
        }


        @Override
        public Board execute() {
            return null;
        }
    }
}
