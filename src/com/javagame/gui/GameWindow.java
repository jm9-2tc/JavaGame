package com.javagame.gui;

import com.javagame.Constants;

import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow(GamePanel panel) {
        int height = Constants.WINDOW_HEIGHT * Constants.UNIT_SIZE;
        int width = Constants.WINDOW_WIDTH * Constants.UNIT_SIZE;

        setSize(width, height);
        add(panel);
        setVisible(true);
    }
}
