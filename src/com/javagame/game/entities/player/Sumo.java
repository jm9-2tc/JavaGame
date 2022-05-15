package com.javagame.game.entities.player;

import com.javagame.game.GameInstance;
import com.javagame.game.entities.AttackMatrix;
import com.javagame.game.entities.player.base.Player;

import java.awt.*;

public class Sumo extends Player {
    public Sumo(String name, GameInstance gameInstance, KeyBinds keyBinds, Image texture, int x, int y) {
        super(name, gameInstance, keyBinds, texture, Type.SUMO, AttackMatrix.CROSS, 150, x, y, 6, 750, 600, 100);
    }
}
