package pl.chessonline.client.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EndGame {
    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;

    public EndGame() throws IOException {
        this.socket = new Socket("localhost",6666);
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    public void end() throws IOException {
        output.writeBoolean(false);
        closeConnection();
    }

    private void closeConnection() throws IOException {
        input.close();
        output.flush();
        output.close();
        socket.close();
    }
}
