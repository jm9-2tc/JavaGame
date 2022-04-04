package com.javagame.game;

import java.awt.*;

public interface IEntity {
    void levelUp();
    void attack(int x, int y);

    void loseHealth(int amount);
    void restoreHealth(int amount);

    void loseStamina(int amount);
    void restoreStamina(int amount);

    void loseMana(int amount);
    void restoreMana(int amount);

    void loadImages(Image idle, Image attackLeft, Image attackRight);
}
