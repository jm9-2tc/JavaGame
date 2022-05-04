package com.javagame.game.entities.player;

import com.javagame.game.GameInstance;
import com.javagame.game.entities.AttackMatrix;
import com.javagame.game.entities.player.base.Player;

import java.awt.*;

public class Ninja extends Player {
    public Ninja(String name, GameInstance gameInstance, KeyBinds keyBinds, Image texture, int x, int y) {
        super(name, gameInstance, keyBinds, texture, Type.NINJA, AttackMatrix.CORNERS, 50, x, y, 50, 1200, 1000, 800);
    }
}
