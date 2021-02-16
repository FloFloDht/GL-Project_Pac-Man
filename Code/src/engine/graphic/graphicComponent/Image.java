package engine.graphic.graphicComponent;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Image extends Sprite {

    private BufferedImage bufferedImage;

    public Image(Color color, double orientation, BufferedImage bufferedImage) {
        super(color, orientation);
        this.bufferedImage = bufferedImage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
