package com.olity.pocgdx.ui.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * credit : <a href="https://stackoverflow.com/questions/35110792/how-to-make-font-text-clickable">Madmenyo</a>
 */
public class ClickableFont {

    //Declare the fields
    private final GlyphLayout layout;
    private final BitmapFont font;

    private final int posX;
    private final int posY;
    private final SimpleCallable onClick;

    private boolean visible;

    /**
     * Constructs clickable text from a font
     *
     * @param text Text to display
     * @param posX X position of the text
     * @param posY Y position of the text
     */
    public ClickableFont(String text, int posX, int posY, int scaleXY, SimpleCallable onClick) {
        this.visible = true;
        this.posX = posX;
        this.posY = posY;
        this.onClick = onClick;

        font = new BitmapFont(); // use libGDX's default Arial font // new BitmapFont(Gdx.files.internal("arial-15.fnt"));
        font.getData().setScale(scaleXY);
        layout = new GlyphLayout(font, text);
    }

    public void setVisible(boolean visible) {
        this.visible=visible;
    }
    public void updateText(String newText) {
        layout.setText(font, newText);
    }

    /**
     * @param batch  Draws the text using the given SpriteBatch.
     * @param camera Requires a camera to calculate touches between screen and world.
     */
    public void update(SpriteBatch batch, OrthographicCamera camera) {
        if (!visible) {
            return;
        }
        checkClicked(camera);
        font.draw(batch, layout, posX, posY);
    }

    /**
     * Checks if this object is clicked and outputs to console
     *
     * @param camera the camera
     */
    private void checkClicked(OrthographicCamera camera) {
        if (onClick != null && Gdx.input.justTouched()) {
            //Get screen coordinates
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            //Transform screen touch to world coordinates using the camera you are drawing with
            camera.unproject(touch);
            if (getRectangle().contains(touch.x, touch.y)) {
                onClick.call();
            }
        }
    }

    /**
     * Creates a rectangle for the sprite to perform collision calculations.
     * Since it seems font.draw draws from top to bottom (coordinate system of LibGDX is not consistent)
     * We have to adept the rectangle position Y position
     *
     * @return rectangle of font bounds
     */
    private Rectangle getRectangle() {
        return new Rectangle(posX, posY - (int) layout.height, (int) layout.width, (int) layout.height);
    }

    public void dispose() {
        font.dispose();
    }

}