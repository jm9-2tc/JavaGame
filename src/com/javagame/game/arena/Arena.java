package com.javagame.game.arena;

import java.awt.*;

public class Arena {
    public final Image[] blockTextures;
    public final byte[][] blocks;

    public Arena(Image[] blockTextures, byte[][] blocks) {
        this.blockTextures = blockTextures;
        this.blocks = blocks;
    }
}
