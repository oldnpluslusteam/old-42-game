package com.github.oldnpluslusteam.old42_game.modules;

import com.github.alexeybond.partly_solid_bicycle.ioc.IoC;
import com.github.alexeybond.partly_solid_bicycle.ioc.modules.Module;
import com.github.alexeybond.partly_solid_bicycle.ioc.strategy.Singleton;
import com.github.oldnpluslusteam.old42_game.screens.GameScreen;

public class StartupScreenModule implements Module {
    @Override
    public void init() {
        IoC.register("initial screen", new Singleton(new GameScreen()));
    }

    @Override
    public void shutdown() {

    }
}
