package pl.chessonline.client.model.pieces;

import com.google.common.collect.ImmutableList;
import pl.chessonline.client.model.alliance.Alliance;
import pl.chessonline.client.model.board.Board;
import pl.chessonline.client.model.board.BoardUtils;
import pl.chessonline.client.controller.moves.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static pl.chessonline.client.controller.moves.Move.*;

public class Pawn extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATE = {8, 16, 7, 9};

    public Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.PAWN, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<Move>();
        for ( final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE){
            final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCandidateOffset);

            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){

                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
            } else if(currentCandidateOffset ==16 &&(
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
                            (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite()))) {
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * 8);

                if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }
            } else if(currentCandidateOffset == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                    (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())) )) {
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece pieceCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceAlliance != pieceCandidate.getPieceAlliance()){
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }

            }else if(currentCandidateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                     (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())) )){
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece pieceCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceAlliance != pieceCandidate.getPieceAlliance()){
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    }
                }

            }

        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }

    @Override
    public String toString(){
        return PieceType.PAWN.toString();
    }
}
