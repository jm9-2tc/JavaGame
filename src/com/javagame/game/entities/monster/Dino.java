package com.javagame.game.entities.monster;

import com.javagame.game.GameInstance;
import com.javagame.game.entities.AttackMatrix;
import com.javagame.game.entities.monster.base.Monster;
import com.javagame.resources.Resources;

public class Dino extends Monster {
    public Dino(GameInstance gameInstance) {
        super(gameInstance, Resources.loadTexture(Resources.monsterTexturesPath + "dino.png"), AttackMatrix.CROSS, 100, 12);
        this.setHealth(400);
    }
}
