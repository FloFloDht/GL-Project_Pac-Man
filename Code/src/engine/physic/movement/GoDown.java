package engine.physic.movement;

import engine.universal.Coordinates;
import engine.physic.speed.Speed;

public class GoDown implements Movement {

    private Speed speed;

    public GoDown(Speed speed) {
        this.speed = speed;
    }

    @Override
    public void move(Coordinates coordinates) {
        coordinates.setY(coordinates.getY() + speed.applySpeed());
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "go down with speed : " + speed.toString();
    }

}
