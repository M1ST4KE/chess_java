package pl.chessonline.server;


import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        try {
            ConnectionBroker broker = new ConnectionBroker();
            broker.waitForPlayers();
            broker.switchPortForEndgame();
            broker.playGame();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}