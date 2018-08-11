package com.github.oldnpluslusteam.old42_game.components;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.alexeybond.partly_solid_bicycle.game.Component;
import com.github.alexeybond.partly_solid_bicycle.game.Entity;
import com.github.alexeybond.partly_solid_bicycle.game.Game;
import com.github.alexeybond.partly_solid_bicycle.game.declarative.ComponentDeclaration;
import com.github.alexeybond.partly_solid_bicycle.game.declarative.GameDeclaration;
import com.github.alexeybond.partly_solid_bicycle.game.systems.box2d_physics.interfaces.BodyPhysicsComponent;
import com.github.alexeybond.partly_solid_bicycle.game.systems.timing.TimingSystem;
import com.github.alexeybond.partly_solid_bicycle.util.event.helpers.Subscription;
import com.github.alexeybond.partly_solid_bicycle.util.event.props.BooleanProperty;
import com.github.alexeybond.partly_solid_bicycle.util.event.props.FloatProperty;

public class PlayerController implements Component {
    private BooleanProperty keyForward, keyBackward, keyLeft, keyRight;
    private BooleanProperty keyLight, btnLight1, btnLight2;
    private FloatProperty axisX, axisY;

    private BooleanProperty lightEnable;
    private BodyPhysicsComponent body;

    private final Subscription<FloatProperty> dtSub = new Subscription<FloatProperty>() {
        @Override
        public boolean onTriggered(FloatProperty event) {
            updateControl();

            return true;
        }
    };

    @Override
    public void onConnect(Entity entity) {
        keyForward = entity.events().event("keyForward", BooleanProperty.make());
        keyBackward = entity.events().event("keyBackward", BooleanProperty.make());
        keyLeft = entity.events().event("keyLeft", BooleanProperty.make());
        keyRight = entity.events().event("keyRight", BooleanProperty.make());

        keyLight = entity.events().event("keyLight", BooleanProperty.make());
        btnLight1 = entity.events().event("btnLight1", BooleanProperty.make());
        btnLight2 = entity.events().event("btnLight2", BooleanProperty.make());

        axisX = entity.events().event("axisX", FloatProperty.make());
        axisY = entity.events().event("axisY", FloatProperty.make());

        lightEnable = entity.events().event("lightEnable", BooleanProperty.make());
        body = entity.components().get("body");

        dtSub.set(entity.game().systems()
                .<TimingSystem>get("timing").events()
                .<FloatProperty>event("deltaTime"));
    }

    @Override
    public void onDisconnect(Entity entity) {
        dtSub.clear();
    }

    private void applyControl(float x, float y, boolean light) {
        lightEnable.set(light);
        Body body = this.body.body();

        float linSpeed = 100;

        if (x == 0 && y == 0) {
            body.setLinearDamping(1000);
        } else {
            body.setLinearDamping(0);
            body.setLinearVelocity(x * linSpeed, y * linSpeed);
        }
    }

    private float roundAxis(float value) {
        if (Math.abs(value) < 0.2) return 0;

        return value;
    }

    private void updateControl() {
        float x = (keyLeft.get() ? -1f : 0f) + (keyRight.get() ? 1f : 0f) + roundAxis(axisX.get());
        float y = (keyForward.get() ? 1f : 0f) + (keyBackward.get() ? -1f : 0f) + roundAxis(axisY.get());
        boolean light = keyLight.get() || (btnLight1.get() && btnLight2.get());

        x = MathUtils.clamp(x, -1, 1);
        y = MathUtils.clamp(y, -1, 1);

        applyControl(x, y, light);
    }

    public static class Decl implements ComponentDeclaration {
        @Override
        public Component create(GameDeclaration gameDeclaration, Game game) {
            return new PlayerController();
        }
    }
}
