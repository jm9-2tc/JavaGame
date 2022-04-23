package com.javagame.game.player;

import com.javagame.game.GameInstance;

public class Knight extends Player{
    public Knight(GameInstance gameInstance, KeyBinds keyBinds, int x, int y) {
        super(gameInstance, keyBinds, Type.WARRIOR, AttackMatrix.CROSS, 100, x, y, 100, 1000, 800, 200);
    }
}
