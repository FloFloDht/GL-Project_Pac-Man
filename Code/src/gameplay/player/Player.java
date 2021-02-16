package gameplay.player;

import engine.kernel.GameComponent;
import gameplay.bonus.Bonus;

public abstract class Player {

    protected GameComponent gameComponent;
    protected Bonus bonus;
    protected boolean master;
    protected int lives;

    public GameComponent getGameComponent() {
        return gameComponent;
    }

    public gameplay.bonus.Bonus getBonus() {
        return bonus;
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public int getLives() {
        return lives;
    }

    public void retireLife() {
        if(lives > 0) {
            lives --;
        }
    }

    public void addLife() {
        lives ++;
    }
}
