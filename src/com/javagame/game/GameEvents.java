package com.javagame.game;

import com.javagame.game.player.Player;
import com.javagame.gui.GamePanel;

public class GameEvents implements Runnable {
    private GameInstance gameInstance;
    private GamePanel panel;

    private boolean keyboardDisabled = false;

    public long time = 0;

    public void play() {
        panel.repaint();
        time = System.currentTimeMillis();
    }


    public void setup(GameInstance gameInstance, GamePanel panel) {
        this.gameInstance = gameInstance;
        this.panel = panel;
    }


    public void handleClickEvent(int x, int y) {

    }

    public void handleKeyEvent(int keyCode) {
        if (!keyboardDisabled) {
            for(Player player : gameInstance.players) {
                player.handleKeyEvent(keyCode);
            }
        }
    }

    public synchronized void disableKeyboard(boolean disable) {
        keyboardDisabled = disable;
    }

    @Override
    public void run() {
        play();
    }
}
