package com.javagame.game;

import com.javagame.game.entities.player.base.Player;
import com.javagame.gui.GameScreen;

import java.util.concurrent.atomic.AtomicLong;

public class GameEvents implements Runnable {
    private GameInstance gameInstance;
    private GameScreen panel;

    private boolean keyboardDisabled = false;

    private final AtomicLong frame = new AtomicLong(0);

    public void play() {
        while (true) {
            panel.repaint();
            frame.incrementAndGet();
        }
    }


    public void setup(GameInstance gameInstance, GameScreen panel) {
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

    public synchronized long getFrame() {
        return frame.get();
    }

    @Override
    public void run() {
        play();
    }
}
