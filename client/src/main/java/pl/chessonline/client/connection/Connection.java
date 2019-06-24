package pl.chessonline.client.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class Connection {
    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;

    public Connection(String port) throws IOException {
        this.socket = new Socket("localhost",Integer.parseInt(port));
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    public Movement recieveMessage() throws IOException, JSONException {
        JSONObject movement = new JSONObject((String)input.readUTF());
        int from = movement.getInt("from");
        int to = movement.getInt("to");
        return new Movement(from, to);
    }

    /**
     * @param movement movement to send to client
     * @throws IOException when impossible to write
     */
    public void sendMessage(Movement movement) throws IOException {
        output.flush();
        output.writeUTF(movement.toString());
        output.flush();
    }

    /**
     * @throws IOException when connection already closed
     */
    public void closeConnection() throws IOException {
        input.close();
        output.flush();
        output.close();
        socket.close();
    }

}
