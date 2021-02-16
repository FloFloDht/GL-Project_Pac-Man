package gameplay;

import engine.ai.Ambusher;
import engine.ai.Escape;
import engine.ai.Mad;
import engine.ai.Stalker;
import engine.graphic.graphicComponent.Circle;
import engine.graphic.graphicComponent.Image;
import engine.graphic.graphicComponent.RoundedRectangle;
import engine.graphic.graphicComponent.Text;
import engine.kernel.CoreKernel;
import engine.kernel.GameComponent;
import engine.physic.movement.*;
import engine.physic.speed.Normal;
import engine.physic.speed.Speed;
import engine.universal.Coordinates;
import engine.universal.Dimension;
import gameplay.ai.Ghost;
import gameplay.collectibles.Collectible;
import gameplay.collectibles.Type;
import gameplay.map.Map;
import gameplay.player.PacMan;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashSet;

public class LevelThree implements Map {

    private final CoreKernel kernel;
    private boolean quit = false;
    private boolean failure = false;
    private boolean success = false;
    private boolean reset = false;
    private final int step;
    private final Dimension dimension;
    private final Coordinates coordinatesBorder;
    private final HashSet<Collectible> collectibles = new HashSet<>();
    private final HashSet<Ghost> ghosts = new HashSet<>();
    private final HashSet<GameComponent> walls = new HashSet<>();
    private PacMan playerOne;
    private GameComponent scorePlayer1;
    private GameComponent livesPlayerOne;
    private int score;
    private boolean scared = false;


    public LevelThree(int step, CoreKernel kernel, int windowWidth, int windowHeight) throws IOException {
        this.kernel = kernel;
        this.step = step;
        dimension = new Dimension(28 * this.step, 18 * this.step);
        coordinatesBorder = new Coordinates((windowWidth - dimension.getWidth())/2 - 8, (int)((windowHeight - dimension.getHeight())/1.8));
        score = 0;
        initTexts(windowWidth, windowHeight);
    }

    private void initTexts(int windowWidth, int windowHeight) throws IOException {
        scorePlayer1 = kernel.createGameComponent(new Text(Color.WHITE,0, "score : " + score, new Font("DialogInput",Font.PLAIN,18)),
                new Coordinates(100,windowHeight - 20), new Dimension(0,0), new StandBy(new Normal()), false);

        livesPlayerOne = kernel.createGameComponent(new Text(Color.WHITE,0, "lives : " + 2, new Font("DialogInput",Font.PLAIN,18)),
                new Coordinates(windowWidth - 200,windowHeight - 20), new Dimension(0,0), new StandBy(new Normal()), false);
    }

    @Override
    public void generateMap() throws IOException {
        int x1 = coordinatesBorder.getX(), y1 = coordinatesBorder.getY();
        int x2 = x1 + 8, y2 = y1 + 8;

        /** murs **/
        initWalls(x1, y1, x2, y2);

        /** collectible **/
        initCollectibles(x2, y2);

        /** joueur **/
        initPlayer(x2,y2,2);

        /** fantômes **/
        initGhosts(x2, y2);
    }

    private void initWalls(int x1, int y1, int x2, int y2) {
        Color customPurple = new Color(129,50,229);

        /** Création des murs extérieurs **/
        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x1, y1), new Dimension(dimension.getWidth() + 16, 8),
                new StandBy(), false));
        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x1, y1), new Dimension(8, dimension.getHeight() + 16),
                new StandBy(), false));
        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x1, y1 + dimension.getHeight() + 8), new Dimension(dimension.getWidth() + 16, 8),
                new StandBy(), false));
        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x1 + dimension.getWidth() + 8, y1), new Dimension(8, dimension.getHeight() + 16),
                new StandBy(), false));

        /** Création des blocks **/
        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + step, y2 + step), new Dimension(step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + step, y2 + 3 * step), new Dimension(step, step * 3),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + step, y2 + 7 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + step, y2 + 10 * step), new Dimension(step * 2, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + step, y2 + 13 * step), new Dimension(step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + step, y2 + 15 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 3 * step, y2 + step), new Dimension(step, step * 3),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 5 * step, y2 + step), new Dimension(step * 3, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 9 * step, y2 + step), new Dimension(step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 9 * step, y2 + step), new Dimension(step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 11 * step, y2 + step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 12 * step, y2 + step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 14 * step, y2 + step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 16 * step, y2 + step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 18 * step, y2 + step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 21 * step, y2 + step), new Dimension(step * 2, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 24 * step, y2 + step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 26 * step, y2 + step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 5 * step, y2 + 3 * step), new Dimension(step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 7 * step, y2 + 3 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 10 * step, y2 + 3 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 18 * step, y2 + 3 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 10 * step, y2 + 4 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 13 * step, y2 + 4 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 14 * step, y2 + 4 * step), new Dimension(step, step * 4),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 16 * step, y2 + 4 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 16 * step, y2 + 5 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 19 * step, y2 + 3 * step), new Dimension(step, step * 3),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 21 * step, y2 + 4 * step), new Dimension(step * 3, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 21 * step, y2 + 4 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 25 * step, y2 + 4 * step), new Dimension(step * 2, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 26 * step, y2 + 5 * step), new Dimension(step, step * 3),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 3 * step, y2 + 5 * step), new Dimension(step * 2, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 6 * step, y2 + 5 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 8 * step, y2 + 5 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 10 * step, y2 + 6 * step), new Dimension(step * 3, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 14 * step, y2 + 7 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 17 * step, y2 + 7 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 18 * step, y2 + 7 * step), new Dimension(step, step * 3),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 20 * step, y2 + 7 * step), new Dimension(step, step * 3),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 22 * step, y2 + 7 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 22 * step, y2 + 7 * step), new Dimension(step * 3, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 23 * step, y2 + 6 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 3 * step, y2 + 8 * step), new Dimension(step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 5 * step, y2 + 8 * step), new Dimension(step * 3, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 6 * step, y2 + 8 * step), new Dimension(step, step * 4),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 9 * step, y2 + 8 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 12 * step, y2 + 8 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 12 * step, y2 + 9 * step), new Dimension(step * 3, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 16 * step, y2 + 9 * step), new Dimension(step, step * 3),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 15 * step, y2 + 11 * step), new Dimension(step * 4, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 18 * step, y2 + 11 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 20 * step, y2 + 11 * step), new Dimension(step * 3, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 22 * step, y2 + 10 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 24 * step, y2 + 9 * step), new Dimension(step, step * 3),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 26 * step, y2 + 9 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 26 * step, y2 + 12 * step), new Dimension(step, step * 3),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 24 * step, y2 + 16 * step), new Dimension(step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 23 * step, y2 + 13 * step), new Dimension(step * 2, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 20 * step, y2 + 13 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 18 * step, y2 + 14 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 18 * step, y2 + 15 * step), new Dimension(step * 2, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 21 * step, y2 + 15 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 21 * step, y2 + 16 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 16 * step, y2 + 16 * step), new Dimension(step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 15 * step, y2 + 13 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 16 * step, y2 + 13 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 14 * step, y2 + 15 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 12 * step, y2 + 11 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 13 * step, y2 + 11 * step), new Dimension(step, step * 3),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 10 * step, y2 + 13 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 11 * step, y2 + 15 * step), new Dimension(step * 2, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 11 * step, y2 + 13 * step), new Dimension(step, step * 3),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 9 * step, y2 + 15 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 6 * step, y2 + 16 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 7 * step, y2 + 15 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 3 * step, y2 + 15 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 3 * step, y2 + 16 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 4 * step, y2 + 10 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 3 * step, y2 + 13 * step), new Dimension(step * 3, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 5 * step, y2 + 13 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 8 * step, y2 + 10 * step), new Dimension(step, step * 4),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 7 * step, y2 + 13 * step), new Dimension(step * 2, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 10 * step, y2 + 10 * step), new Dimension(step, step * 2),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customPurple,0,true, 8,8),
                new Coordinates(x2 + 26 * step, y2 + 16 * step), new Dimension(step, step),
                new StandBy(), false));

    }

    private void initCollectibles(int x, int y) throws IOException {
        int fruitGap = 10;
        int fruitDim = step - fruitGap;
        int fruitCoo = fruitGap/2;

        int gumGap = 25;
        int gumDim = step - gumGap;
        int gumCoo = gumGap/2;

        /** pommes **/
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/apple.png"))),
                new Coordinates(x + 6 * step + fruitCoo,y + 13 * step + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.APPLE));
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/apple.png"))),
                new Coordinates(x + 19 * step + fruitCoo,y + 14 * step + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.APPLE));
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/apple.png"))),
                new Coordinates(x + 4 * step + fruitCoo,y + 3 * step + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.APPLE));

        /** cerises **/
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/cerise.png"))),
                new Coordinates(x + fruitCoo,y + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.CERISE));
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/cerise.png"))),
                new Coordinates(x + 27 * step + fruitCoo,y + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.CERISE));
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/cerise.png"))),
                new Coordinates(x + fruitCoo,y + 17 * step + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.CERISE));
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/cerise.png"))),
                new Coordinates(x + 27 * step + fruitCoo,y + 17 * step + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.CERISE));

        /** grappes **/
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/grape.png"))),
                new Coordinates(x + 13 * step + fruitCoo,y + 16 * step + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.GRAPE));

        /** oranges **/
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/orange.png"))),
                new Coordinates(x + step + fruitCoo,y + 9 * step + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.ORANGE));
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/orange.png"))),
                new Coordinates(x + step * 26 + fruitCoo,y + step * 8 + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.ORANGE));

        /** fraises **/
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/strawberry.png"))),
                new Coordinates(x + step * 14 + fruitCoo,y + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.STRAWBERRY));


        /** gums **/
        for(int index = 0; index < 18; index ++) {
            if(index > 0 && index < 17) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 27 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if(index == 2 || index == 6 || index == 12 || index == 14) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if(index != 10 && index != 11 && index != 16 && index != 1) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 2 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if(index == 4 || index == 7 || (index >= 9 && index <= 12) || index == 14) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 3 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if(index == 1 || index == 2 || index == 4 || (index >= 7 && index <= 9) || index == 12 || index == 14 || index == 15) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 4 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if(index == 2 || (index >= 4 && index <= 7) || (index >= 9 && index <= 12) || index == 15 || index == 16) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 5 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 2 && index <= 4) || index == 7 || index == 12 || index == 14 || index == 15) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 6 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 9 && index <= 12) || (index >= 4 && index <= 7) || index == 2 || index == 14) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 7 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 7 && index <= 9) || (index >= 14 && index <= 16) || index == 1 || index == 2 || index == 4) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 8 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 2 && index <= 7) || (index >= 9 && index <= 14)) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 9 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 14 && index <= 16) || index == 1 || index == 2 || index == 5 || index == 7 || index == 9 || index == 12) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 10 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 7 && index <= 12) || index == 2 || index == 3 || index == 5) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 11 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 3 && index <= 5) || index == 7 || index == 10 || (index >= 12 && index <= 14)) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 12 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 1 && index <= 3) || (index >= 14 && index <= 15) || index == 10 || (index >= 5 && index <= 8)) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 13 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 10 && index <= 13)) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 14 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 1 && index <= 6) || (index >= 8 && index <= 10) || (index >= 14 && index <= 16) || index == 12) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 15 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 6 && index <= 8) || index == 3 || index == 12 || index == 15) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 16 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 1 && index <= 4) || (index >= 8 && index <= 10) || (index >= 12 && index <= 16) || index == 6) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 17 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 4 && index <= 6) || index == 2 || index == 10 || index == 13) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 18 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 6 && index <= 13) || index == 2) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 19 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 3 && index <= 6) || (index >= 14 && index <= 16) || index == 1 || index == 2 || index == 10 || index == 12) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 20 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 6 && index <= 10) || index == 3 || index == 12 || index == 14) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 21 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 12 && index <= 15) || index == 3 || index == 5 || index == 6 || index == 9) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 22 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 1 && index <= 3) || (index >= 8 && index <= 12) || index == 5 || index == 15 || index == 16) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 23 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 3 && index <= 6) || index == 8 || index == 12 || index == 15) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 24 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 6 && index <= 15) || index == 2 || index == 3) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 25 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if(index == 3 || index == 11 || index == 15) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + 26 * step + gumCoo,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }
        }

        for(int index = 0; index < 28; index ++) {
            if (index > 0 && index < 27) {
                if(index != 14) {
                    collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE, 0, true),
                            new Coordinates(x + index * step + gumCoo, y + gumCoo), new Dimension(gumDim, gumDim), new StandBy(), true), Type.GUM));
                }
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE, 0, true),
                        new Coordinates(x + index * step + gumCoo, y + 17 * step + gumCoo), new Dimension(gumDim, gumDim), new StandBy(), true), Type.GUM));
            }
        }
    }

    private void initPlayer(int x, int y, int lifePoints) throws IOException {
        playerOne = new PacMan(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/players/pac_man_yellow.png"))),
                new Coordinates(x + 14 * step + 8, y + 8 * step + 8), new Dimension(step - 13, step - 13), new StandBy(new Normal()),true), lifePoints);
    }

    private void initGhosts(int x, int y) throws IOException {
        HashSet<Movement> movementsAuthorized = new HashSet<>();
        movementsAuthorized.add(new GoRight(new Normal()));
        movementsAuthorized.add(new GoDown(new Normal()));
        movementsAuthorized.add(new GoLeft(new Normal()));
        movementsAuthorized.add(new GoUp(new Normal()));

        ghosts.add(new Ghost(kernel.createGameComponent(new Image(Color.WHITE, 0, ImageIO.read(getClass().getResource("ressources/images/enemies/phantom_yellow.png"))),
                new Coordinates(x + 2 * step + 1,y + step + 1), new Dimension(step - 3, step - 3), new StandBy(new Normal()), true), movementsAuthorized, "yellow"));

        ghosts.add(new Ghost(kernel.createGameComponent(new Image(Color.WHITE, 0, ImageIO.read(getClass().getResource("ressources/images/enemies/phantom_cyan.png"))),
                new Coordinates(x + 2 * step + 1,y + 16 * step + 1), new Dimension(step - 3, step - 3), new StandBy(new Normal()), true), movementsAuthorized, "cyan"));

        ghosts.add(new Ghost(kernel.createGameComponent(new Image(Color.WHITE, 0, ImageIO.read(getClass().getResource("ressources/images/enemies/phantom_pink.png"))),
                new Coordinates(x + 25 * step + 1,y + step + 1), new Dimension(step - 3, step - 3), new StandBy(new Normal()), true), movementsAuthorized, "pink"));

        ghosts.add(new Ghost(kernel.createGameComponent(new Image(Color.WHITE, 0, ImageIO.read(getClass().getResource("ressources/images/enemies/phantom_green.png"))),
                new Coordinates(x + 25 * step + 1,y + 16 * step + 1), new Dimension(step - 3, step - 3), new StandBy(new Normal()), true), movementsAuthorized, "green"));

        ghosts.add(new Ghost(kernel.createGameComponent(new Image(Color.WHITE, 0, ImageIO.read(getClass().getResource("ressources/images/enemies/phantom_red.png"))),
                new Coordinates(x + 14 * step + 1,y + 3 * step + 1), new Dimension(step - 3, step - 3), new StandBy(new Normal()), true), movementsAuthorized, "red"));

        ghosts.add(new Ghost(kernel.createGameComponent(new Image(Color.WHITE, 0, ImageIO.read(getClass().getResource("ressources/images/enemies/phantom_blue.png"))),
                new Coordinates(x + 14 * step + 1,y + 14 * step + 1), new Dimension(step - 3, step - 3), new StandBy(new Normal()), true), movementsAuthorized, "blue"));

        for(Ghost ghost : ghosts) {
            if(ghost.getName().equals("red") || ghost.getName().equals("blue") || ghost.getName().equals("green"))
                ghost.setStrategy(new Stalker(ghost.getGameComponent().getEntity()));
            else if(ghost.getName().equals("pink") || ghost.getName().equals("yellow"))
                ghost.setStrategy(new Ambusher(ghost.getGameComponent().getEntity()));
            else if(ghost.getName().equals("cyan"))
                ghost.setStrategy(new Mad(ghost.getGameComponent().getEntity()));
        }
    }

    @Override
    public void updateMap() throws IOException {
        movePlayerOne();
        isCollectibleEaten();
        moveAIs();
        isGhostTouched();

        if(reset) {
            reset();
            reset = false;
        }
    }

    private void moveAIs() {
        for(Ghost ghost : ghosts) {
            ghost.getStrategy().bestChoice(playerOne.getGameComponent().getEntity().getCoordinates(), kernel.getPhysics(), ghost.getMovementsAuthorized(), playerOne.getGameComponent().getSprite().getOrientation());
            kernel.getPhysics().moveTo(ghost.getGameComponent().getEntity());
        }
    }

    private void movePlayerOne() {
        if(!playerOne.getGameComponent().getEntity().getMovement().getClass().equals(StandBy.class)) {
            int prevX = playerOne.getGameComponent().getEntity().getX();
            int prevY = playerOne.getGameComponent().getEntity().getY();
            Speed previousSpeed = playerOne.getGameComponent().getEntity().getSpeed();
            kernel.getPhysics().moveTo(playerOne.getGameComponent().getEntity());

            if((playerOne.getGameComponent().hasCollision() && !playerOne.isMaster()) || exceedLimits()) {
                playerOne.getGameComponent().getEntity().setX(prevX);
                playerOne.getGameComponent().getEntity().setY(prevY);
                playerOne.getGameComponent().getEntity().setMovement(new StandBy(previousSpeed));
            }
        }
    }

    private boolean exceedLimits() {
        int posX = playerOne.getGameComponent().getEntity().getX();
        int posY = playerOne.getGameComponent().getEntity().getY();

        return !(posX > coordinatesBorder.getX() + 8 && posX < coordinatesBorder.getX() - 16 + dimension.getWidth()
                && posY > coordinatesBorder.getY() + 8 && posY < coordinatesBorder.getY() - 16  + dimension.getHeight());
    }

    private void isCollectibleEaten() throws IOException {
        for(Collectible collectible: collectibles) {
            if(playerOne.getGameComponent().getCollisionPassable() == collectible.getGameComponent().getEntity()) {
                kernel.deleteGameComponent(collectible.getGameComponent());

                if(collectible.getType().equals(Type.APPLE)) {
                    score += 300;
                    playerOne.getBonus().reset();
                    if(scared)
                        unScareGhosts();
                }

                else if(collectible.getType().equals(Type.GRAPE)) {
                    score += 500;
                    playerOne.getBonus().reset();
                    scareGhosts();
                }

                else if(collectible.getType().equals(Type.CERISE)) {
                    score += 450;
                    playerOne.getBonus().reborn();
                    if(scared)
                        unScareGhosts();
                }

                else if(collectible.getType().equals(Type.ORANGE)) {
                    score += 650;
                    playerOne.getBonus().flash();
                    if(scared)
                        unScareGhosts();
                }

                else if(collectible.getType().equals(Type.STRAWBERRY)) {
                    score += 2500;
                    playerOne.getBonus().master();
                    if(scared)
                        unScareGhosts();
                }

                else if(collectible.getType().equals(Type.GUM)) {
                    score += 150;
                }
                ((Text) scorePlayer1.getSprite()).setLabel("score : " + score);
                ((Text) livesPlayerOne.getSprite()).setLabel("lives : " + playerOne.getLives());
                playerOne.getGameComponent().resetCollisionPassable();
            }
        }
        if(score == 55_250 && !success) {
            success = true;
        }
    }

    private void isGhostTouched() {
        for(Ghost ghost : ghosts) {
            if(kernel.getPhysics().isTraverseHitbox(ghost.getGameComponent().getEntity(), playerOne.getGameComponent().getEntity())) {
                if(!ghost.isScared()) {
                    playerOne.retireLife();
                    if(playerOne.getLives() > 0) {
                        ghost.getGameComponent().resetCollisionPassable();
                        ((Text) livesPlayerOne.getSprite()).setLabel("lives : " + playerOne.getLives());
                        reset = true;
                    }
                }
                else {
                    ghost.getGameComponent().resetCollisionPassable();
                    playerOne.getGameComponent().resetCollisionPassable();
                    kernel.deleteGameComponent(ghost.getGameComponent());
                }
            }
        }
        if(playerOne.getLives() == 0) {
            failure = true;
            playerOne.getGameComponent().resetCollisionPassable();
        }
    }

    private void scareGhosts() throws IOException {
        scared = true;
        for(Ghost ghost : ghosts) {
            ghost.setStrategy(new Escape(ghost.getGameComponent().getEntity()));
            ghost.setScared(true);
            ghost.getGameComponent().getEntity().getMovement().setSpeed(new Normal());
            ((Image) ghost.getGameComponent().getSprite()).setBufferedImage(ImageIO.read(getClass().getResource("ressources/images/enemies/phantom_scared.png")));
        }
    }

    private void unScareGhosts() throws IOException {
        scared = false;
        String imgUrl = "";
        for(Ghost ghost : ghosts) {
            ghost.setScared(false);
            ghost.setStrategy(new Stalker(ghost.getGameComponent().getEntity()));

            switch(ghost.getName()) {
                case "yellow":
                    imgUrl = "ressources/images/enemies/phantom_yellow.png";
                    ghost.setStrategy(new Stalker(ghost.getGameComponent().getEntity()));
                    break;
                case "cyan":
                    imgUrl = "ressources/images/enemies/phantom_cyan.png";
                    ghost.setStrategy(new Stalker(ghost.getGameComponent().getEntity()));
                    break;
                case "pink":
                    imgUrl = "ressources/images/enemies/phantom_pink.png";
                    ghost.setStrategy(new Ambusher(ghost.getGameComponent().getEntity()));
                    break;
                case "green":
                    imgUrl = "ressources/images/enemies/phantom_green.png";
                    ghost.setStrategy(new Ambusher(ghost.getGameComponent().getEntity()));
                    break;
                case "red":
                    imgUrl = "ressources/images/enemies/phantom_red.png";
                    ghost.setStrategy(new Ambusher(ghost.getGameComponent().getEntity()));
                    break;
                case "blue":
                    imgUrl = "ressources/images/enemies/phantom_blue.png";
                    ghost.setStrategy(new Mad(ghost.getGameComponent().getEntity()));
                    break;
            }
            ((Image) ghost.getGameComponent().getSprite()).setBufferedImage(ImageIO.read(getClass().getResource(imgUrl)));
        }
    }

    private void reset() throws IOException {
        for(Ghost ghost: ghosts) {
            kernel.deleteGameComponent(ghost.getGameComponent());
        }
        int x1 = coordinatesBorder.getX(), y1 = coordinatesBorder.getY();
        int x2 = x1 + 8, y2 = y1 + 8;
        ghosts.clear();
        initGhosts(x2,y2);
        kernel.deleteGameComponent(playerOne.getGameComponent());
        initPlayer(x2,y2, playerOne.getLives());
    }

    @Override
    public void listenInputs() {
        listenInputsPlayerOne();
        kernel.getInputs().updateListeners();
    }

    private void listenInputsPlayerOne() {
        Speed currentSpeed = playerOne.getGameComponent().getEntity().getMovement().getSpeed();

        if(kernel.getInputs().getKeyInput().isKeyDown(KeyEvent.VK_RIGHT)) {
            playerOne.getGameComponent().getEntity().setMovement(new GoRight(currentSpeed));
            playerOne.getGameComponent().getSprite().setOrientation(0);
        }

        if(kernel.getInputs().getKeyInput().isKeyDown(KeyEvent.VK_DOWN)) {
            playerOne.getGameComponent().getEntity().setMovement(new GoDown(currentSpeed));
            playerOne.getGameComponent().getSprite().setOrientation(90);
        }

        if(kernel.getInputs().getKeyInput().isKeyDown(KeyEvent.VK_LEFT)) {
            playerOne.getGameComponent().getEntity().setMovement(new GoLeft(currentSpeed));
            playerOne.getGameComponent().getSprite().setOrientation(180);
        }

        if(kernel.getInputs().getKeyInput().isKeyDown(KeyEvent.VK_UP)) {
            playerOne.getGameComponent().getEntity().setMovement(new GoUp(currentSpeed));
            playerOne.getGameComponent().getSprite().setOrientation(-90);
        }

        if(kernel.getInputs().getKeyInput().isKeyDown(KeyEvent.VK_SPACE)) {
            playerOne.getGameComponent().getEntity().setMovement(new StandBy(currentSpeed));
        }

        if(kernel.getInputs().getKeyInput().isKeyDown(KeyEvent.VK_ESCAPE)) {
            quit = true;
        }

    }

    @Override
    public boolean isQuit() {
        return quit;
    }

    @Override
    public boolean isFailure() {
        return failure;
    }

    @Override
    public boolean isSuccess() {
        if(success) {
            System.out.println(score);
        }
        return success;
    }

    @Override
    public void shutdown() {
        kernel.deleteGameComponent(playerOne.getGameComponent());
        kernel.deleteGameComponent(livesPlayerOne);
        kernel.deleteGameComponent(scorePlayer1);

        for(GameComponent wall: walls) {
            kernel.deleteGameComponent(wall);
        }

        for(Ghost ghost: ghosts) {
            kernel.deleteGameComponent(ghost.getGameComponent());
        }

        for(Collectible collectible: collectibles) {
            kernel.deleteGameComponent(collectible.getGameComponent());
        }
    }
}
