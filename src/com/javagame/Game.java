package com.javagame;

import com.javagame.game.GameEvents;
import com.javagame.game.GameInstance;
import com.javagame.game.GameMechanics;
import com.javagame.game.player.Player;
import com.javagame.game.player.Knight;
import com.javagame.gui.GameInterface;
import com.javagame.gui.GameScreen;
import com.javagame.gui.GameWindow;

import java.awt.event.KeyEvent;

public class Game {
    public static final GameEvents events = new GameEvents();
    public static final GameInstance gameInstance = new GameInstance(events);

    public static final GameWindow window = new GameWindow();
    public static final GameScreen screen = new GameScreen(window, events, gameInstance);
    public static final GameInterface gameInterface = new GameInterface();

    public static final GameMechanics mechanics = new GameMechanics(gameInstance, screen, gameInterface);

    public static void main(String[] args) {
        screen.addInterface(gameInterface);
        mechanics.setup();

        Player.KeyBinds defaultKeys = new Player.KeyBinds(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);

        Player[] players = new Player[] {
                new Knight(gameInstance, defaultKeys, 5, 5)
        };

        players[0].loadImages("archer", "archer", "archer");



        //Arena arena = ArenaLoader.load("great-labyrinth", blockTextures);

        gameInstance.setPlayers(players);

        window.add(screen);

        events.setup(gameInstance, screen);

        new Thread(events).start();

        mechanics.start();
    }
}
