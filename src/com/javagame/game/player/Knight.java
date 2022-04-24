package com.javagame.game.player;

import com.javagame.game.GameInstance;

import java.awt.*;

public class Knight extends Player{
    public Knight(String name, GameInstance gameInstance, KeyBinds keyBinds, Image texture, int x, int y) {
        super(name, gameInstance, keyBinds, texture, Type.KNIGHT, AttackMatrix.CROSS, 100, x, y, 100, 1000, 800, 200);
    }
}
