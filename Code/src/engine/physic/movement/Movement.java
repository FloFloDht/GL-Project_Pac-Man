package engine.physic.movement;

import engine.universal.Coordinates;
import engine.physic.speed.Speed;

public interface Movement {

    void move(Coordinates coordinates);
    Speed getSpeed();
    void setSpeed(Speed speed);
    String toString();
}
