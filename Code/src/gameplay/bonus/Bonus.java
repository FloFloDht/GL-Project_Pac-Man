package gameplay.bonus;

import engine.physic.speed.Fast;
import engine.physic.speed.Normal;
import gameplay.player.Player;

public class Bonus {

    protected Player player;

    public Bonus(Player player) {
        this.player = player;
        reset();
    }

    public void reset() {
        player.setMaster(false);
        player.getGameComponent().getEntity().getMovement().setSpeed(new Normal());
    }

    public void master() {
        reset();
        player.setMaster(true);
    }

    public void flash() {
        reset();
        player.getGameComponent().getEntity().getMovement().setSpeed(new Fast());
    }

    public void reborn() {
        reset();
        player.addLife();
    }

}
