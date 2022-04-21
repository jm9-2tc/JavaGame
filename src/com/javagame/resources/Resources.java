package com.javagame.resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Resources {
    public static final String texturesPath;

    static {
        texturesPath = Resources.class.getClassLoader().getResource("resources.textures").getPath();
    }

    public static BufferedImage loadTexture(String filename) {
        try {
            return ImageIO.read(new File(texturesPath + filename + ".png"));
        } catch (IOException e) {
            System.out.println("Cannot load texture '" + filename + "'.");
            System.exit(64);
        }
        return null;
    }
}
