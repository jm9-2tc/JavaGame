package com.javagame.game.player;

import com.javagame.game.GameInstance;

public class Warrior extends Player{
    public Warrior(GameInstance gameInstance, KeyBinds keyBinds, int x, int y) {
        super(gameInstance, keyBinds, Type.WARRIOR, AttackMatrix.CROSS, 100, x, y, 1000, 800, 200);
    }
}
