package engine.graphic.graphicComponent;


import java.awt.*;

public class Circle extends Sprite {

    private boolean filled;

    public Circle(Color color, double orientation, boolean filled) {
        super(color, orientation);
        this.filled = filled;
    }

    public boolean isFilled() {
        return filled;
    }
}
