package engine.physic.collision;

import engine.physic.Entity;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CollisionObserver implements PropertyChangeListener {

    private Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setEntity((Entity) evt.getNewValue());
    }
}
