package pl.chessonline.client.model;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BoardTest {
    @Test
    public void initialBoard() {

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
}
