package gameplay;

import engine.graphic.graphicComponent.Image;
import engine.graphic.graphicComponent.Text;
import engine.kernel.CoreKernel;
import engine.kernel.GameComponent;
import engine.physic.movement.StandBy;
import engine.physic.speed.Neutral;
import engine.physic.speed.Normal;
import engine.universal.Coordinates;
import engine.universal.Dimension;
import gameplay.slide.Slide;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.HashSet;

public class HomePage implements Slide {

    private final CoreKernel kernel;
    private int WIDTH;
    private int HEIGHT;
    private HashSet<GameComponent> gameComponents = new HashSet<>();

    public HomePage(CoreKernel kernel, int WIDTH, int HEIGHT) {
        this.kernel = kernel;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    @Override
    public void display() throws IOException {
        /** calcul des bonnes dimensions en fonction de la taille de la fenêtre **/
        int dimensionPacMan = percentageOf(15, HEIGHT);
        int dimensionGhost = percentageOf(8, HEIGHT);

        gameComponents.add(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/players/pac_man_yellow.png"))),
                new Coordinates((WIDTH / 2) - dimensionPacMan/2,(HEIGHT / 3) - dimensionPacMan/2), new Dimension(dimensionPacMan,dimensionPacMan), new StandBy(new Neutral()), false));

        String imageUrl = "ressources/images/enemies/phantom_orange.png";

        for (int index = 4; index > 0; index --) {
            if(index == 3)
                imageUrl = "ressources/images/enemies/phantom_cyan.png";
            else if(index == 2)
                imageUrl = "ressources/images/enemies/phantom_pink.png";
            else if(index == 1)
                imageUrl = "ressources/images/enemies/phantom_red.png";

            gameComponents.add(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource(imageUrl))),
                    new Coordinates(((WIDTH / 10) * index - dimensionGhost/2) + (WIDTH / 4),(int) (HEIGHT / 2) - dimensionGhost/2), new Dimension(dimensionGhost,dimensionGhost), new StandBy(new Neutral()), false));
        }

        GameComponent text = kernel.createGameComponent(new Text(Color.WHITE,0, "Press ENTER button", new Font("DialogInput",Font.PLAIN,22)),
                new Coordinates(WIDTH / 2, (int) (HEIGHT / 1.5)), new Dimension(0,0), new StandBy(new Normal()), false);
        text.getSprite().setX(WIDTH / 2 - text.getSprite().getWidth() / 2);

        gameComponents.add(text);
    }

    @Override
    public void shutdown() {
        for(GameComponent gameComponent: gameComponents) {
            kernel.deleteGameComponent(gameComponent);
        }
    }


    public static int percentageOf(int percentage, int total) {
        return (total * percentage) / 100;
    }

}
