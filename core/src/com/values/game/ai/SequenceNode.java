package com.values.game.ai;

import java.util.List;

public abstract class SequenceNode implements Node {
    private List<Node> children;

    public boolean task() {
        for (Node n : children) {
            if (!n.task()) {
                return false;
            }
        }

        return true;
    }
}
