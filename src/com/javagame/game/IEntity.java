package com.javagame.game;

public interface IEntity {
    void levelUp();
    void attack();

    void loseHealth(int amount);
    void restoreHealth(int amount);

    void loseStamina(int amount);
    void restoreStamina(int amount);

    void loseMana(int amount);
    void restoreMana(int amount);

    void loadTexture(String filename);
}
