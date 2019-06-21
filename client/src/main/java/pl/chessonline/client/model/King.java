package pl.chessonline.client.model;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static pl.chessonline.client.model.Move.*;

public class King extends Piece{
    private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};

    public King(int piecePosition, Alliance pieceAlliance) {
        super(PieceType.KING, piecePosition, pieceAlliance);
    }

    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<Move>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE){
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            if(isEightColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                    isFirstColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                continue;
            }

            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                    if(this.pieceAlliance != pieceAlliance){
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }

            }

        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString(){
        return PieceType.KING.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 ||candidateOffset == -1 ||
                candidateOffset == 7);
    }

    private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 ||
                candidateOffset == 9);
    }
}
