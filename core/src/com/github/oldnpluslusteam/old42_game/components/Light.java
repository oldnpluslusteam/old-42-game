package com.github.oldnpluslusteam.old42_game.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.alexeybond.partly_solid_bicycle.drawing.DrawingContext;
import com.github.alexeybond.partly_solid_bicycle.game.Component;
import com.github.alexeybond.partly_solid_bicycle.game.Entity;
import com.github.alexeybond.partly_solid_bicycle.game.Game;
import com.github.alexeybond.partly_solid_bicycle.game.declarative.ComponentDeclaration;
import com.github.alexeybond.partly_solid_bicycle.game.declarative.GameDeclaration;
import com.github.alexeybond.partly_solid_bicycle.game.systems.render.components.BaseRenderComponent;
import com.github.alexeybond.partly_solid_bicycle.game.systems.timing.TimingSystem;
import com.github.alexeybond.partly_solid_bicycle.ioc.IoC;
import com.github.alexeybond.partly_solid_bicycle.util.event.helpers.Subscription;
import com.github.alexeybond.partly_solid_bicycle.util.event.props.BooleanProperty;
import com.github.alexeybond.partly_solid_bicycle.util.event.props.FloatProperty;

public class Light extends BaseRenderComponent {
    private float scaleMin, scaleMax, decreaseSpeed, increaseSpeed, deadDecreaseScale;

    private float currentScale = 10f;

    private final Subscription<FloatProperty> dtSub = new Subscription<FloatProperty>() {
        @Override
        public boolean onTriggered(FloatProperty event) {
            float dt = event.get();

            if (lightEnable.get()) {
                currentScale = Math.min(currentScale + dt * increaseSpeed, scaleMax);
            } else {
                currentScale = Math.max(currentScale - dt * decreaseSpeed, scaleMin);
            }

            return true;
        }
    };

    private BooleanProperty lightEnable;

    private final Sprite sprite;

    public Light(float scaleMin, float scaleMax, float decreaseSpeed, float increaseSpeed, float deadDecreaseScale, TextureRegion texture) {
        super("game-light");
        this.scaleMin = scaleMin;
        this.scaleMax = scaleMax;
        this.decreaseSpeed = decreaseSpeed;
        this.increaseSpeed = increaseSpeed;
        this.deadDecreaseScale = deadDecreaseScale;
        sprite = new Sprite(texture);
    }

    @Override
    public void onConnect(Entity entity) {
        super.onConnect(entity);
        lightEnable = entity.events().event("lightEnable", BooleanProperty.make());
        dtSub.set(entity.game().systems()
                .<TimingSystem>get("timing").events()
                .<FloatProperty>event("deltaTime"));
    }

    @Override
    public void onDisconnect(Entity entity) {
        lightEnable.set(false);
        decreaseSpeed *= deadDecreaseScale;
        scaleMin = 0;
        currentScale = Math.max(currentScale, scaleMax * 0.7f);
//        super.onDisconnect(entity);
//        dtSub.clear();
    }

    @Override
    public void draw(DrawingContext context) {
        Batch batch = context.state().beginBatch();

        sprite.setScale(currentScale);
        sprite.setPosition(position.ref().x - sprite.getOriginX(), position.ref().y - sprite.getOriginY());
        sprite.setColor(Color.WHITE);
        sprite.draw(batch);
    }

    public static class Decl implements ComponentDeclaration {
        public float scaleMin = 1f, scaleMax = 10f;
        public float decreaseSpeed = 1, increaseSpeed = 1, deadDecreaseScale= 0.7f;

        public String sprite = "particle";

        @Override
        public Component create(GameDeclaration gameDeclaration, Game game) {
            return new Light(
                    scaleMin,
                    scaleMax,
                    decreaseSpeed,
                    increaseSpeed,
                    deadDecreaseScale,
                    IoC.<TextureRegion>resolve("get texture region", sprite));
        }
    }
}
