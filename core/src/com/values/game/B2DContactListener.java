package com.values.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;

/* Code used from https://www.gamedevelopment.blog/ashley-and-box2d-tutorial/ */
public class B2DContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        System.out.println("Contact");

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getBody().getUserData() instanceof Entity) {
            System.out.println("FA userData passed");
            Entity entity = (Entity) fa.getBody().getUserData();
            entityCollision(entity, fb);
        }

        if (fb.getBody().getUserData() instanceof Entity) {
            System.out.println("FB userData passed");
            Entity entity = (Entity) fb.getBody().getUserData();
            entityCollision(entity, fa);
        }
    }

    private void entityCollision(Entity entity, Fixture fb) {
        if (fb.getBody().getUserData() instanceof Entity) {
            Entity colEnt = (Entity) fb.getBody().getUserData();
            CollisionComponent colA = entity.getComponent(CollisionComponent.class);
            CollisionComponent colB = colEnt.getComponent(CollisionComponent.class);

            if (colA != null) {
                colA.entity.add(colEnt);
            }

            if (colB != null) {
                colB.entity.add(entity);
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
