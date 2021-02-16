package engine.physic.movement;

import engine.universal.Coordinates;
import engine.physic.speed.Speed;

public class GoUp implements Movement {

    private Speed speed;

    public GoUp(Speed speed) {
        this.speed = speed;
    }

    @Override
    public void move(Coordinates coordinates) {
        coordinates.setY(coordinates.getY() - speed.applySpeed());
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "go up with speed : " + speed.toString();
    }
}

