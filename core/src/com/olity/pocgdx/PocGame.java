package com.olity.pocgdx;

import static com.olity.pocgdx.PocGame.LaunchMode.DESKTOP;
import static java.util.Arrays.asList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.olity.pocgdx.ui.screens.MainMenuScreen;

public class PocGame extends Game {
    public static int width = 1920;
    public static int height = 1080;
    private final LaunchMode mode;
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public BitmapFont font;
    public MainMenuScreen mainMenu;
    public int screenHeight;
    public int screenWidth;
    public int gameScore;

    public static enum LaunchMode {
        HTML, ANDROID, DESKTOP, UNKNOWN
    }

    public PocGame(LaunchMode mode) {
        this.mode = mode;
    }

    public boolean canQuitApplication() {
        return asList(LaunchMode.ANDROID, DESKTOP).contains(mode);
    }

    public void create() {
        batch = new SpriteBatch();

        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        font = new BitmapFont(); // use libGDX's default Arial font
        font.getData().setScale(2);

        mainMenu = new MainMenuScreen(this);
        gotoMainMenu(0);
    }

    public void gotoMainMenu(int scoreIncrement) {
        this.gameScore += scoreIncrement;
        mainMenu.updateScore();
        this.setScreen(mainMenu);
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        mainMenu.dispose();
    }

}