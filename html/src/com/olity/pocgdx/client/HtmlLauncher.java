package com.olity.pocgdx.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.olity.pocgdx.PocGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
                return new GwtApplicationConfiguration(true);
                // Fixed size application:
                // return new GwtApplicationConfiguration(PocGame.width*4/5, PocGame.height *4/5);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new PocGame(PocGame.LaunchMode.HTML);
        }
}