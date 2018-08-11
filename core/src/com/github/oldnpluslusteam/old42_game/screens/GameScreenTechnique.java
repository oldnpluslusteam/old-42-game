package com.github.oldnpluslusteam.old42_game.screens;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.github.alexeybond.partly_solid_bicycle.drawing.Pass;
import com.github.alexeybond.partly_solid_bicycle.drawing.TargetSlot;
import com.github.alexeybond.partly_solid_bicycle.drawing.tech.PlainTechnique;
import com.github.alexeybond.partly_solid_bicycle.ioc.IoC;

public class GameScreenTechnique extends PlainTechnique {
    private Pass mainCamera, background, main, foreground, particles, debug, light, ui;
    private TargetSlot colorSlot, lightSlot;
    private ShaderProgram shader;

    @Override
    protected void setup() {
        shader = IoC.resolve("load shader from files",
                "vs_game_ss.glsl",
                "ps_game_ss.glsl"
        );

        mainCamera = newPass("setup-main-camera");
        background = newPass("game-background");
        main = newPass("game-objects");
        foreground = newPass("game-foreground");
        debug = newPass("game-debug");
        light = newPass("game-light");
        particles = newPass("game-particles");
        ui = newPass("ui");

        colorSlot = target("color");
        lightSlot = target("light");
    }

    @Override
    protected void draw() {
        ensureMatchingFBO(colorSlot, context().getOutputTarget());
        ensureMatchingFBO(lightSlot, context().getOutputTarget());

        toTarget(colorSlot);
        gl.glClearColor(.5f, .5f, .5f, 1f);
        clear();
        doPass(mainCamera);

        doPass(background);
        doPass(particles);
        doPass(main);
        doPass(foreground);

        toTarget(lightSlot);
        gl.glClearColor(0,0,0,1);
        clear();
        doPass(light);

        toOutput();
        withShader(shader);
        shader.setUniformi("u_lightTexture", 1);
        lightSlot.get().asColorTexture().getTexture().bind(1);
        gl.glActiveTexture(gl.GL_TEXTURE0);
        screenQuad(colorSlot.get().asColorTexture(), true);
        withShader(null);

        doPass(ui);
        doPass(debug);
    }
}
