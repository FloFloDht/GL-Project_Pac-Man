package gameplay;

import engine.ai.Ambusher;
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

public class LevelTwo implements Map {

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


    public LevelTwo(int step, CoreKernel kernel, int windowWidth, int windowHeight) throws IOException {
        this.kernel =  kernel;
        this.step = step + 3;
        dimension = new Dimension(11 * this.step, 12 * this.step);
        coordinatesBorder = new Coordinates((windowWidth - dimension.getWidth())/2, (windowHeight - dimension.getHeight())/2);
        score = 0;
        initTexts(windowWidth, windowHeight);
    }

    private void initTexts(int windowWidth, int windowHeight) throws IOException {
        scorePlayer1 = kernel.createGameComponent(new Text(Color.WHITE,0, "score : " + score, new Font("DialogInput",Font.PLAIN,18)),
                new Coordinates(100,windowHeight - 50), new Dimension(0,0), new StandBy(new Normal()), false);

        livesPlayerOne = kernel.createGameComponent(new Text(Color.WHITE,0, "lives : " + 2, new Font("DialogInput",Font.PLAIN,18)),
                new Coordinates(windowWidth - 200,windowHeight - 50), new Dimension(0,0), new StandBy(new Normal()), false);
    }

    @Override
    public void generateMap() throws IOException {
        int x1 = coordinatesBorder.getX(), y1 = coordinatesBorder.getY();
        int x2 = x1 + 8, y2 = y1 + 8;

        /** murs **/
        initWalls(x1, y1, x2, y2);

        /** items **/
        initCollectibles(x2,y2);

        /** joueur **/
        initPlayer(x2,y2,2);

        /** fantômes **/
        initGhosts(x2, y2);
    }

    private void initWalls(int x1, int y1, int x2, int y2) {
        Color customGreen = new Color(65,210,47);

        /** Création des murs extérieurs **/
        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x1, y1), new Dimension(dimension.getWidth() + 16, 8),
                new StandBy(), false));
        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x1, y1), new Dimension(8, dimension.getHeight() + 16),
                new StandBy(), false));
        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x1, y1 + dimension.getHeight() + 8), new Dimension(dimension.getWidth() + 16, 8),
                new StandBy(), false));
        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x1 + dimension.getWidth() + 8, y1), new Dimension(8, dimension.getHeight() + 16),
                new StandBy(), false));

        /** Création des blocks **/
        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + step, y2 + step), new Dimension( 3 * step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + step, y2 + step), new Dimension(step, 2 * step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + 9 * step, y2 + step), new Dimension(step, 2 * step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + 5 * step , y2 + step), new Dimension(step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + 7 * step , y2 + step), new Dimension(3 * step , step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + 3 * step, y2 + 3 * step), new Dimension(3 * step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + 3 * step, y2 + 3 * step), new Dimension(step, 2 * step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + 5 * step, y2 + 3 * step), new Dimension(3 * step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + 7 * step, y2 + 3 * step), new Dimension(step, 2 * step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + step, y2 + 4 * step), new Dimension(step, 5 * step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + 9 * step, y2 + 4 * step), new Dimension(step, 5 * step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + step, y2 + 6 * step), new Dimension(3 * step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + 7 * step, y2 + 6 * step), new Dimension(3 * step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + 5 * step, y2 + 5 * step), new Dimension(step, 2 * step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + step, y2 + 10 * step), new Dimension(3 * step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + 3 * step, y2 + 8 * step), new Dimension(step, 3 * step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + 7 * step, y2 + 10 * step), new Dimension(3 * step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + 7 * step, y2 + 8 * step), new Dimension(step, 3 * step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + 5 * step, y2 + 8 * step), new Dimension(step, step),
                new StandBy(), false));

        walls.add(kernel.createGameComponent(new RoundedRectangle(customGreen,0,true, 8,8),
                new Coordinates(x2 + 5 * step, y2 + 10 * step), new Dimension(step, step),
                new StandBy(), false));
    }

    private void initCollectibles(int x, int y) throws IOException {
        int fruitGap = 18;
        int fruitDim = step - fruitGap;
        int fruitCoo = fruitGap/2;

        int gumGap = 31;
        int gumDim = step - gumGap;
        int gumCoo = gumGap /2;

        /** pommes **/
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/apple.png"))),
                new Coordinates(x + 5 * step + fruitCoo,y + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.APPLE));

        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/apple.png"))),
                new Coordinates(x + 5 * step + fruitCoo,y + 9 * step + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.APPLE));

        /** gums **/
        for(int index = 0; index < 12; index ++) {
            collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                    new Coordinates(x + gumCoo,y + gumCoo + index * step), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                    new Coordinates(x + 10 * step + gumCoo,y + gumCoo + index * step), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
        }

        for(int index  = 1; index < 10; index ++) {
            if(index != 5)
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + gumCoo + index * step,y + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                    new Coordinates(x + gumCoo + index * step,y + gumCoo + 11 * step), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
        }

        for(int index = 1; index < 11; index ++) {
            if(index != 3) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + gumCoo + 4 * step,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + gumCoo + 6 * step,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if(index == 2 || index == 5 || index == 7) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + gumCoo + 3 * step,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + gumCoo + 7 * step,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if(index == 2 || index == 4) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + gumCoo + 5 * step,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if((index >= 2 && index <= 5) || (index >= 7 && index <= 9)) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + gumCoo + 2 * step,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + gumCoo + 8 * step,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if(index == 9) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + gumCoo + step,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + gumCoo + 9 * step,y + index * step + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));

            }
        }
    }

    private void initPlayer(int x, int y, int lifePoints) throws IOException {
        playerOne = new PacMan(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/players/pac_man_yellow.png"))),
                new Coordinates(x + 5 * step + 8, y + 7 * step + 8), new Dimension(step - 18, step - 18), new StandBy(new Normal()),true), lifePoints);
    }

    private void initGhosts(int x, int y) throws IOException {
        HashSet<Movement> movementsAuthorized = new HashSet<>();
        movementsAuthorized.add(new GoRight(new Normal()));
        movementsAuthorized.add(new GoDown(new Normal()));
        movementsAuthorized.add(new GoLeft(new Normal()));
        movementsAuthorized.add(new GoUp(new Normal()));

        ghosts.add(new Ghost(kernel.createGameComponent(new Image(Color.WHITE, 0, ImageIO.read(getClass().getResource("ressources/images/enemies/phantom_orange.png"))),
                new Coordinates(x + step + 1,y + 3 * step + 1), new Dimension(step - 3, step - 3), new StandBy(new Normal()), true), movementsAuthorized,
                "orange"));

        ghosts.add(new Ghost(kernel.createGameComponent(new Image(Color.WHITE, 0, ImageIO.read(getClass().getResource("ressources/images/enemies/phantom_blue.png"))),
                new Coordinates(x + 9 * step + 1,y + 3 * step + 1), new Dimension(step - 3, step - 3), new StandBy(new Normal()), true), movementsAuthorized,
                "blue"));

        for(Ghost ghost : ghosts) {
            if(ghost.getName().equals("orange"))
                ghost.setStrategy(new Stalker(ghost.getGameComponent().getEntity()));
            if(ghost.getName().equals("blue"))
                ghost.setStrategy(new Ambusher(ghost.getGameComponent().getEntity()));
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

    private void moveAIs() {
        for(Ghost ghost : ghosts) {
            ghost.getStrategy().bestChoice(playerOne.getGameComponent().getEntity().getCoordinates(), kernel.getPhysics(), ghost.getMovementsAuthorized(),
                    playerOne.getGameComponent().getSprite().getOrientation());
            kernel.getPhysics().moveTo(ghost.getGameComponent().getEntity());
        }
    }

    private boolean exceedLimits() {
        int posX = playerOne.getGameComponent().getEntity().getX();
        int posY = playerOne.getGameComponent().getEntity().getY();

        return !(posX > coordinatesBorder.getX() + 8 && posX < coordinatesBorder.getX() - 16 + dimension.getWidth()
                && posY > coordinatesBorder.getY() + 8 && posY < coordinatesBorder.getY() - 16  + dimension.getHeight());
    }

    private void isCollectibleEaten() {
        for(Collectible collectible: collectibles) {
            if(playerOne.getGameComponent().getCollisionPassable() == collectible.getGameComponent().getEntity()) {
                kernel.deleteGameComponent(collectible.getGameComponent());

                if(collectible.getType().equals(Type.APPLE)) {
                    score += 300;
                }

                else if(collectible.getType().equals(Type.GUM)) {
                    score += 150;
                }
                ((Text) scorePlayer1.getSprite()).setLabel("score : " + score);
                playerOne.getGameComponent().resetCollisionPassable();
            }
        }
        if(score == 13050 && !success) {
            success = true;
        }
    }

    private void isGhostTouched() {
        for(Ghost ghost : ghosts) {
            if(kernel.getPhysics().isTraverseHitbox(ghost.getGameComponent().getEntity(), playerOne.getGameComponent().getEntity())) {
                    playerOne.retireLife();
                    if(playerOne.getLives() > 0) {
                        ghost.getGameComponent().resetCollisionPassable();
                        ((Text) livesPlayerOne.getSprite()).setLabel("lives : " + playerOne.getLives());
                        reset = true;
                    }
                }
            }
        if(playerOne.getLives() == 0) {
            failure = true;
            playerOne.getGameComponent().resetCollisionPassable();
        }
    }

    public void reset() throws IOException {
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
