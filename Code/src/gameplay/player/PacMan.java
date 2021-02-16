package gameplay.player;

import engine.kernel.GameComponent;
import gameplay.bonus.Bonus;

public class PacMan extends Player {

    public PacMan(GameComponent gameComponent, int lives) {
        super.gameComponent = gameComponent;
        super.lives = lives;
        super.master = false;
        super.bonus = new Bonus(this);
    }
}
