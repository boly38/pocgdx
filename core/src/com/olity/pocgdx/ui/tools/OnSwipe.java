package com.olity.pocgdx.ui.tools;

@FunctionalInterface
public interface OnSwipe {
    public static enum Direction{
        UP,DOWN,LEFT,RIGHT,NONE;
    }
    void call(Direction direction);
}

