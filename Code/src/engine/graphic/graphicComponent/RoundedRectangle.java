package engine.graphic.graphicComponent;

import java.awt.*;

public class RoundedRectangle extends Sprite {

    private final boolean filled;
    private final int arcWidth;
    private final int arcHeight;

    public RoundedRectangle(Color color, double orientation, boolean filled, int arcWidth, int arcHeight) {
        super(color, orientation);
        this.filled = filled;
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
    }

    public boolean isFilled() {
        return filled;
    }

    public int getArcWidth() {
        return arcWidth;
    }

    public int getArcHeight() {
        return arcHeight;
    }
}
