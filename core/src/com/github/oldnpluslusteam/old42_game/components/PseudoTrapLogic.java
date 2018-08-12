package com.github.oldnpluslusteam.old42_game.components;

import com.github.alexeybond.partly_solid_bicycle.game.Component;
import com.github.alexeybond.partly_solid_bicycle.game.Entity;
import com.github.alexeybond.partly_solid_bicycle.game.Game;
import com.github.alexeybond.partly_solid_bicycle.game.declarative.ComponentDeclaration;
import com.github.alexeybond.partly_solid_bicycle.game.declarative.GameDeclaration;
import com.github.alexeybond.partly_solid_bicycle.util.event.Event;
import com.github.alexeybond.partly_solid_bicycle.util.event.helpers.Subscription;
import com.github.alexeybond.partly_solid_bicycle.util.event.props.BooleanProperty;
import com.github.alexeybond.partly_solid_bicycle.util.event.props.ObjectProperty;

public class PseudoTrapLogic implements Component {
    private final Subscription<Event> hitBeginSub = new Subscription<Event>() {
        @Override
        public boolean onTriggered(Event event) {
            output.set(true);
            return true;
        }
    };
    private final Subscription<Event> hitEndSub = new Subscription<Event>() {
        @Override
        public boolean onTriggered(Event event) {
            output.set(false);
            return true;
        }
    };

    private BooleanProperty output;

    @Override
    public void onConnect(Entity entity) {
        hitBeginSub.set(entity.events().event("playerHit", ObjectProperty.make()));
        hitEndSub.set(entity.events().event("playerHitEnd", ObjectProperty.make()));
        output = entity.events().event("active", BooleanProperty.make());
    }

    @Override
    public void onDisconnect(Entity entity) {
        hitBeginSub.clear();
        hitEndSub.clear();
    }

    public static class Decl implements ComponentDeclaration {
        @Override
        public Component create(GameDeclaration gameDeclaration, Game game) {
            return new PseudoTrapLogic();
        }
    }
}
