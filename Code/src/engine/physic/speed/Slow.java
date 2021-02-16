package engine.physic.speed;

public class Slow implements Speed {

    @Override
    public int applySpeed() {
        return 1;
    }

    @Override
    public String toString() {
        return "slow : " + applySpeed();
    }
}
