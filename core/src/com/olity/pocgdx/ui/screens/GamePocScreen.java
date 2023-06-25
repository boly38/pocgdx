package com.olity.pocgdx.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.olity.pocgdx.PocGame;
import com.olity.pocgdx.ui.tools.SwipeGestureListener;
import com.olity.pocgdx.ui.tools.ClickableFont;
import com.olity.pocgdx.ui.tools.SimpleCallable;

import java.util.ArrayList;
import java.util.List;


/**
 * ^
 * | Y
 * |
 * |
 * |-----------> X
 */
public class GamePocScreen implements Screen {
    private final static String GOOD_GAME = "Bien joué ! Good game !";
    public static final int EXIT_DELAY_SEC = 5;
    private final PocGame game;
    private final Texture playerTexture, maskTexture, cellTexture, exitTexture;
    private final Vector2 gameGridScreenPosition, playerPosition, exitPosition;

    private final int CELL_SIZE = 80;
    private final int BOARD_SIZE_X = 10;
    private final int BOARD_SIZE_Y = 10;

    private final boolean[][] discovered = new boolean[BOARD_SIZE_X][BOARD_SIZE_Y];
    private final List<ClickableFont> clickableFonts = new ArrayList<>();
    private final ClickableFont endGameLabel;
    private final ClickableFont scoreLabel;
    private boolean gameEnded = false;
    private Timer.Task asyncLeaveTask = null;

    private int userLevelScore = 200;

    public GamePocScreen(final PocGame game) {
        this.game = game;
        //~ texture
        playerTexture = new Texture("player_80px.png");
        cellTexture = new Texture("cell_80px.jpg");
        maskTexture = new Texture("mask_80px.jpg");
        exitTexture = new Texture("exit_80px.jpg");

        //~ label
        endGameLabel = new ClickableFont(
                GOOD_GAME,
                game.screenWidth / 2,
                game.screenHeight - 100, 2,
                () -> Gdx.app.log("POC", "GG Click!")
        );
        clickableFonts.add(endGameLabel);
        endGameLabel.setVisible(gameEnded);

        scoreLabel = new ClickableFont(
                "Votre score: " + userLevelScore,
                game.screenWidth / 2,
                game.screenHeight - 20, 2,
                null
        );
        clickableFonts.add(scoreLabel);

        //~ positions
        int boardHeight = CELL_SIZE * BOARD_SIZE_Y;
        gameGridScreenPosition = new Vector2(100, (float) (game.screenHeight - boardHeight) / 2);
        playerPosition = randomStartPlayerPosition();
        exitPosition = randomEndExistPosition();

        //~ controls
        initializeGestures();

    }

    private void initializeGestures() {
        Gdx.input.setInputProcessor(new GestureDetector(new SwipeGestureListener(
                direction -> {
                    if (gameEnded) {
                        // remove previously scheduled tasks
                        forceGoToMainMenuNow();
                        return;
                    }
                    Gdx.app.log("POC", "swipe direction:" + direction);
                    switch (direction) {
                        case LEFT:
                            movePlayerWithinBoardLimit(-1, 0);
                            break;
                        case RIGHT:
                            movePlayerWithinBoardLimit(1, 0);
                            break;
                        case DOWN:
                            movePlayerWithinBoardLimit(0, -1);
                            break;
                        case UP:
                            movePlayerWithinBoardLimit(0, 1);
                            break;
                    }
                }
        )));
    }

    private Vector2 randomStartPlayerPosition() {// Start is at the top-left 1/4 corner on border
        return randomBoolean() ?
                new Vector2(0, BOARD_SIZE_Y - 1 - MathUtils.random.nextInt(getQuarterY()))//borderLeft
                : new Vector2(MathUtils.random.nextInt(getQuarterX()), BOARD_SIZE_Y - 1);// borderTop
    }


    private Vector2 randomEndExistPosition() {// End is at the bottom-right 1/4 corner on border
        return randomBoolean() ?
                new Vector2(BOARD_SIZE_X - 1, MathUtils.random.nextInt(getQuarterY()))//borderRight
                : new Vector2(BOARD_SIZE_X - 1 - MathUtils.random.nextInt(getQuarterX()), 0);// borderDown
    }

    private int getQuarterX() {
        return (int) (BOARD_SIZE_X / 4f);
    }

    private int getQuarterY() {
        return (int) (BOARD_SIZE_Y / 4f);
    }

    private static boolean randomBoolean() {
        return MathUtils.random.nextInt(2) == 0;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        handleKeyboardInput();
        handleGameTurn();
        handleBatchDraw();
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

    private void handleKeyboardInput() {
        int horizontalMovement = 0;
        int verticalMovement = 0;

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
            horizontalMovement--;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
            horizontalMovement++;
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            verticalMovement++;
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            verticalMovement--;

        boolean hasMovement = horizontalMovement != 0 || verticalMovement != 0;

        boolean keyJustPressedToForceLeave = (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER));
        if (keyJustPressedToForceLeave && gameEnded) {
            forceGoToMainMenuNow();
            return;
        } else if (gameEnded) {
            return;
        }
        if (hasMovement) {
            userLevelScore--;
            endGameLabel.updateText(GOOD_GAME + " score:" + userLevelScore);
            scoreLabel.updateText("Votre score: " + userLevelScore);
        }
        movePlayerWithinBoardLimit(horizontalMovement, verticalMovement);
    }


    private void movePlayerWithinBoardLimit(int horizontalMovement, int verticalMovement) {
        // Move the player within the board limits
        if (playerPosition.x + horizontalMovement >= 0 && playerPosition.x + horizontalMovement < BOARD_SIZE_X)
            playerPosition.x += horizontalMovement;
        if (playerPosition.y + verticalMovement >= 0 && playerPosition.y + verticalMovement < BOARD_SIZE_Y)
            playerPosition.y += verticalMovement;
    }

    private void handleGameTurn() {
        handlePlayerReachExitPosition();
    }

    private void handlePlayerReachExitPosition() {
        // Check if the player reached the exit position
        if (!gameEnded && playerPosition.equals(exitPosition)) {//!gameEnded condition prevents creating multiple tasks ^^
            gameEnded = true;
            endGameLabel.setVisible(true);
            asyncLeaveTask = scheduleAsyncTask(() -> game.gotoMainMenu(this.userLevelScore));
        }
    }


    private void handleBatchDraw() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        {
            int textX = (int) gameGridScreenPosition.x + (CELL_SIZE*BOARD_SIZE_X) + 50;
            game.font.draw(game.batch, "Trouvez la sortie avec le moins de déplacements possible",
                    textX, game.screenHeight - 300, 400, Align.left, true);

            game.font.draw(game.batch, "Vous pouvez vous déplacer avec des swipes (dekstop: ou au clavier).",
                    textX, game.screenHeight - 500, 400, Align.left, true);
            dragGridGround();
            drawPlayerOnGrid();
            drawExitOnGrid();
            drawFonts();
        }
        game.batch.end();
    }

    private void drawFonts() {
        for (ClickableFont font : clickableFonts) {
            font.update(game.batch, game.camera);
        }
    }

    private void dragGridGround() {
        for (int i = 0; i < BOARD_SIZE_X; i++) {
            for (int j = 0; j < BOARD_SIZE_Y; j++) {
                Texture cellTexture = discovered[i][j] ? this.cellTexture : maskTexture;
                drawTextureOnGrid(cellTexture, new Vector2(i, j));
            }
        }
    }

    private void drawPlayerOnGrid() {
        drawTextureOnGrid(playerTexture, playerPosition);
        discovered[(int) playerPosition.x][(int) playerPosition.y] = true;
    }

    private void drawExitOnGrid() {
        if (discovered[(int) exitPosition.x][(int) exitPosition.y]) {
            drawTextureOnGrid(exitTexture, exitPosition);
        }
    }

    private void drawTextureOnGrid(Texture texture, Vector2 gridPosition) {
        game.batch.draw(texture,
                gameGridScreenPosition.x + (gridPosition.x * CELL_SIZE),
                gameGridScreenPosition.y + (gridPosition.y * CELL_SIZE));
    }


    @Override
    public void dispose() {
        Gdx.app.log("dispose", "gamePoc");
        Gdx.input.setInputProcessor(null);
        playerTexture.dispose();
        cellTexture.dispose();
        maskTexture.dispose();
        exitTexture.dispose();
    }

    private Timer.Task scheduleAsyncTask(SimpleCallable callable) {
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.log("POC", "async callable.call()");
                callable.call();
            }
        };
        Timer.schedule(task, GamePocScreen.EXIT_DELAY_SEC);
        return task;
    }

    private void forceGoToMainMenuNow() {
        if (asyncLeaveTask != null) {
            Gdx.app.log("POC", "async task cancel()");
            asyncLeaveTask.cancel();
        }
        game.gotoMainMenu(this.userLevelScore);
    }
}
