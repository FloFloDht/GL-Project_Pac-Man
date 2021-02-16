package engine.input.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseButtonInput implements MouseListener {

    private final int NUM_BUTTONS = 5;

    private boolean[] buttons = new boolean[NUM_BUTTONS];
    private boolean[] buttonsLast = new boolean[NUM_BUTTONS];

    private int mouseX;
    private int mouseY;

    public MouseButtonInput() {}

    public void updateButtons() {
        buttonsLast = buttons;
        buttons = new boolean[NUM_BUTTONS];
        mouseX = -1;
        mouseY = -1;
    }

    public boolean isButton(int keyCode) {
        return buttons[keyCode];
    }

    public boolean isButtonUp(int keyCode) {
        return !buttons[keyCode] && buttonsLast[keyCode];
    }

    public boolean isButtonDown(int keyCode) {
        return buttons[keyCode] && !buttonsLast[keyCode];
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
