package com.javagame.game.entities.player;

import com.javagame.game.entities.AttackMatrix;
import com.javagame.game.GameInstance;
import com.javagame.game.entities.player.base.Player;

import java.awt.*;

public class Knight extends Player {
    public Knight(String name, GameInstance gameInstance, KeyBinds keyBinds, Image texture, int x, int y) {
        super(name, gameInstance, keyBinds, texture, Type.KNIGHT, AttackMatrix.SQUARE, 100, x, y, 100, 1000, 800, 200);
    }
}
