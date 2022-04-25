package com.javagame.gui;

import com.javagame.Constants;
import com.javagame.game.GameEvents;
import com.javagame.game.GameInstance;
import com.javagame.game.arena.Arena;
import com.javagame.game.entities.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameScreen extends JPanel {
    private final GameWindow gameWindow;
    private final GameEvents gameEvents;
    private final GameInstance gameInstance;

    private int unitSize;
    private int marginX = 0;
    private int marginY = 0;

    private int mousePosX = 0;
    private int mousePosY = 0;

    public GameScreen(GameWindow gameWindow, GameEvents gameEvents, GameInstance gameInstance) {
        this.gameWindow = gameWindow;
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

    private void drawPlayer(Graphics graphics, Player player) {
        int x = marginX + player.getX() * unitSize;
        int y = marginY + player.getY() * unitSize;

        graphics.drawImage(player.texture, x, y, unitSize, unitSize, this);
        graphics.drawString(String.valueOf(player.getHealth()), x, y + 16);
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

    public void recenter() {
        Dimension size = gameWindow.getSize();
        Arena arena = gameInstance.getArena();

        int unitSize = size.width / arena.width;

        if (arena.height * unitSize > size.height) {
            unitSize = size.height / arena.height;
        }

        int marginX = (size.width - (arena.width * unitSize)) / 2;
        int marginY = (size.height - (arena.height * unitSize)) / 2;

        this.marginX = marginX;
        this.marginY = marginY;
        this.unitSize = unitSize;

        repaint();
    }
}
