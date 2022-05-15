package com.javagame.game.entities.monster;

import com.javagame.game.GameInstance;
import com.javagame.game.entities.AttackMatrix;
import com.javagame.game.entities.monster.base.Monster;
import com.javagame.resources.Resources;

import java.awt.*;

public class Glazojad extends Monster {

    public Glazojad(GameInstance gameInstance) {
        super(gameInstance, Resources.loadTexture(Resources.monsterTexturesPath+"glazojad.png"), AttackMatrix.SQUARE, 120, 20);
        setHealth(1300);
    }
}
