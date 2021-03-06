package com.values.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import com.values.game.components.CollisionComponent;

/* Code used from https://www.gamedevelopment.blog/ashley-and-box2d-tutorial/ */
public class B2DContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        System.out.println("Contact");

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        // Check if one of the Fixtures' Body has an associated Entity
        if (fa.getBody().getUserData() instanceof Entity && !fa.isSensor()) {
            System.out.println("FA userData passed");
            Entity entity = (Entity) fa.getBody().getUserData();
            entityCollision(entity, fb);
        } else if (fb.getBody().getUserData() instanceof Entity && !fb.isSensor()) {
            System.out.println("FB userData passed");
            Entity entity = (Entity) fb.getBody().getUserData();
            entityCollision(entity, fa);
        }
    }

    private void entityCollision(Entity entity, Fixture fb) {
        if (fb.getBody().getUserData() instanceof Entity) { // Second check for asssociated Entity
            Entity colEnt = (Entity) fb.getBody().getUserData();
            CollisionComponent colA = entity.getComponent(CollisionComponent.class);
            CollisionComponent colB = colEnt.getComponent(CollisionComponent.class);

            // Add Entities to the appropriate CollisionComponent
            if (colA != null) {
                colA.entity.add(colEnt);
                System.out.println("A added");
            }

            if (colB != null && !fb.isSensor()) {
                colB.entity.add(entity);
                System.out.println("B added");
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("Contact end");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}