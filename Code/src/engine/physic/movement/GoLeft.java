package engine.physic.movement;

import engine.universal.Coordinates;
import engine.physic.speed.Speed;

public class GoLeft implements Movement {

    private Speed speed;

    public GoLeft(Speed speed) {
        this.speed = speed;
    }

    @Override
    public void move(Coordinates coordinates) {
        coordinates.setX(coordinates.getX() - speed.applySpeed());
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "go left with speed : " + speed.toString();
    }

}
