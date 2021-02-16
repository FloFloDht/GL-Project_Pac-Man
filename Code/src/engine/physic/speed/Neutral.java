package engine.physic.speed;

public class Neutral implements Speed{

    @Override
    public int applySpeed() {
        return 0;
    }

    @Override
    public String toString() {
        return "neutral : " + applySpeed();
    }

}
