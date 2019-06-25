package pl.chessonline.client;


import pl.chessonline.gui.Table;
import pl.chessonline.client.model.board.Board;

public class Main {
    public static void main(String[] args) {
        System.out.println("TESTUJEMY");
        Board board = Board.createStandardBoard();
        System.out.println(board);
        Table table = new Table();
    }
}