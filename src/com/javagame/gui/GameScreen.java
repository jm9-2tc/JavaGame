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

public class GameScreen extends JPanel {
    private final GameEvents gameEvents;
    private final GameInstance gameInstance;

    private int unitSize;
    private int marginX = 0;
    private int marginY = 0;

    private int mousePosX = 0;
    private int mousePosY = 0;

    public GameScreen(GameEvents gameEvents, GameInstance gameInstance) {
        this.gameEvents = gameEvents;
        this.gameInstance = gameInstance;

        this.unitSize = Constants.INITIAL_UNIT_SIZE;

        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(new KeyListener());
        addMouseListener(new MouseListener());
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        if (gameInstance == null) return;

        super.paintComponent(graphics);
        drawEnvironment(graphics);

        for (Player player : gameInstance.players) {
            drawPlayer(graphics, player);
        }
    }

    public void drawDialogBox(String info) {

    }

    private void drawPlayer(Graphics graphics, Player player) {
        int x = marginX + player.getX() * unitSize;
        int y = marginY + player.getY() * unitSize;

        graphics.drawImage(player.getTexture(), x, y, unitSize, unitSize, this);
        graphics.drawString(String.valueOf(player.getHealth()), x, y);
    }

    private void drawEnvironment(Graphics graphics) {
        Arena arena = gameInstance.getArena();

        for(int x = 0; x < arena.blocks.length; x++) {
            byte[] row = arena.blocks[x];

            for(int y = 0; y < row.length; y++) {
                Image blockTexture = arena.blockTextures[row[y]];
                graphics.drawImage(blockTexture, (x * unitSize) + marginX, (y * unitSize) + marginY, unitSize, unitSize, this);
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
            int fieldX = (mousePosX - (mousePosX % Constants.INITIAL_UNIT_SIZE)) / Math.max(1, mousePosX);
            int fieldY = (mousePosY - (mousePosY % Constants.INITIAL_UNIT_SIZE)) / Math.max(1, mousePosY);
            gameEvents.handleClickEvent(mousePosX, mousePosY);
        }
    }

    public void addInterface(GameInterface gameInterface) {
        add(gameInterface);
    }

    public void setMargins(int x, int y) {
        this.marginX = x;
        this.marginY = y;
    }

    public void setUnitSize(int unitSize) {
        this.unitSize = unitSize;
        setSize(Constants.WINDOW_WIDTH * unitSize, Constants.WINDOW_HEIGHT * unitSize);
        repaint();
    }
}
