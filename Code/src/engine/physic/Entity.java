package engine.physic;

import engine.physic.collision.CollisionObservable;
import engine.physic.collision.CollisionPassableObservable;
import engine.physic.speed.Speed;
import engine.universal.Coordinates;
import engine.universal.Dimension;
import engine.physic.movement.Movement;

public class Entity {

    private Coordinates coordinates;
    private final Dimension dimension;
    private Movement movement;
    private boolean passable;
    private final CollisionObservable collisionObservable;
    private final CollisionPassableObservable collisionPassableObservable;

    public Entity(Coordinates coordinates, Dimension dimension, Movement movement, boolean passable) {
        this.coordinates = coordinates;
        this.dimension = dimension;
        this.movement = movement;
        this.passable = passable;
        collisionObservable = new CollisionObservable();
        collisionPassableObservable = new CollisionPassableObservable();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public int getX(){
        return coordinates.getX();
    }

    public void setX(int x){
        coordinates.setX(x);
    }

    public int getY(){
        return coordinates.getY();
    }

    public void setY(int y){
        coordinates.setY(y);
    }

    public int getWidth() {
        return dimension.getWidth();
    }

    public int getHeight() {
        return dimension.getHeight();
    }

    public boolean isPassable() {
        return passable;
    }

    public CollisionObservable getCollisionObservable() {
        return collisionObservable;
    }

    public CollisionPassableObservable getCollisionPassableObservable() {
        return collisionPassableObservable;
    }

    public Speed getSpeed() {
        return getMovement().getSpeed();
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    @Override
    public String toString() {
        return "entity : " + getCoordinates().toString() + " and : " + getDimension().toString();
    }

}
