package com.values.game.ai;

// Leaf Node to execute walking task
public class WalkLeafNode implements Node {
    private int x,y;

    public WalkLeafNode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean task() {
        return true;
    }
}
