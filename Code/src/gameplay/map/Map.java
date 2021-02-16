package gameplay.map;

import java.io.IOException;

public interface Map {

    void generateMap() throws IOException;
    void updateMap() throws IOException, InterruptedException;
    void listenInputs();
    boolean isQuit();
    boolean isFailure();
    boolean isSuccess();
    void shutdown();
}
