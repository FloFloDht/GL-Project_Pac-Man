package engine.physic;

import java.util.HashSet;

public class CorePhysics {

    private final HashSet<Entity> entities;

    public CorePhysics() {
        this.entities = new HashSet<>();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public void moveTo(Entity entity) {
        entity.getMovement().move(entity.getCoordinates());
        if(!isEntityBlocked(entity)) {
            entity.getCollisionObservable().setEntity(null);
        }
        checkTraverse(entity);
    }

    public boolean isEntityBlocked(Entity entity1) {
        for(Entity entity2: entities) {
            if(entity1 != entity2 && !entity2.isPassable() && isTraverse(entity1,entity2))
                return true;
        }
        return false;
    }

    public void checkTraverse(Entity entity1) {
        for(Entity entity2: entities) {
            if(entity1 != entity2 && isTraverse(entity2,entity1)) {
                if(entity2.isPassable())
                    entity1.getCollisionPassableObservable().setEntity(entity2);
                else
                    entity1.getCollisionObservable().setEntity(entity2);
            }
        }
    }

    public boolean isTraverse(Entity e1, Entity e2) {
        return (((e1.getX() >= e2.getX() && e1.getX() <= e2.getX() + e2.getWidth()) || (e2.getX() >= e1.getX() && e2.getX() <= e1.getX() + e1.getWidth())) &&
                ((e1.getY() >= e2.getY() && e1.getY() <= e2.getY() + e2.getHeight()) || (e2.getY() >= e1.getY() && e2.getY() <= e1.getY() + e1.getHeight())));
    }

    public boolean isTraverseHitbox(Entity e1, Entity e2) {
        double e1X = e1.getX() + e1.getWidth()/2.5;
        double e1Y = e1.getY() + e1.getHeight()/2.5;
        double e2X = e2.getX() + e2.getWidth()/2.5;
        double e2Y = e2.getY() + e2.getHeight()/2.5;

        double e1W= e1.getWidth()/2.5;
        double e1H= e1.getHeight()/2.5;
        double e2W= e2.getWidth()/2.5;
        double e2H= e2.getHeight()/2.5;

        return (((e1X >= e2X && e1X <= e2X + e2W) || (e2X >= e1X && e2X <= e1X + e1W)) && ((e1Y >= e2Y && e1Y <= e2Y + e2H) || (e2Y >= e1Y && e2Y <= e1Y + e1H)));
    }

    public void removeAllEntities() {
        entities.clear();
    }

}
