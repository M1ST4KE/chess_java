package pl.chessonline.client.model;

import java.util.Collection;

public class BlackPlayer extends Player{
    public BlackPlayer(Board board, Collection<Move> whiteStandardLegalMoves, Collection<Move> blackStandardLEgalMoves) {
        super(board, blackStandardLEgalMoves, whiteStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }
}
