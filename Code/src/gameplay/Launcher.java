package gameplay;

import java.io.IOException;

public class Launcher {

    public static void main(String[] args) throws IOException, InterruptedException {
        CoreGameplay coreGameplay = new CoreGameplay();
        coreGameplay.start();
    }
}
