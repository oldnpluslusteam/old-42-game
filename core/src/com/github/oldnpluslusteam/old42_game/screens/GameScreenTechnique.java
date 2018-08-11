package com.github.oldnpluslusteam.old42_game.screens;

import com.github.alexeybond.partly_solid_bicycle.drawing.Pass;
import com.github.alexeybond.partly_solid_bicycle.drawing.tech.PlainTechnique;

public class GameScreenTechnique extends PlainTechnique {
    private Pass mainCamera, background, main, foreground, debug, light;

    @Override
    protected void setup() {
        mainCamera = newPass("setup-main-camera");
        background = newPass("game-background");
        main = newPass("game-objects");
        foreground = newPass("game-foreground");
        debug = newPass("game-debug");
        light = newPass("game-light");
    }

    @Override
    protected void draw() {
        clear();
        doPass(mainCamera);

        doPass(background);
        doPass(main);
        doPass(foreground);
        doPass(debug);
    }
}
