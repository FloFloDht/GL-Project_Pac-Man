package engine.ai;

import engine.physic.CorePhysics;
import engine.physic.Entity;
import engine.physic.movement.*;
import engine.physic.speed.Speed;
import engine.universal.Coordinates;
import java.util.HashSet;

public class Mad implements Strategy {

    protected Entity entity;

    public Mad(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void bestChoice(Coordinates target, CorePhysics physics, HashSet<Movement> movementsAuthorized, double orientation) {
        Coordinates coordinates = entity.getCoordinates();
        int prevX = coordinates.getX();
        int prevY = coordinates.getY();
        Speed previousSpeed = entity.getSpeed();
        int madChoice = randomChoice(getChoices(physics, prevX, prevY, movementsAuthorized).size());
        int index = 0;

        for(Movement movement: getChoices(physics, prevX, prevY, movementsAuthorized)) {
            if(index == madChoice) {
                movement.setSpeed(previousSpeed);
                entity.setMovement(movement);
                break;
            }
            index ++;
        }
    }

    private int randomChoice(int range) {
        return (int) (Math.random() * range);
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
}
