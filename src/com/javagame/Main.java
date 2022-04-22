package com.javagame;

import com.javagame.game.GameEvents;
import com.javagame.game.GameInstance;
import com.javagame.game.arena.Arena;
import com.javagame.game.arena.ArenaLoader;
import com.javagame.game.player.Player;
import com.javagame.game.player.Warrior;
import com.javagame.gui.GamePanel;
import com.javagame.gui.GameWindow;

import java.awt.event.KeyEvent;

public class Main {

    public static void main(String[] args) {
        GameEvents events = new GameEvents();
        GameInstance game = new GameInstance(events, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        GamePanel panel = new GamePanel(events, game);

        Player.KeyBinds defaultKeys = new Player.KeyBinds(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);

        Player[] players = new Player[] {
                new Warrior(game, defaultKeys, 5, 5)
        };

        players[0].loadImages("archer", "archer", "archer");

        String[] blockTextures = {
                "0",
                "1",
                "2",
                "3",
                "4"
        };

        Arena arena = ArenaLoader.load("backrooms", blockTextures);

        game.setPlayers(players);
        game.setArena(arena);

        events.setup(game, panel);

        new Thread(events).start();

        GameWindow window = new GameWindow(panel);
    }
}
