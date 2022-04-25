package com.javagame.game.entities.player;

import com.javagame.game.IEntity;

public interface IPlayer extends IEntity {
    void levelUp();

    void loseHealth(int amount);
    void restoreHealth(int amount);

    void loseStamina(int amount);
    void restoreStamina(int amount);

    void loseMana(int amount);
    void restoreMana(int amount);
}
