package com.javagame.game.entities.player;

import com.javagame.game.GameInstance;
import com.javagame.game.entities.AttackMatrix;
import com.javagame.game.entities.player.base.Player;

import java.awt.*;

public class Archer extends Player {
    public Archer(String name, GameInstance gameInstance, KeyBinds keyBinds, Image texture, int x, int y) {
        super(name, gameInstance, keyBinds, texture, Type.ARCHER, AttackMatrix.ALL, 50, x, y, 2, 750, 800, 600);
    }
}
