package engine.kernel;

import engine.universal.Coordinates;
import engine.universal.Dimension;
import engine.graphic.CoreGraphics;
import engine.graphic.graphicComponent.Sprite;
import engine.input.CoreInput;
import engine.physic.CorePhysics;
import engine.physic.Entity;
import engine.physic.movement.Movement;

import java.awt.*;

public class CoreKernel {

    private final CoreGraphics graphics;
    private final CorePhysics physics;
    private final CoreInput inputs;

    public CoreKernel(int width, int height, Color color) {
        graphics = new CoreGraphics(width, height, color);
        physics = new CorePhysics();
        inputs = new CoreInput();
        setGraphicsEvents();
    }

    public void setGraphicsEvents() {
        getGraphics().getMainScene().addKeyListener(inputs.getKeyInput());
        getGraphics().getMainScene().addMouseListener(inputs.getMouseButtonsInput());
        getGraphics().getMainScene().addMouseMotionListener(inputs.getMouseMotionInput());
    }

    public GameComponent createGameComponent(Sprite sprite, Coordinates coordinates, Dimension dimension, Movement movement, boolean passable) {
        sprite.setCoordinates(coordinates);
        sprite.setDimension(dimension);
        graphics.addSprite(sprite);

        Entity entity = new Entity(coordinates,dimension,movement,passable);
        physics.addEntity(entity);

        return new GameComponent(sprite, entity);
    }

    public void deleteGameComponent(GameComponent gameComponent) {
        graphics.removeSprite(gameComponent.getSprite());
        physics.removeEntity(gameComponent.getEntity());
    }

    public void deleteAllGameComponent() {
        graphics.getMainScene().removeAllSprites();
        physics.removeAllEntities();
    }

    public CoreGraphics getGraphics() {
        return graphics;
    }

    public CorePhysics getPhysics() {
        return physics;
    }

    public CoreInput getInputs() {
        return inputs;
    }
}
