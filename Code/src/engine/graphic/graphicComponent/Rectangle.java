package engine.graphic.graphicComponent;

import java.awt.*;

public class Rectangle extends Sprite {

    private boolean filled;

    public Rectangle(Color color, double orientation, boolean filled) {
        super(color, orientation);
        this.filled = filled;
    }

    public boolean isFilled() {
        return filled;
    }
}
