package com.javagame.resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Resources {
    public static final String texturesPath = "src/com/javagame/resources/textures/";
    public static final String playerTexturesPath = "player/";
    public static final String blockTexturesPath = "block/";
    public static final String mapsPath = "map/";

    public static BufferedImage loadTexture(String filename) {
        try {
            return ImageIO.read(new File(texturesPath + filename));
        } catch (IOException e) {
            System.out.println("Cannot load texture '" + filename + "'.");
            System.exit(64);
        }
        return null;
    }
}
