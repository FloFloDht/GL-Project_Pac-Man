package engine.graphic.graphicComponent;

import engine.universal.Coordinates;
import engine.universal.Dimension;

import java.awt.*;

public class Sprite {

    Coordinates coordinates;
    Dimension dimension;
    Color color;
    double Orientation;

    public Sprite(Color color, double orientation) {
        this.color = color;
        Orientation = orientation;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getOrientation() {
        return Orientation;
    }

    public void setOrientation(double orientation) {
        Orientation = orientation;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
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

    public void setWidth(int width) {
        dimension.setWidth(width);
    }

    public int getHeight() {
        return dimension.getHeight();
    }

    public void setHeight(int height) {
        dimension.setHeight(height);
    }

    public String toString() {
        return "sprite : " + getCoordinates().toString() + " and : " + getDimension().toString();
    }
}
