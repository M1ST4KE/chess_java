package pl.chessonline.client.model;

import java.util.Collection;

public class WhitePlayer extends Player{
    public WhitePlayer(Board board, Collection<Move> whiteStandardLegalMoves, Collection<Move> blackStandardLEgalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLEgalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }
}
