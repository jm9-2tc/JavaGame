package com.javagame;

import com.javagame.game.GameEvents;
import com.javagame.game.GameInstance;
import com.javagame.gui.GamePanel;
import com.javagame.gui.GameWindow;

public class Main {

    public static void main(String[] args) {
        GameEvents events = new GameEvents();
        GameInstance game = new GameInstance(events, null, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        GamePanel panel = new GamePanel(events, game, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        GameWindow window = new GameWindow(panel);
    }
}
