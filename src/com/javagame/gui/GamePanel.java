package com.javagame.gui;

import com.javagame.Constants;
import com.javagame.game.GameEvents;
import com.javagame.game.GameInstance;
import com.javagame.game.arena.Arena;
import com.javagame.game.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private final GameEvents gameEvents;
    private final GameInstance gameInstance;

    private final int unitSize;

    private final int width;
    private final int height;

    private int mousePosX = 0;
    private int mousePosY = 0;

    public GamePanel(GameEvents gameEvents, GameInstance gameInstance, int width, int height) {
        this.gameEvents = gameEvents;
        this.gameInstance = gameInstance;

        this.unitSize = Constants.UNIT_SIZE;

        this.width = width;
        this.height = height;

        setFocusable(true);

        addKeyListener(new KeyListener());
        addMouseListener(new MouseListener());
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        drawEnvironment(graphics);

        for (Player player : gameInstance.players) {
            drawPlayer(graphics, player);
        }
    }

    public void drawDialogBox(String info) {

    }

    private void drawPlayer(Graphics graphics, Player player) {
        graphics.drawImage(player.getTexture(), player.getX(), player.getY(), unitSize, unitSize, this);
    }

    private void drawEnvironment(Graphics graphics) {
        Arena arena = gameInstance.getArena();

        for(int x = 0; x < arena.blocks.length; x++) {
            byte[] row = arena.blocks[x];

            for(int y = 0; y < row.length; y++) {
                Image blockTexture = arena.blockTextures[row[y]];
                graphics.drawImage(blockTexture, x * unitSize, y * unitSize, unitSize, unitSize, this);
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
