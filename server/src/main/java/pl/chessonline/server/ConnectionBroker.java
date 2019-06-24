package pl.chessonline.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionBroker {
    private int playerNo;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;


    public ConnectionBroker() throws IOException {
        playerNo = 0;
        serverSocket = new ServerSocket(21370);
        socket = serverSocket.accept();
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
    }

    public void waitForPlayers() {
        while ( playerNo <= 1) {
            try {
                boolean b = input.readBoolean();
                if (!b) {
                    throw new IOException();
                }
                if (playerNo == 0) {
                    output.writeInt(6868);
                } else {
                    output.writeInt(6969);
                }
                playerNo += 1;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void playGame() {
        Game game = new Game();
        DisconnectManager observer = new DisconnectManager(input);
        observer.subscribe(()->{game.breakGame();});
        Thread t = new Thread(game::gameLoop);
        t.start();

        try {
            observer.gameBreakObserver();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void closeConnection() throws IOException {
        input.close();
        output.flush();
        output.close();
        socket.close();
        serverSocket.close();
    }

    public void switchPortForEndgame() {
        try {
            closeConnection();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            serverSocket = new ServerSocket(6666);
            socket = serverSocket.accept();
            input = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
