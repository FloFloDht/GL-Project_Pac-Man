package engine.input.listeners;

import engine.universal.Coordinates;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionInput implements MouseMotionListener {

    private int mouseX;
    private int mouseY;
    private final Coordinates mouseCoordinates;

    public MouseMotionInput() {
        mouseCoordinates = new Coordinates(-1,-1);
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseCoordinates.setX(e.getX());
        mouseCoordinates.setY(e.getY());
    }

    public int getMouseX() {
        return mouseCoordinates.getX();
    }

    public int getMouseY() {
        return mouseCoordinates.getY();
    }
}
