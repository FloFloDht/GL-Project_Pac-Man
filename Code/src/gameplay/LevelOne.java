package gameplay;

import engine.ai.Stalker;
import engine.graphic.graphicComponent.Circle;
import gameplay.ai.Ghost;
import engine.graphic.graphicComponent.Image;
import engine.graphic.graphicComponent.RoundedRectangle;
import engine.graphic.graphicComponent.Text;
import engine.kernel.CoreKernel;
import engine.kernel.GameComponent;
import engine.physic.movement.*;
import engine.physic.speed.Normal;
import engine.physic.speed.Slow;
import engine.physic.speed.Speed;
import engine.universal.Coordinates;
import engine.universal.Dimension;
import gameplay.collectibles.Collectible;
import gameplay.collectibles.Type;
import gameplay.map.Map;
import gameplay.player.PacMan;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashSet;

public class LevelOne implements Map {

    private final CoreKernel kernel;
    private boolean quit = false;
    private boolean failure = false;
    private boolean success = false;
    private final int step;
    private final Dimension dimension;
    private final Coordinates coordinatesBorder;
    private final HashSet<Collectible> collectibles = new HashSet<>();
    private final HashSet<Ghost> ghosts = new HashSet<>();
    private PacMan playerOne;
    private GameComponent scorePlayer1;
    private GameComponent livesPlayerOne;
    private int score;
    private final HashSet<GameComponent> walls = new HashSet<>();


    public LevelOne(int step, CoreKernel kernel, int windowWidth, int windowHeight) throws IOException {
        this.kernel =  kernel;
        this.step = step + 3;
        dimension = new Dimension(9 * this.step, 7 * this.step);
        coordinatesBorder = new Coordinates((windowWidth - dimension.getWidth())/2, (windowHeight - dimension.getHeight())/2);
        score = 0;
        initText(windowWidth, windowHeight);
    }

    private void initText(int windowWidth, int windowHeight) {
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
        initCollectibles(x2, y2);

        /** fantômes **/
        initGhosts(x2, y2);

        /** joueur **/
        initPlayer(x2,y2);

    }

    private void initWalls(int x1, int y1, int x2, int y2) {
        /** Création des murs extérieurs **/
        walls.add(kernel.createGameComponent(new RoundedRectangle(Color.BLUE,0,true, 8,8),
                new Coordinates(x1, y1), new Dimension(dimension.getWidth() + 16, 8),
                new StandBy(), false));
        walls.add(kernel.createGameComponent(new RoundedRectangle(Color.BLUE,0,true, 8,8),
                new Coordinates(x1, y1), new Dimension(8, dimension.getHeight() + 16),
                new StandBy(), false));
        walls.add(kernel.createGameComponent(new RoundedRectangle(Color.BLUE,0,true, 8,8),
                new Coordinates(x1, y1 + dimension.getHeight() + 8), new Dimension(dimension.getWidth() + 16, 8),
                new StandBy(), false));
        walls.add(kernel.createGameComponent(new RoundedRectangle(Color.BLUE,0,true, 8,8),
                new Coordinates(x1 + dimension.getWidth() + 8, y1), new Dimension(8, dimension.getHeight() + 16),
                new StandBy(), false));

        /** Création des blocks **/
        walls.add(kernel.createGameComponent(new RoundedRectangle(Color.BLUE,0,true, 8,8),
                new Coordinates(x2 + step, y2 + step), new Dimension(2 * step, 2 * step),
                new StandBy(), false));
        walls.add(kernel.createGameComponent(new RoundedRectangle(Color.BLUE,0,true, 8,8),
                new Coordinates(x2 + step, y2 + 4 * step), new Dimension(2 * step, 2 * step),
                new StandBy(), false));
        walls.add(kernel.createGameComponent(new RoundedRectangle(Color.BLUE,0,true, 8,8),
                new Coordinates(x2 + 4 * step, y2 + 4 * step), new Dimension(step, step * 2),
                new StandBy(), false));
        walls.add(kernel.createGameComponent(new RoundedRectangle(Color.BLUE,0,true, 8,8),
                new Coordinates(x2 + 4 * step, y2 + step), new Dimension(step, step * 2),
                new StandBy(), false));
        walls.add(kernel.createGameComponent(new RoundedRectangle(Color.BLUE,0,true, 8,8),
                new Coordinates(x2 + 6 * step, y2 + 4 * step), new Dimension(2 * step, 2 * step),
                new StandBy(), false));
        walls.add(kernel.createGameComponent(new RoundedRectangle(Color.BLUE,0,true, 8,8),
                new Coordinates(x2 + 6 * step, y2 + step), new Dimension(2 * step, 2 * step),
                new StandBy(), false));
    }

    private void initCollectibles(int x, int y) throws IOException {
        /** pour placer les collectibles en fonction de la dimension de la map **/
        int gap = 18;
        int fruitDim = step - gap;
        int fruitCoo = gap/2;

        int gumGap = 31;
        int gumDim = step - gumGap;
        int gumCoo = gumGap /2;

        /** fraise **/
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/strawberry.png"))),
                new Coordinates(x + 4 * step + fruitCoo,y + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.STRAWBERRY));

        /** oranges **/
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/orange.png"))),
                new Coordinates(x + fruitCoo,y + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.ORANGE));
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/orange.png"))),
                new Coordinates(x + fruitCoo,y + 6 * step + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.ORANGE));
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/orange.png"))),
                new Coordinates(x + 8 * step + fruitCoo,y + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.ORANGE));
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/orange.png"))),
                new Coordinates(x + 8 * step + fruitCoo,y + 6 * step + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.ORANGE));

        /** grappe **/
        collectibles.add(new Collectible(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/bonus/fruits/grape.png"))),
                new Coordinates(x + 4 * step + fruitCoo,y + 6 * step + fruitCoo), new Dimension(fruitDim,fruitDim), new StandBy(), true), Type.GRAPE));


        for(int index = 1; index < 6; index ++) {
            if(index != 3) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + gumCoo,y + gumCoo + index * step), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                    new Coordinates(x + 3 * step + gumCoo,y + gumCoo + index * step), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));

            collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                    new Coordinates(x + 5 * step + gumCoo,y + gumCoo + index * step), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));

            collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                    new Coordinates(x + 8 * step + gumCoo,y + gumCoo + index * step), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
        }

        for(int index = 1; index < 8; index ++) {
            if(index != 4) {
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + index * step + gumCoo,y + gumCoo), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
                collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + index * step + gumCoo,y + gumCoo + 6 * step), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }

            if(index == 1 || index == 2 || index == 6 || index == 7) {
                    collectibles.add(new Collectible(kernel.createGameComponent(new Circle(Color.WHITE,0,true),
                        new Coordinates(x + index * step + gumCoo,y + gumCoo + 3 * step), new Dimension(gumDim,gumDim), new StandBy(), true), Type.GUM));
            }
        }
    }

    private void initPlayer(int x, int y) throws IOException {
        playerOne = new PacMan(kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/players/pac_man_yellow.png"))),
                new Coordinates(x + 4 * step + 8, y + 3 * step + 8), new Dimension(step - 18, step - 18), new StandBy(new Normal()),true), 2);
    }

    private void initGhosts(int x, int y) throws IOException {
        HashSet<Movement> movementsAuthorized = new HashSet<>();
        movementsAuthorized.add(new GoRight(new Normal()));
        movementsAuthorized.add(new GoDown(new Normal()));
        movementsAuthorized.add(new GoLeft(new Normal()));
        movementsAuthorized.add(new GoUp(new Normal()));

        ghosts.add(new Ghost(kernel.createGameComponent(new Image(Color.WHITE, 0, ImageIO.read(getClass().getResource("ressources/images/enemies/phantom_red.png"))),
                new Coordinates(x,y + 3 * step), new Dimension(step - 3, step - 3), new StandBy(new Normal()), true), movementsAuthorized, "red"));

        for(Ghost ghost : ghosts) {
            ghost.setStrategy(new Stalker(ghost.getGameComponent().getEntity()));
        }
    }

    @Override
    public void updateMap() throws IOException {
        movePlayerOne();
        isCollectibleEaten();
        moveAIs();
        isGhostTouched();
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
        for(Collectible collectible : collectibles) {
            if(playerOne.getGameComponent().getCollisionPassable() == collectible.getGameComponent().getEntity()) {
                kernel.deleteGameComponent(collectible.getGameComponent());

                if(collectible.getType().equals(Type.STRAWBERRY)) {
                    score += 2500;
                    playerOne.getBonus().master();
                }

                else if(collectible.getType().equals(Type.GRAPE)) {
                    score += 500;
                    playerOne.getBonus().reset();
                    scareGhosts();
                }

                else if(collectible.getType().equals(Type.ORANGE)) {
                    score += 650;
                    playerOne.getBonus().flash();
                }

                else if(collectible.getType().equals(Type.GUM)) {
                    score += 150;
                }

                ((Text) scorePlayer1.getSprite()).setLabel("score : " + score);
                playerOne.getGameComponent().resetCollisionPassable();
            }
        }
        if(score == 10850) {
            success = true;
        }
    }

    private void isGhostTouched() {
        for(Ghost ghost : ghosts) {
            if(kernel.getPhysics().isTraverseHitbox(ghost.getGameComponent().getEntity(), playerOne.getGameComponent().getEntity())) {
                if(!ghost.isScared()) {
                    playerOne.retireLife();
                    if(playerOne.getLives() > 0) {
                        reset();
                        ghost.getGameComponent().resetCollisionPassable();
                        ((Text) livesPlayerOne.getSprite()).setLabel("lives : " + playerOne.getLives());
                    }
                }
                else {
                    ghost.getGameComponent().resetCollisionPassable();
                    playerOne.getGameComponent().resetCollisionPassable();
                    kernel.deleteGameComponent(ghost.getGameComponent());
                    ((Text) scorePlayer1.getSprite()).setLabel("score : " + score);
                }
            }
        }

        if(playerOne.getLives() == 0) {
            failure = true;
            playerOne.getGameComponent().resetCollisionPassable();
        }
    }

    private void scareGhosts() throws IOException {
        for(Ghost ghost : ghosts) {
            ghost.setStrategy(new engine.ai.Escape(ghost.getGameComponent().getEntity()));
            ghost.setScared(true);
            ghost.getGameComponent().getEntity().getMovement().setSpeed(new Slow());
            ((Image) ghost.getGameComponent().getSprite()).setBufferedImage(ImageIO.read(getClass().getResource("ressources/images/enemies/phantom_scared.png")));
        }
    }

    private void moveAIs() {
        for(Ghost ghost : ghosts) {
            ghost.getStrategy().bestChoice(playerOne.getGameComponent().getEntity().getCoordinates(), kernel.getPhysics(), ghost.getMovementsAuthorized(), playerOne.getGameComponent().getSprite().getOrientation());
            kernel.getPhysics().moveTo(ghost.getGameComponent().getEntity());
        }
    }

    private void reset() {
        int x = coordinatesBorder.getX() + 8, y = coordinatesBorder.getY() + 8;

        playerOne.getGameComponent().getSprite().setX(x + 4 * step + 8);
        playerOne.getGameComponent().getSprite().setY(y + 3 * step + 8);
        playerOne.getGameComponent().getSprite().setOrientation(0);
        playerOne.getGameComponent().getEntity().setMovement(new StandBy(new Normal()));

        for(Ghost ghost : ghosts) {
            ghost.getGameComponent().getSprite().setX(x);
            ghost.getGameComponent().getSprite().setY(y + 3 * step);
            ghost.setScared(false);
            ghost.setStrategy(new Stalker(ghost.getGameComponent().getEntity()));
        }
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

    public boolean isFailure() {
        return failure;
    }

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
