package gameplay.ai;

import engine.ai.Strategy;
import engine.kernel.GameComponent;
import engine.physic.movement.Movement;
import java.util.HashSet;

public abstract class ArtificialIntelligence {

    protected GameComponent gameComponent;
    protected Strategy strategy;
    protected HashSet<Movement> movementsAuthorized;

    public GameComponent getGameComponent() {
        return gameComponent;
    }

    public engine.ai.Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public HashSet<Movement> getMovementsAuthorized() {
        return movementsAuthorized;
    }
}
