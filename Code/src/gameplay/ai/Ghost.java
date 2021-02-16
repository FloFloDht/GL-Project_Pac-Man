package gameplay.ai;

import engine.kernel.GameComponent;
import engine.physic.movement.Movement;

import java.util.HashSet;

public class Ghost extends ArtificialIntelligence {

    private boolean scared;
    private String name;

    public Ghost(GameComponent gameComponent, HashSet<Movement> movementsAutorized, String name) {
        scared = false;
        this.name = name;
        super.gameComponent = gameComponent;
        super.movementsAuthorized = movementsAutorized;
    }

    public boolean isScared() {
        return scared;
    }

    public void setScared(boolean scared) {
        this.scared = scared;
    }

    public String getName() {
        return name;
    }
}
