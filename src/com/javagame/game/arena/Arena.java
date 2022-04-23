package com.javagame.game.arena;

import java.awt.*;

public class Arena {
    public final Image[] blockTextures;
    public final byte[][] blocks;

    public final int width;
    public final int height;

    public Arena(Image[] blockTextures, byte[][] blocks, int width, int height) {
        this.blockTextures = blockTextures;
        this.blocks = blocks;
        this.width = width;
        this.height = height;
    }
}
