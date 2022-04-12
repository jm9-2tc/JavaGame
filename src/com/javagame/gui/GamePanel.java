package com.javagame.gui;

import com.javagame.Constants;
import com.javagame.game.GameEvents;
import com.javagame.game.GameInstance;
import com.javagame.game.player.Player;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private final GameEvents gameEvents;
    private final GameInstance gameInstance;

    private final int width;
    private final int height;

    private int mousePosX = 0;
    private int mousePosY = 0;

    public GamePanel(GameEvents gameEvents, GameInstance gameInstance, int width, int height) {
        this.gameEvents = gameEvents;
        this.gameInstance = gameInstance;

        this.width = width;
        this.height = height;

        setFocusable(true);

        addKeyListener(new KeyListener());
        addMouseListener(new MouseListener());
    }

    public void drawDialogBox(String info) {

    }

    public void draw() {
        drawEnvironment();

        for (Player player : gameInstance.players) {

        }
    }

    private void drawEnvironment() {
        for(int x = 0; x < gameInstance.boardEnvironment.length; x++) {
            byte[] row = gameInstance.boardEnvironment[x];
            for(int y = 0; y < row.length; y++) {

            }
        }
    }

    public class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            gameEvents.handleKeyEvent(e.getKeyCode());
        }
    }

    public class MouseListener extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            mousePosX = e.getX();
            mousePosY = e.getY();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int fieldX = (mousePosX - (mousePosX % Constants.UNIT_SIZE)) / Math.max(1, mousePosX);
            int fieldY = (mousePosY - (mousePosY % Constants.UNIT_SIZE)) / Math.max(1, mousePosY);
            gameEvents.handleClickEvent(mousePosX, mousePosY);
        }
    }
}
