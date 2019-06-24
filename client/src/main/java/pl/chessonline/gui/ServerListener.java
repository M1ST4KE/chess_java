package pl.chessonline.gui;

import org.json.JSONException;
import org.json.JSONObject;
import pl.chessonline.client.connection.Movement;

import java.io.DataInputStream;
import java.io.IOException;

public class ServerListener implements Observable {
    private ServerListenerInterface serverListenerInterface;
    private DataInputStream inputStream;

    @Override
    public void subscribe(ServerListenerInterface serverListenerInterface) {
        this.serverListenerInterface = serverListenerInterface;
    }

    public ServerListener(DataInputStream inputStream) {
        this.inputStream = inputStream;
    }

    void serverMovementListener() throws IOException, JSONException {
        JSONObject json = new JSONObject((String)inputStream.readUTF());
        int from = json.getInt("from");
        int to = json.getInt("to");

        Movement movement = new Movement(from, to);

        serverListenerInterface.sendInfo();
    }
}
