package com.javagame.game.entities.monster;

import com.javagame.game.IEntity;

public interface IMonster extends IEntity {
    void loseHealth(int amount);

    //void loadTexture(String filename);
}
