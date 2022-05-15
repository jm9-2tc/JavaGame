package com.javagame.game.entities;

import java.awt.*;

public interface IEntity {
    void loseHealth(int amount);
    void moveTo(int x, int y);
    Image getTexture();
    int getX();
    int getY();
    int getHealth();
    //void loadTexture(String filename);
}
