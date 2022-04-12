package com.javagame.game;

import com.javagame.game.player.Player;

public class GameEvents implements Runnable {
    private final GameInstance gameInstance;

    public GameEvents(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
    }

    public void play() {

    }

    public void handleClickEvent(int x, int y) {

    }

    public void handleKeyEvent(int keyCode) {

    }

    @Override
    public void run() {
        play();
    }
}
