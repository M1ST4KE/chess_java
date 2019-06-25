package pl.chessonline.client.model;

import org.junit.Test;
import pl.chessonline.client.model.board.*;
import pl.chessonline.client.model.pieces.*;
import pl.chessonline.client.model.alliance.*;
import pl.chessonline.client.controller.moves.*;
import pl.chessonline.client.model.player.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BoardTest {

    @Test
    public void initialBoardTest() {

        final Board board = Board.createStandardBoard();
        assertFalse(board.currentPlayer().isInCheck());
        assertFalse(board.currentPlayer().isInCheckMate());
        assertFalse(board.currentPlayer().isCastled());
        assertEquals(board.currentPlayer(), board.whitePlayer());
        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
        assertFalse(board.currentPlayer().getOpponent().isInCheck());
        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
        assertFalse(board.currentPlayer().getOpponent().isCastled());
    }

    @Test
    public void simplePawnAttackTest() {

        final Board.Builder builder= new Board.Builder();

        builder.setPiece(new King(0,Alliance.WHITE));
        builder.setPiece(new King(6,Alliance.BLACK));

        builder.setPiece(new Pawn(40,Alliance.WHITE));
        builder.setPiece(new Pawn(33,Alliance.BLACK));

        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();

        int amountOfAttacksOnBlackPawn = Player.calculateAttacksOnTile(33,board.whitePlayer().getLegalMoves()).size();

        assertEquals(amountOfAttacksOnBlackPawn, 1);

    }

    @Test
    public void simpleInCheckTest() {
        final Board.Builder builder= new Board.Builder();

        builder.setPiece(new King(0,Alliance.WHITE));
        builder.setPiece(new King(6,Alliance.BLACK));

        builder.setPiece(new Rook(40,Alliance.BLACK));

        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();

        assertEquals(board.whitePlayer().isInCheck(), true);
    }

    @Test
    public void simpleNotInCheckTest() {
        final Board.Builder builder= new Board.Builder();

        builder.setPiece(new King(0,Alliance.WHITE));
        builder.setPiece(new King(6,Alliance.BLACK));

        builder.setPiece(new Rook(41,Alliance.BLACK));

        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();

        assertEquals(board.whitePlayer().isInCheck(), false);
    }

    @Test
    public void simpleBishopMovementTest() {
        final Board.Builder builder= new Board.Builder();

        builder.setPiece(new King(1,Alliance.WHITE));
        builder.setPiece(new King(6,Alliance.BLACK));

        builder.setPiece(new Bishop(63,Alliance.BLACK));

        builder.setMoveMaker(Alliance.BLACK);

        final Board board = builder.build();
        
        assertEquals(board.getTile(63).getPiece().calculateLegalMoves(board).size(), 7);
    }

    @Test
    public void simpleKnightMovementTest() {
        final Board.Builder builder= new Board.Builder();

        builder.setPiece(new King(1,Alliance.WHITE));
        builder.setPiece(new King(6,Alliance.BLACK));

        builder.setPiece(new Knight(63,Alliance.BLACK));

        builder.setMoveMaker(Alliance.BLACK);

        final Board board = builder.build();

        assertEquals(board.getTile(63).getPiece().calculateLegalMoves(board).size(), 2);
    }

    @Test
    public void simpleRookMovementTest() {
        final Board.Builder builder= new Board.Builder();

        builder.setPiece(new King(1,Alliance.WHITE));
        builder.setPiece(new King(6,Alliance.BLACK));

        builder.setPiece(new Rook(63,Alliance.BLACK));

        builder.setMoveMaker(Alliance.BLACK);

        final Board board = builder.build();

        assertEquals(board.getTile(63).getPiece().calculateLegalMoves(board).size(), 14);
    }

    @Test
    public void simpleQueenMovementTest() {
        final Board.Builder builder= new Board.Builder();

        builder.setPiece(new King(1,Alliance.WHITE));
        builder.setPiece(new King(6,Alliance.BLACK));

        builder.setPiece(new Queen(63,Alliance.BLACK));

        builder.setMoveMaker(Alliance.BLACK);

        final Board board = builder.build();

        assertEquals(board.getTile(63).getPiece().calculateLegalMoves(board).size(), 21);
    }

    @Test
    public void simpleKingMovementTest() {
        final Board.Builder builder= new Board.Builder();

        builder.setPiece(new King(1,Alliance.WHITE));
        builder.setPiece(new King(63,Alliance.BLACK));

        //builder.setPiece(new Bishop(63,Alliance.BLACK));

        builder.setMoveMaker(Alliance.BLACK);

        final Board board = builder.build();

        assertEquals(board.getTile(63).getPiece().calculateLegalMoves(board).size(), 3);
    }

    @Test
    public void simpleCheckMateTest() {
        final Board.Builder builder= new Board.Builder();

        builder.setPiece(new King(0,Alliance.WHITE));
        builder.setPiece(new King(18,Alliance.BLACK));

        builder.setPiece(new Queen(9,Alliance.BLACK));

        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();

        assertEquals(board.whitePlayer().isInCheckMate(), true);
    }

    @Test
    public void simpleStaleMateTest() {
        final Board.Builder builder= new Board.Builder();

        builder.setPiece(new King(0,Alliance.WHITE));
        builder.setPiece(new King(18,Alliance.BLACK));

        builder.setPiece(new Queen(17,Alliance.BLACK));

        builder.setMoveMaker(Alliance.WHITE);

        final Board board = builder.build();

        assertEquals(board.whitePlayer().isInStaleMate(), true);
    }


}