package engine.ai;

import engine.physic.CorePhysics;
import engine.physic.Entity;
import engine.physic.movement.*;
import engine.physic.speed.Speed;
import engine.universal.Coordinates;
import java.util.HashSet;

public class Ambusher implements Strategy {

    protected Entity entity;

    public Ambusher(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void bestChoice(Coordinates target, CorePhysics physics, HashSet<Movement> movementsAuthorized, double orientation) {
        Coordinates coordinates = entity.getCoordinates();
        int prevX = coordinates.getX();
        int prevY = coordinates.getY();
        int bestGap = Integer.MAX_VALUE;
        Speed previousSpeed = entity.getSpeed();
        Movement bestMovement = new StandBy();

        for(Movement movement: getChoices(physics, prevX, prevY, movementsAuthorized)) {
            entity.setMovement(movement);
            physics.moveTo(entity);

            if(getGap(entity.getCoordinates(), anticipateTarget(target, orientation)) < bestGap) {
                bestGap = getGap(entity.getCoordinates(), anticipateTarget(target, orientation));
                bestMovement = movement;
            }
            bestMovement.setSpeed(previousSpeed);
            entity.setX(prevX);
            entity.setY(prevY);
            entity.setMovement(bestMovement);
        }
    }

    private Coordinates anticipateTarget(Coordinates target, double orientation) {
        Coordinates anticipatedTarget = new Coordinates(target.getX(), target.getY());
        if(orientation == 0)
            anticipatedTarget.setX(target.getX() + 100);
        else if (orientation ==  90)
            anticipatedTarget.setY(target.getY() + 100);
        else if (orientation ==  180)
            anticipatedTarget.setY(target.getX() - 100);
        else if (orientation ==  270)
            anticipatedTarget.setY(target.getY() - 100);
        return anticipatedTarget;
    }

    private HashSet<Movement> getChoices(CorePhysics physics, int prevX, int prevY, HashSet<Movement> movementsAuthorized) {
        HashSet<Movement> choices = new HashSet<>();
        Movement previousMovement = entity.getMovement();

        for(Movement movement: movementsAuthorized) {
            entity.setMovement(movement);
            physics.moveTo(entity);

            if (entity.getCollisionObservable().getEntity() == null) {
                if (previousMovement.getClass().equals(GoDown.class) && !movement.getClass().equals(GoUp.class)
                        || previousMovement.getClass().equals(GoUp.class) && !movement.getClass().equals(GoDown.class)
                        || previousMovement.getClass().equals(GoLeft.class) && !movement.getClass().equals(GoRight.class)
                        || previousMovement.getClass().equals(GoRight.class) && !movement.getClass().equals(GoLeft.class)
                        || previousMovement.getClass().equals(StandBy.class)) {
                    choices.add(movement);
                }
            }
            entity.setX(prevX);
            entity.setY(prevY);
        }
        if(choices.isEmpty())
            choices.add(new StandBy());

        entity.setMovement(previousMovement);
        return choices;
    }

    private int getGap(Coordinates c1, Coordinates c2) {
        return (Math.abs(c1.getX() - c2.getX()) + Math.abs(c1.getY() - c2.getY()));
    }
}
