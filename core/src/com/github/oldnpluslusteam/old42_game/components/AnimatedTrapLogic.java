package com.github.oldnpluslusteam.old42_game.components;

import com.github.alexeybond.partly_solid_bicycle.game.Component;
import com.github.alexeybond.partly_solid_bicycle.game.Entity;
import com.github.alexeybond.partly_solid_bicycle.game.Game;
import com.github.alexeybond.partly_solid_bicycle.game.declarative.ComponentDeclaration;
import com.github.alexeybond.partly_solid_bicycle.game.declarative.GameDeclaration;
import com.github.alexeybond.partly_solid_bicycle.util.event.Event;
import com.github.alexeybond.partly_solid_bicycle.util.event.helpers.Subscription;
import com.github.alexeybond.partly_solid_bicycle.util.event.props.ObjectProperty;

public class AnimatedTrapLogic implements Component {
    private final String hitAnimationName;

    private ObjectProperty<String> animationProp;
    private final Subscription<Event> hitSub = new Subscription<Event>() {
        @Override
        public boolean onTriggered(Event event) {
            animationProp.set(hitAnimationName);

            return true;
        }
    };

    public AnimatedTrapLogic(String hitAnimationNmae) {
        this.hitAnimationName = hitAnimationNmae;
    }

    @Override
    public void onConnect(Entity entity) {
        animationProp = entity.events().event("animation", ObjectProperty.<String>make());

        hitSub.set(entity.events().event("playerHit", ObjectProperty.make()));
    }

    @Override
    public void onDisconnect(Entity entity) {
        hitSub.clear();
    }

    public static class Decl implements ComponentDeclaration {
        public String hitAnimation = "hit";

        @Override
        public Component create(GameDeclaration gameDeclaration, Game game) {
            return new AnimatedTrapLogic(hitAnimation);
        }
    }
}
