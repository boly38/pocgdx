package com.olity.pocgdx.ui.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.olity.pocgdx.PocGame;
import com.olity.pocgdx.ui.tools.ClickableFont;

import java.util.ArrayList;
import java.util.List;

public class MainMenuScreen implements Screen {

    final PocGame game;
    final int menuX;
    private final List<ClickableFont> clickableFonts = new ArrayList<>();
    private final ClickableFont gameScoreLabel;

    public MainMenuScreen(final PocGame game) {
        this.game = game;
        this.menuX = game.screenWidth /4;
        gameScoreLabel = new ClickableFont("Game score: " + game.gameScore,
                this.menuX,
                game.screenHeight - 150, 3,
                null
        );
        clickableFonts.add(gameScoreLabel);
        clickableFonts.add(new ClickableFont("Démarrer !",
                this.menuX,
                game.screenHeight - 300, 3,
                this::launchGame
        ));
        if (game.canQuitApplication()) {
            clickableFonts.add(new ClickableFont("Quitter",
                    this.menuX,
                    game.screenHeight - 400, 3,
                    this::existApp
            ));
        }
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        {
            game.font.draw(game.batch, "Bienvenue sur le Poc GDX Game! ",
                    this.menuX, game.screenHeight - 100, 400, Align.left, false);
            drawFonts();
        }
        game.batch.end();
    }

    private void drawFonts() {
        for (ClickableFont font : clickableFonts) {
            font.update(game.batch, game.camera);
        }
    }

    private void launchGame() {
        game.setScreen(new GamePocScreen(game));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        Gdx.app.log("dispose", "mainMenu");
        for (ClickableFont font : clickableFonts) {
            font.dispose();
        }
    }

    private void existApp() {
        Gdx.app.exit(); // Quit the app
    }

    public void updateScore() {
        gameScoreLabel.updateText("Game score: " + game.gameScore);
    }
}