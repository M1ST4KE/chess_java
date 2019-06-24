package pl.chessonline.client.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Handshake {
    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;

    public Handshake() throws IOException {
        this.socket = new Socket("localhost",21370);
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    public Integer getGamePort() throws IOException {
        output.writeBoolean(true);
        return input.readInt();
    }

    public void closeConnection() throws IOException {
        input.close();
        output.flush();
        output.close();
        socket.close();
    }
}
