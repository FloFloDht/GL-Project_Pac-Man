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
import gameplay.map.Map;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class CoreGameplay {

    private final CoreKernel kernel;
    private final int WIDTH = 1200, HEIGHT = 800;
    private Map map;
    private Menu menu;
    private HomePage homePage;

    /** le nom du niveau **/
    private GameComponent levelTitle;

    public CoreGameplay() throws IOException {
        kernel = new CoreKernel(WIDTH,HEIGHT, Color.BLACK);
        homePage = new HomePage(kernel, WIDTH, HEIGHT);
        menu = new Menu(kernel, WIDTH, HEIGHT);
        initHeader();
    }

    public void initHeader() throws IOException {
        /** création du logo **/
        kernel.createGameComponent(new Image(Color.WHITE,0, ImageIO.read(getClass().getResource("ressources/images/scene/title_logo.png"))),
                new Coordinates((WIDTH / 2) - 140,10), new Dimension(300,105), new StandBy(new Neutral()), false);
    }

    public void start() throws InterruptedException, IOException {
        /** on affiche l'écran d'accueil **/
        homePage.display();
        render();

        while(true) {
            Thread.sleep(10);

            /** le menu s'affiche **/
            if(kernel.getInputs().getKeyInput().isKeyDown(KeyEvent.VK_ENTER)) {
                kernel.getInputs().getKeyInput().updateKeys();
                homePage.shutdown();
                menu.display();
                render();
                menu.waitLevelChosen();

                /** le niveau 1 est choisi **/
                if(menu.getLevelChosen() == 1) {
                    menu.shutdown();
                    initLevelOne();
                    Thread.sleep(2000);
                    gameLoop();
                    homePage.display();
                    render();
                }

                /** le niveau 1 est choisi **/
                else if(menu.getLevelChosen() == 2) {
                    menu.shutdown();
                    initLevelTwo();
                    Thread.sleep(2000);
                    gameLoop();
                    homePage.display();
                    render();
                }

                /** le niveau 1 est choisi **/
                else if(menu.getLevelChosen() == 3) {
                    menu.shutdown();
                    initLevelThree();
                    Thread.sleep(2000);
                    gameLoop();
                    homePage.display();
                    render();
                }
            }
        }
    }

    private void initLevelOne() throws IOException {
        map = new LevelOne(35, kernel, WIDTH, HEIGHT);
        map.generateMap();

        /** on met le titre à Level One **/
        levelTitle = kernel.createGameComponent(new Text(Color.WHITE,0, "Level One", new Font("DialogInput",Font.PLAIN,18)),
                new Coordinates(( WIDTH / 2) - 35,120), new Dimension(0,0), new StandBy(new Normal()), false);
        render();
    }

    private void initLevelTwo() throws IOException {
        map = new LevelTwo(35, kernel, WIDTH, HEIGHT);
        map.generateMap();

        /** on met le titre à Level Two **/
        levelTitle = kernel.createGameComponent(new Text(Color.WHITE,0, "Level Two", new Font("DialogInput",Font.PLAIN,18)),
                new Coordinates(( WIDTH / 2) - 50,120), new Dimension(0,0), new StandBy(new Normal()), false);
        render();
    }

    private void initLevelThree() throws IOException {
        map = new LevelThree(30, kernel, WIDTH, HEIGHT);
        map.generateMap();

        /** on met le titre à Level Three **/
        levelTitle = kernel.createGameComponent(new Text(Color.WHITE,0, "Level Three", new Font("DialogInput",Font.PLAIN,18)),
                new Coordinates(( WIDTH / 2) - 50,120), new Dimension(0,0), new StandBy(new Normal()), false);
        render();
    }

    private void gameLoop() throws InterruptedException, IOException {
        /** on lance le niveau **/

        while(!map.isQuit() && !map.isFailure() && !map.isSuccess()) {
            Thread.sleep(30);
            map.listenInputs();
            map.updateMap();
            render();
        }

        if(map.isQuit()) {
            map.shutdown();
            kernel.deleteGameComponent(levelTitle);
        }

        else if(map.isFailure() || map.isSuccess()) {
            Thread.sleep(2000);
            map.shutdown();
            kernel.deleteGameComponent(levelTitle);
        }
    }

    private void render() {
        kernel.getGraphics().getMainScene().repaint();
    }
}
