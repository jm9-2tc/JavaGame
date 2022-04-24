package com.javagame.game.monster;

import com.javagame.game.IEntity;

public interface IMonster extends IEntity {
    void attack();
    void loseHealth(int amount);

    //void loadTexture(String filename);
}
