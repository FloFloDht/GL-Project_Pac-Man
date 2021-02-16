package engine.input;

import engine.input.listeners.KeyInput;
import engine.input.listeners.MouseButtonInput;
import engine.input.listeners.MouseMotionInput;

public class CoreInput {

    private final KeyInput keyInput;
    private final MouseButtonInput mouseButtonInput;
    private final MouseMotionInput mouseMotionInput;

    public CoreInput() {
        keyInput = new KeyInput();
        mouseButtonInput = new MouseButtonInput();
        mouseMotionInput = new MouseMotionInput();
    }

    public KeyInput getKeyInput() {
        return keyInput;
    }

    public MouseButtonInput getMouseButtonsInput() {
        return mouseButtonInput;
    }

    public MouseMotionInput getMouseMotionInput() {
        return mouseMotionInput;
    }

    public void updateListeners() {
        keyInput.updateKeys();
        mouseButtonInput.updateButtons();
    }
}
