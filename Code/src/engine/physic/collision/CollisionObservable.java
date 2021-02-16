package engine.physic.collision;


import engine.physic.Entity;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CollisionObservable {

    private Entity entity;
    private PropertyChangeSupport support;

    public CollisionObservable() {
        support = new PropertyChangeSupport(this);
    }

    public void setEntity(Entity entity) {
        support.firePropertyChange("collision",this.entity,entity);
        this.entity = entity;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public Entity getEntity() {
        return entity;
    }
}
