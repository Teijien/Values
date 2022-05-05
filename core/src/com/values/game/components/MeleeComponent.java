package com.values.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public class MeleeComponent implements Component {
    public boolean attack = false;
    public boolean attacking = false;   // Attack state. If making a StateComponent, may want to move there
    public boolean cancelable = true;
    public float attackLength = 0.1f;   // Time that attack should take
    public float deltaTime = 0f;    // Used to track current time in attack animation
    public Sprite sprite = new Sprite(new Texture(Gdx.files.internal("attack.png")));   // Attack sprite

    public Sprite currSprite;   // Current sprite being shown

    public Body hitbox;

    public MeleeComponent(Body hitbox) {
        this.hitbox = hitbox;
    }
}
