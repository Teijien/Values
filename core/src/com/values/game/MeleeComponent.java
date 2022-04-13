package com.values.game;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MeleeComponent implements Component {
    public boolean attack;
    public boolean attacking;
    public boolean cancelable;
    public float attackLength;
    public float deltaTime;
    public Sprite sprite;

    public MeleeComponent(float attackLength, Sprite sprite) {
        attack = false;
        attacking = false;
        cancelable = true;
        deltaTime = 0;
        this.attackLength = attackLength;
        this.sprite = sprite;
    }
}
