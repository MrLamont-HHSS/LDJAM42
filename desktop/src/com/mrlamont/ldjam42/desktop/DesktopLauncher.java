package com.mrlamont.ldjam42.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mrlamont.ldjam42.LDJAM42Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.title = "Ludum Dare 42: Running Out of Space";
                config.width = 1024;
                config.height = 576;
		new LwjglApplication(new LDJAM42Game(), config);
	}
}
