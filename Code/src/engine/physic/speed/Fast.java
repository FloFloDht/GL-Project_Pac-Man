package engine.physic.speed;

public class Fast implements Speed {

    @Override
    public int applySpeed() {
        return 4;
    }

    @Override
    public String toString() {
        return "fast : " + applySpeed();
    }
}
