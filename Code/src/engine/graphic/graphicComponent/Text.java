package engine.graphic.graphicComponent;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Text  extends Sprite {

    private String label;
    private Font font;

    public Text(Color color, double orientation, String label, Font font) {
        super(color, orientation);
        this.label = label;
        this.font = font;
    }

    public void initDimension() {
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        super.setWidth((int) getFont().getStringBounds(getText(), frc).getWidth());
        super.setHeight((int) getFont().getStringBounds(getText(), frc).getHeight());
    }

    public String getText() {
        return label;
    }

    public Font getFont() {
        return font;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
