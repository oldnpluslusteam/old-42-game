package com.github.oldnpluslusteam.old42_game.screens;

import com.badlogic.gdx.Gdx;
import com.github.alexeybond.partly_solid_bicycle.application.Layer;
import com.github.alexeybond.partly_solid_bicycle.application.Screen;
import com.github.alexeybond.partly_solid_bicycle.application.impl.DefaultScreen;
import com.github.alexeybond.partly_solid_bicycle.application.impl.layers.GameLayerWith2DPhysicalGame;
import com.github.alexeybond.partly_solid_bicycle.application.util.ScreenUtils;
import com.github.alexeybond.partly_solid_bicycle.drawing.Technique;
import com.github.alexeybond.partly_solid_bicycle.drawing.tech.EDSLTechnique;
import com.github.alexeybond.partly_solid_bicycle.game.Game;
import com.github.alexeybond.partly_solid_bicycle.game.declarative.GameDeclaration;
import com.github.alexeybond.partly_solid_bicycle.game.declarative.visitor.impl.ApplyGameDeclarationVisitor;
import com.github.alexeybond.partly_solid_bicycle.ioc.IoC;
import com.github.alexeybond.partly_solid_bicycle.util.event.Event;
import com.github.alexeybond.partly_solid_bicycle.util.event.EventListener;
import com.github.alexeybond.partly_solid_bicycle.util.event.props.BooleanProperty;
import com.github.alexeybond.partly_solid_bicycle.util.parts.AParts;
import main.java.com.github.alexeybond.partly_solid_bicycle.ext.controllers.AnyControllerSystem;

public class GameScreen extends DefaultScreen {
    @Override
    protected Technique createTechnique() {
        return new GameScreenTechnique();
    }

    @Override
    protected void createLayers(AParts<Screen, Layer> layers) {
        super.createLayers(layers);

        ScreenUtils.enableToggleDebug(this, true);

        final Game game = layers.add("game", new GameLayerWith2DPhysicalGame()).game();

        game.systems().add("controller", new AnyControllerSystem());

        GameDeclaration gameDeclaration = IoC.resolve(
                "load game declaration",
                Gdx.files.internal("game.json"));

        new ApplyGameDeclarationVisitor().doVisit(gameDeclaration, game);

        game.events().event("lose", Event.makeEvent()).subscribe(new EventListener<Event>() {
            @Override
            public boolean onTriggered(Event event) {
                next(new LoseScreen(game, scene()));

                return true;
            }
        });

        game.events().event("lose", Event.makeEvent()).subscribe(new EventListener<Event>() {
            @Override
            public boolean onTriggered(Event event) {
                next(new WinScreen(game, scene()));

                return true;
            }
        });

        input().keyEvent("R").subscribe(new EventListener<BooleanProperty>() {
            @Override
            public boolean onTriggered(BooleanProperty event) {
                if (!event.get()) return false;
                next(new GameScreen());
                return true;
            }
        });
    }
}
