package pl.chessonline.client.connection;

import org.json.JSONException;

import java.io.IOException;

public class Game {
    EndGame endGame;
    Connection connection;

    public Game() throws IOException {
        Handshake handshake = new Handshake();
        connection = new Connection(handshake.getGamePort());
        endGame = new EndGame();
    }

    public void loop() throws IOException {

        if (connection.getPort() == 9898) {
            try {
                //todo potrzebuję tu wysyłay ruch
                Movement movementToSend = new Movement();
                connection.sendMessage(movementToSend);
            }
        }

        while (true) {
            try {
                Movement movement = connection.recieveMessage();
                //todo obsługa otrzymanego ruchu
                Movement movementToSend = new Movement();
                connection.sendMessage(movementToSend);
                //czekamy, albo wywalamy koniec gry
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //a przynajmniej tak to powinno działać
        }
        //todo to trzeba wrzucić gdzieś obok
        endGame.end();
    }
}
