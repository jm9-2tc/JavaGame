package com.javagame;

import com.javagame.game.GameEvents;
import com.javagame.game.GameInstance;
import com.javagame.game.GameMechanics;
import com.javagame.game.arena.Arena;
import com.javagame.game.arena.ArenaLoader;
import com.javagame.game.player.Player;
import com.javagame.game.player.Warrior;
import com.javagame.gui.GameInterface;
import com.javagame.gui.GameScreen;
import com.javagame.gui.GameWindow;

import java.awt.event.KeyEvent;

public class Main {

    public static void main(String[] args) {
        GameEvents events = new GameEvents();
        GameInstance gameInstance = new GameInstance(events, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        GameScreen screen = new GameScreen(events, gameInstance);
        GameInterface gameInterface = new GameInterface();

        screen.addInterface(gameInterface);

        GameMechanics mechanics = new GameMechanics(gameInterface);

        Player.KeyBinds defaultKeys = new Player.KeyBinds(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);

        Player[] players = new Player[] {
                new Warrior(gameInstance, defaultKeys, 5, 5)
        };

        players[0].loadImages("archer", "archer", "archer");

        String[] blockTextures = {
                "0",
                "1",
                "2",
                "3",
                "4"
        };

        Arena arena = ArenaLoader.load("great-labyrinth", blockTextures);

        gameInstance.setPlayers(players);
        gameInstance.setArena(arena);

        events.setup(gameInstance, screen);

        new GameWindow(screen);
        new Thread(events).start();

        mechanics.start();
    }
}
