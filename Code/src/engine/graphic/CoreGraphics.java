package engine.graphic;

import engine.graphic.graphicComponent.Sprite;
import javax.swing.*;
import java.awt.*;

public class CoreGraphics {

    private final JFrame mainFrame;
    private Scene mainScene;

    public CoreGraphics(int width, int height, Color color) {
        this.mainFrame = new JFrame();
        initScene(width, height, color);
        initFrame();
    }

    public void initFrame() {
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void initScene(int width, int height, Color color) {
        mainScene = new Scene(width, height, color);
        mainFrame.getContentPane().add(mainScene);
        mainFrame.pack();
    }

    public void addSprite(Sprite sprite){
        mainScene.addSprite(sprite);
    }

    public void removeSprite(Sprite sprite) {
        mainScene.removeSprite(sprite);
    }

    public Scene getMainScene() {
        return mainScene;
    }

}
