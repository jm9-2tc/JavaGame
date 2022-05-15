package com.javagame.game.entities.player.base;

import com.javagame.game.entities.IEntity;

public interface IPlayer extends IEntity {
    void levelUp();

    void restoreHealth(int amount);

    void loseStamina(int amount);
    void restoreStamina(int amount);

    void loseMana(int amount);
    void restoreMana(int amount);
}
