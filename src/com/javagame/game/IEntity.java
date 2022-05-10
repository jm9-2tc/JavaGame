package com.javagame.game;

import java.awt.*;

public interface IEntity {
    void loseHealth(int amount);
    void moveTo(int x, int y);
    void die();
    Image getTexture();
    int getX();
    int getY();
    int getHealth();
    //void loadTexture(String filename);
}
