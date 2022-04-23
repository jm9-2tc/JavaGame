package com.javagame.game.arena;

import com.javagame.Constants;
import com.javagame.resources.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ArenaLoader {

    private static final int GRASS_COLOR = 0xFF00FF00;
    private static final int WALL_COLOR = 0xFFC8C8C8;
    private static final int SPIKY_BUSH_COLOR = 0xFF008800;
    private static final int WATER_COLOR = 0xFF0000FF;
    private static final int HP_POTION_COLOR = 0xFFFF00FF;

    public static Arena load(String filename, String[] texturesPath) {
        BufferedImage mapFile = Resources.loadTexture("map/" + filename);

        int width = mapFile.getWidth();
        int height = mapFile.getHeight();

        byte[][] map = new byte[width][height];

        for (int y = 0; y < mapFile.getHeight(); y++) {
            for (int x = 0; x < mapFile.getWidth(); x++) {
                map[x][y] = getBlockByColor(mapFile.getRGB(x, y));
            }
        }

        Image[] textures = new Image[texturesPath.length];

        for(byte index = 0; index < texturesPath.length; index++) {
            textures[index] = Resources.loadTexture("block/" + texturesPath[index]);
        }

        return new Arena(textures, map);
    }

    private static byte getBlockByColor(int color) {
        switch (color) {
            case GRASS_COLOR:
                return Constants.BLOCK_GRASS;

            case WALL_COLOR:
                return Constants.BLOCK_WALL;

            case SPIKY_BUSH_COLOR:
                return Constants.BLOCK_SPIKY_BUSH;

            case WATER_COLOR:
                return Constants.BLOCK_WATER;

            case HP_POTION_COLOR:
                return Constants.BLOCK_HP_POTION;
        }
        return Constants.BLOCK_GRASS;
    }
}
