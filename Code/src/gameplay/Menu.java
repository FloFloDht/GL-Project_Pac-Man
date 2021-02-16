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

import static gameplay.HomePage.percentageOf;

public class Menu implements Slide {

    private final CoreKernel kernel;
    private int WIDTH;
    private int HEIGHT;
    private HashSet<GameComponent> gameComponents = new HashSet<>();
    private GameComponent captureLevelOne;
    private GameComponent captureLevelTwo;
    private GameComponent captureLevelThree;
    private GameComponent titleLevelOne;
    private GameComponent titleLevelTwo;
    private GameComponent titleLevelThree;


    private int levelChosen = 0;

    public Menu(CoreKernel kernel, int WIDTH, int HEIGHT) {
        this.kernel = kernel;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    @Override
    public void display() throws IOException {
        int dimCap = percentageOf(25, HEIGHT);

        /** affichage des captures de niveaux **/
        for (int index = 3; index > 0; index --) {
            if(index == 1) {
                captureLevelOne = kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/scene/level_one.png"))),
                        new Coordinates(((WIDTH / 4) * index - (int) (dimCap * 1.2)/2),(HEIGHT / 2) - dimCap/2), new Dimension((int) (dimCap * 1.2), dimCap), new StandBy(new Neutral()), false);
                titleLevelOne = kernel.createGameComponent(new Text(Color.WHITE,0, "Level One", new Font("DialogInput",Font.PLAIN,18)),
                        new Coordinates((WIDTH / 4) * index - 50,HEIGHT - 260), new Dimension(0,0), new StandBy(new Normal()), false);

            }
            else if(index == 2) {
                captureLevelTwo = kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/scene/level_two.png"))),
                        new Coordinates(((WIDTH / 4) * index - dimCap/2),(HEIGHT / 2) - dimCap/2), new Dimension(dimCap, dimCap), new StandBy(new Neutral()), false);
                titleLevelTwo = kernel.createGameComponent(new Text(Color.WHITE,0, "Level Two", new Font("DialogInput",Font.PLAIN,18)),
                        new Coordinates((WIDTH / 4) * index - 50,HEIGHT - 260), new Dimension(0,0), new StandBy(new Normal()), false);

            }
            else if(index == 3) {
                captureLevelThree = kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/scene/level_three.png"))),
                        new Coordinates(((WIDTH / 4) * index - (int) (dimCap * 1.7)/2),(HEIGHT / 2) - dimCap/2), new Dimension((int) (dimCap * 1.7),dimCap), new StandBy(new Neutral()), false);
                titleLevelThree = kernel.createGameComponent(new Text(Color.WHITE,0, "Level Three", new Font("DialogInput",Font.PLAIN,18)),
                        new Coordinates((WIDTH / 4) * index - 55,HEIGHT - 260), new Dimension(0,0), new StandBy(new Normal()), false);

            }
        }

        GameComponent text = kernel.createGameComponent(new Text(Color.WHITE,0, "Choose a level", new Font("DialogInput",Font.PLAIN,26)),
                new Coordinates(WIDTH / 2, (HEIGHT / 4)), new Dimension(0,0), new StandBy(new Normal()), false);
        text.getSprite().setX(WIDTH / 2 - text.getSprite().getWidth() / 2);
        gameComponents.add(text);
    }

    public boolean isMouseOnLevelOne() {
        return ((kernel.getInputs().getMouseButtonsInput().getMouseX() >= captureLevelOne.getEntity().getX() &&  kernel.getInputs().getMouseButtonsInput().getMouseX() <= captureLevelOne.getEntity().getX() + captureLevelOne.getEntity().getWidth())
                && (kernel.getInputs().getMouseButtonsInput().getMouseY() >= captureLevelOne.getEntity().getY() &&  kernel.getInputs().getMouseButtonsInput().getMouseY() <= captureLevelOne.getEntity().getY() + captureLevelOne.getEntity().getHeight()));
    }

    public boolean isMouseOnLevelTwo() {
        return ((kernel.getInputs().getMouseButtonsInput().getMouseX() >= captureLevelTwo.getEntity().getX() &&  kernel.getInputs().getMouseButtonsInput().getMouseX() <= captureLevelTwo.getEntity().getX() + captureLevelTwo.getEntity().getWidth())
                && (kernel.getInputs().getMouseButtonsInput().getMouseY() >= captureLevelTwo.getEntity().getY() &&  kernel.getInputs().getMouseButtonsInput().getMouseY() <= captureLevelTwo.getEntity().getY() + captureLevelTwo.getEntity().getHeight()));
    }

    public boolean isMouseOnLevelThree() {
        return ((kernel.getInputs().getMouseButtonsInput().getMouseX() >= captureLevelThree.getEntity().getX() &&  kernel.getInputs().getMouseButtonsInput().getMouseX() <= captureLevelThree.getEntity().getX() + captureLevelThree.getEntity().getWidth())
                && (kernel.getInputs().getMouseButtonsInput().getMouseY() >= captureLevelThree.getEntity().getY() &&  kernel.getInputs().getMouseButtonsInput().getMouseY() <= captureLevelThree.getEntity().getY() + captureLevelThree.getEntity().getHeight()));
    }

    public void waitLevelChosen() throws InterruptedException {
        while(levelChosen == 0) {
            Thread.sleep(10);
            if (isMouseOnLevelOne()) {
                levelChosen = 1;
                kernel.getInputs().getMouseButtonsInput().updateButtons();
            }

            else if (isMouseOnLevelTwo()) {
                levelChosen = 2;
                kernel.getInputs().getMouseButtonsInput().updateButtons();
            }

            else if (isMouseOnLevelThree()) {
                levelChosen = 3;
                kernel.getInputs().getMouseButtonsInput().updateButtons();
            }
        }
    }

    public int getLevelChosen() {
        return levelChosen;
    }

    @Override
    public void shutdown() {
        kernel.deleteGameComponent(captureLevelOne);
        kernel.deleteGameComponent(captureLevelTwo);
        kernel.deleteGameComponent(captureLevelThree);
        kernel.deleteGameComponent(titleLevelOne);
        kernel.deleteGameComponent(titleLevelTwo);
        kernel.deleteGameComponent(titleLevelThree);

        levelChosen = 0;

        for(GameComponent gameComponent: gameComponents) {
            kernel.deleteGameComponent(gameComponent);
        }
    }
}
