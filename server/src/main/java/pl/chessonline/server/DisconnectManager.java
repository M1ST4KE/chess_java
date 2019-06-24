package pl.chessonline.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DisconnectManager implements Observable {
    private PauseInterface pauseInterface;
    private DataInputStream inputStream;

    @Override
    public void subscribe(PauseInterface pauseInterface) {
        this.pauseInterface = pauseInterface;
    }

    public DisconnectManager(DataInputStream input) {
        inputStream = input;
    }

    public void gameBreakObserver() throws IOException {
        boolean b = true;
        while (b) {
            b = inputStream.readBoolean();
        }
        pauseInterface.sendInfo();
    }
}
