package com.github.oldnpluslusteam.old42_game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.alexeybond.partly_solid_bicycle.ioc.IoC;
import com.github.alexeybond.partly_solid_bicycle.ioc.ch.SingleContextHolder;
import com.github.oldnpluslusteam.old42_game.TheGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		IoC.init(new SingleContextHolder());
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.forceExit = false;
		config.fullscreen = true;
		config.height = 1080;
		config.width = 1920;
		new LwjglApplication(new TheGame(), config);
	}
}
