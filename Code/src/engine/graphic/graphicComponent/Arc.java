package engine.graphic.graphicComponent;

import java.awt.*;

public class Arc extends Sprite {

    private boolean filled;
    private int startAngle;
    private int extent;

    public Arc(Color color, double orientation, boolean filled, int startAngle, int extent) {
        super(color, orientation);
        this.filled = filled;
        this.startAngle = startAngle;
        this.extent = extent;
    }

    public boolean isFilled() {
        return filled;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public int getExtent() {
        return extent;
    }
}
