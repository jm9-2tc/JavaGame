package com.javagame.game.player;

import com.javagame.game.IEntity;

public interface IPlayer extends IEntity {
    void levelUp();
    void attack();

    void loseHealth(int amount);
    void restoreHealth(int amount);

    void loseStamina(int amount);
    void restoreStamina(int amount);

    void loseMana(int amount);
    void restoreMana(int amount);
}
