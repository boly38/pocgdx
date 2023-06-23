package com.olity.pocgdx;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.olity.pocgdx.PocGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	// TUTORIAL // config.setWindowedMode(800, 480);
	// ANDROID LIKE // width=2186 height=1080
	public static int width = 1920;
	public static int height = 1080;

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("POCGDX");
		config.setWindowedMode(width, height);
		config.useVsync(true);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new PocGame(), config);
	}
}
