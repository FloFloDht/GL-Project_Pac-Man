package engine.physic.movement;

import engine.physic.speed.Neutral;
import engine.universal.Coordinates;
import engine.physic.speed.Speed;

public class StandBy implements Movement {

    private Speed speed;

    public StandBy(Speed speed) {
        this.speed = speed;
    }

    public StandBy() {
        this.speed = new Neutral();
    }

    public void move(Coordinates coordinates) {}

    @Override
    public Speed getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(Speed speed) {}

    @Override
    public String toString() {
        return "stand by";
    }

}
