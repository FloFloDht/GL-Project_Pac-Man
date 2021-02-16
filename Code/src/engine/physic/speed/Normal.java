package engine.physic.speed;

public class Normal implements Speed {

    @Override
    public int applySpeed() {
        return 2;
    }

    @Override
    public String toString() {
        return "normal : " + applySpeed();
    }
}
