package com.olity.pocgdx.ui.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class SwipeGestureListener implements GestureDetector.GestureListener {
    /**
     * set me temporarily to true to fine tune gesture velocity constants under
     */
    private static final boolean DEBUG_ME = false;
    /**
     * min velocity to consider a SWIPE action
     */
    private static final int MIN_SWIPE_VELOCITY = 800;// those value might be customized
    /**
     * max velocity accepted for an unwanted direction
     */
    private static final int MAX_NO_SWIPE_VELOCITY = 500;// those value might be customized
    private final OnSwipe onSwipe;

    /**
     * Register a callback for user swipe
     * @param onSwipe callback method having direction in argument
     */
    public SwipeGestureListener(OnSwipe onSwipe) {
        super();
        this.onSwipe = onSwipe;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float vX, float vY, int button) {
        if (DEBUG_ME) {
            Gdx.app.log("POC", "fling vx:" + vX + " vy:" + vY + " bt:" + button);
        }
        if (vX > MIN_SWIPE_VELOCITY && noSwipeVelocityFor(vY)) {
            this.onSwipe.call(OnSwipe.Direction.RIGHT);
        } else if (vX < -MIN_SWIPE_VELOCITY && noSwipeVelocityFor(vY)) {
            this.onSwipe.call(OnSwipe.Direction.LEFT);
        } else if (noSwipeVelocityFor(vX) && vY > MIN_SWIPE_VELOCITY) {
            this.onSwipe.call(OnSwipe.Direction.DOWN);
        } else if (noSwipeVelocityFor(vX) && vY < -MIN_SWIPE_VELOCITY) {
            this.onSwipe.call(OnSwipe.Direction.UP);
        } else {
            this.onSwipe.call(OnSwipe.Direction.NONE);
        }
        return false;
    }

    private boolean noSwipeVelocityFor(float velocity) {
        return velocity < SwipeGestureListener.MAX_NO_SWIPE_VELOCITY
                && velocity > -SwipeGestureListener.MAX_NO_SWIPE_VELOCITY;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
