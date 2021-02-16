package engine.graphic;


import engine.graphic.graphicComponent.Image;
import engine.graphic.graphicComponent.Rectangle;
import engine.graphic.graphicComponent.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class Scene extends JPanel {

    private final HashSet<Sprite> sprites = new HashSet<>();

    public Scene(int width, int height, Color color) {
        setFocusable(true);
        requestFocusInWindow();
        setPreferredSize(new Dimension(width,height));
        setVisible(true);
        setBackground(color);
    }

    public void addSprite(Sprite sprite) {
        this.sprites.add(sprite);
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
        repaint();
    }

    public void removeAllSprites() {
        sprites.clear();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for(Sprite sprite: sprites) {
            Graphics2D graphics2D = (Graphics2D) graphics.create();
            graphics2D.setColor(sprite.getColor());
            graphics2D.rotate(Math.toRadians(sprite.getOrientation()), sprite.getX() + sprite.getWidth() / 2.0, sprite.getY() + sprite.getHeight() / 2.0);

            if (sprite.getClass().equals(Circle.class)) {
                Circle circle = (Circle) sprite;
                if (circle.isFilled())
                    graphics2D.fillOval(circle.getX(), circle.getY(), circle.getWidth(), circle.getHeight());
                else
                    graphics2D.drawOval(circle.getX(), circle.getY(), circle.getWidth(), circle.getHeight());
            }

            else if (sprite.getClass().equals(Rectangle.class)) {
                Rectangle rectangle = (Rectangle) sprite;
                if (rectangle.isFilled())
                    graphics2D.fillRect(rectangle.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
                else
                    graphics2D.drawRect(rectangle.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

            }

            else if (sprite.getClass().equals(RoundedRectangle.class)) {
                RoundedRectangle roundedRectangle = (RoundedRectangle) sprite;
                if (roundedRectangle.isFilled())
                    graphics2D.fillRoundRect(roundedRectangle.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), roundedRectangle.getArcWidth(), roundedRectangle.getArcHeight());
                else
                    graphics2D.drawRoundRect(roundedRectangle.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), roundedRectangle.getArcWidth(), roundedRectangle.getArcHeight());
            }

            else if (sprite.getClass().equals(Arc.class)) {
                Arc arc = (Arc) sprite;
                if (arc.isFilled())
                    graphics2D.fillArc(arc.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), arc.getStartAngle(), arc.getExtent());
                else
                    graphics2D.drawArc(arc.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), arc.getStartAngle(), arc.getExtent());
            }

            else if(sprite.getClass().equals(Text.class)) {
                Text text = (Text) sprite;
                graphics2D.setFont(text.getFont());
                graphics2D.drawString(text.getText(), text.getX(), text.getY());
            }

            else if(sprite.getClass().equals(Image.class)) {
                Image image = (Image) sprite;
                graphics2D.drawImage(image.getBufferedImage(),image.getX(),image.getY(),image.getWidth(),image.getHeight(),null);
            }
        }

    }

}
