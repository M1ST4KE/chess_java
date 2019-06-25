package pl.chessonline.client;


import pl.chessonline.client.gui.Table;
import pl.chessonline.client.model.board.Board;

public class Main {
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();
        Table table = new Table();
    }
}