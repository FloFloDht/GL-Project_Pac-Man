package gameplay.collectibles;

import engine.kernel.GameComponent;

public class Collectible {

    private final Type type;
    private final GameComponent gameComponent;

    public Collectible(GameComponent gameComponent, Type type) {
        this.gameComponent = gameComponent;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public GameComponent getGameComponent() {
        return gameComponent;
    }
}
