package com.javagame;

import com.javagame.resources.Resources;

import java.io.File;
import java.util.Objects;

public class Constants {
    // GUI:

    public static final int INITIAL_UNIT_SIZE = 40;

    // Game:

    public static final File[] ARENAS_FILES = Objects.requireNonNull(new File(Resources.texturesPath+"/map").listFiles());
    public static final int ARENAS_COUNT = ARENAS_FILES.length;

    public static final int DAMAGE_COLLIDE_SPIKY_BUSH = 100;
    public static final int POTION_HEALTH_RESTORED = 100;

    // Environment blocks:
    // 0 - grass
    // 1 - wall
    // 2 - spiky bush
    // 3 - water
    // 4 - HP potion

    public static final byte BLOCK_GRASS = 0;
    public static final byte BLOCK_WALL = 1;
    public static final byte BLOCK_SPIKY_BUSH = 2;
    public static final byte BLOCK_WATER = 3;
    public static final byte BLOCK_HP_POTION = 4;

    // Other settings:

    public static final short MONSTER_LIMIT = 5;
}
