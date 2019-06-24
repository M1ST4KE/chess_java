package pl.chessonline.server;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private Boolean continueLoopFlg;

    public Game() {
        continueLoopFlg = true;
        players = new ArrayList<Player>(2);
        try {
            players.set(0, new Player("6868"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            players.set(1, new Player("6969"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
