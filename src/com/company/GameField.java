package com.company;

import com.company.classes.CharacterClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameField extends JPanel {
    private Team team;
    private CharacterClass[] players;
    public GameField(Team team) {
        this.team = team;
        this.players = team.getTeamMembers();

        setFocusable(true);
        addKeyListener(new FieldKeyListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (CharacterClass player : players) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
            g.drawString("healthPoints: " + player.getHealthPoints(), player.getX(), player.getY());
        }
    }

    public class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            for (CharacterClass player : players) {
                if (key == player.keyMoveLeft) {
                    player.moveLeft();
                }
                if (key == player.keyMoveRight) {
                    player.moveRight();
                }
                if (key == player.keyMoveUp) {
                    player.moveUp();
                }
                if (key == player.keyMoveDown) {
                    player.moveDown();
                }
                if (key == player.keyAttackLeft) {
                    player.setAttackLeftImage();

                    //timer
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    player.setBaseImage();
                                    repaint();
                                }
                            }, 200
                    );

                    player.attackLeft();
                }
                if (key == player.keyAttackRight) {
                    player.setAttackRightImage();

                    //timer
                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                    player.setBaseImage();
                                    repaint();
                                }
                            }, 200
                    );

                    player.attackRight();
                }
            }
            repaint();
        }
    }
}
