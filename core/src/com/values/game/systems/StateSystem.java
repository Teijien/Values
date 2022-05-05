package com.values.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import com.values.game.Mappers;
import com.values.game.components.BodyComponent;
import com.values.game.components.StateComponent;

public class StateSystem extends IteratingSystem {
    public StateSystem() {
        super(Family.all(StateComponent.class, BodyComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        StateComponent state = Mappers.state.get(entity);

        if (state.state == StateComponent.STUN) {
            Body body = Mappers.body.get(entity).body;
            if (Math.abs(body.getLinearVelocity().x) < 10 && Math.abs(body.getLinearVelocity().y) < 10) {
                state.state = StateComponent.WALK;
                System.out.println("Walking");
            }
        }
    }
}
