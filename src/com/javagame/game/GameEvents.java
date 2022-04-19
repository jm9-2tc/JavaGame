package com.javagame.game;

import com.javagame.game.player.Player;

public class GameEvents implements Runnable {
    private final GameInstance gameInstance;

    private boolean keyboardDisabled = false;

    public long time = 0;

    public GameEvents(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
    }

    public void play() {
        time = System.currentTimeMillis();
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
