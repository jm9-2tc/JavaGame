package com.javagame.game;

import com.javagame.game.player.Player;
import com.javagame.gui.GameScreen;

public class GameEvents implements Runnable {
    private GameInstance gameInstance;
    private GameScreen panel;

    private boolean keyboardDisabled = false;

    public long time = 0;

    public void play() {
        time = System.currentTimeMillis();

        while (true) {
            panel.repaint();
            time = System.currentTimeMillis() - time;
            //System.out.println(time);
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

    @Override
    public void run() {
        play();
    }
}
