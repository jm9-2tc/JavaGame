package com.javagame.game;

import com.javagame.Constants;
import com.javagame.game.entities.monster.base.Monster;
import com.javagame.game.entities.player.base.Player;
import com.javagame.gui.GameScreen;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

public class GameEvents implements Runnable {
    private GameInstance gameInstance;
    private GameScreen panel;

    private boolean keyboardDisabled = false;

    private final AtomicLong frame = new AtomicLong(0);
    private final Timer timer = new Timer(true);
    private final TimerTask gameLoop = new TimerTask() {
        @Override
        public void run() {
            play();
        }
    };

    public void play() {
        for (Monster monster : gameInstance.monsters) {
            monster.update();
        }

        panel.repaint();
        frame.incrementAndGet();
    }


    public void setup(GameInstance gameInstance, GameScreen panel) {
        this.gameInstance = gameInstance;
        this.panel = panel;
        resume();
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

    public synchronized void pause() {
        keyboardDisabled = true;
        timer.cancel();
    }

    public synchronized void resume() {
        keyboardDisabled = false;
        timer.schedule(gameLoop, 0, 1000 / Constants.FRAMES_PER_SECOND);
    }

    public synchronized long getFrame() {
        return frame.get();
    }

    @Override
    public void run() {
        play();
    }
}
