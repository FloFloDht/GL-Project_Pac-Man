package engine.kernel;

import engine.graphic.graphicComponent.Sprite;
import engine.graphic.graphicComponent.Text;
import engine.physic.Entity;
import engine.physic.collision.CollisionObserver;
import engine.physic.collision.CollisionPassableObserver;

public class GameComponent {

    Sprite sprite;
    Entity entity;
    protected CollisionObserver collisionObserver;
    protected CollisionPassableObserver collisionPassableObserver;

    public GameComponent(Sprite sprite, Entity entity) {
        this.sprite = sprite;
        this.entity = entity;
        if(sprite.getClass().equals(Text.class))
            initTextDimensions();
        collisionObserver = new CollisionObserver();
        collisionPassableObserver = new CollisionPassableObserver();
        getEntity().getCollisionObservable().addPropertyChangeListener(collisionObserver);
        getEntity().getCollisionPassableObservable().addPropertyChangeListener(collisionPassableObserver);
    }

    public Entity getCollisionPassable() {
        return collisionPassableObserver.getEntity();
    }

    public void resetCollisionPassable() {
        entity.getCollisionPassableObservable().setEntity(null);
    }

    public boolean hasCollision() {
        return entity.getCollisionObservable().getEntity() != null;
    }

    private void initTextDimensions() {
        Text textSprite = (Text) sprite;
        textSprite.initDimension();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Entity getEntity() {
        return entity;
    }


}
