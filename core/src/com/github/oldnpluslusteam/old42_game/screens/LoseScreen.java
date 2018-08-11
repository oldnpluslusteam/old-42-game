package com.github.oldnpluslusteam.old42_game.screens;

import com.github.alexeybond.partly_solid_bicycle.application.impl.DefaultScreen;
import com.github.alexeybond.partly_solid_bicycle.drawing.Scene;
import com.github.alexeybond.partly_solid_bicycle.drawing.Technique;
import com.github.alexeybond.partly_solid_bicycle.drawing.tech.EDSLTechnique;
import com.github.alexeybond.partly_solid_bicycle.game.Game;

public class LoseScreen extends DefaultScreen {
    public LoseScreen(Game game, Scene scene) {

    }

    @Override
    protected Technique createTechnique() {
        return new EDSLTechnique() {
            @Override
            protected Runnable build() {
                return super.build();
            }
        };
    }
}
