package engine.ai;

import engine.physic.CorePhysics;
import engine.physic.movement.Movement;
import engine.universal.Coordinates;
import java.util.HashSet;

public interface Strategy {

    public void bestChoice(Coordinates target, CorePhysics physics, HashSet<Movement> movementsAuthorized, double orientation);
}
