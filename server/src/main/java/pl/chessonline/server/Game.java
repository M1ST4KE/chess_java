package pl.chessonline.server;

import java.io.IOException;
import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private Boolean continueLoopFlg;

    public Game() {
        continueLoopFlg = true;
        players = new ArrayList<>(2);
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

    public void gameLoop() {
        while (continueLoopFlg) {
            try {
                Movement movement = players.get(0).recieveMessage();
                players.get(1).sendMessage(movement);
                movement = players.get(1).recieveMessage();
                players.get(0).sendMessage(movement);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }

        for (Player player : players) {
            try {
                player.closeConnection();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        continueLoopFlg = false;
    }

    public void breakGame() {
        continueLoopFlg = false;
    }
}
