package com.javagame.gui;

import com.javagame.Constants;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameWindow(GameScreen screen) {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);

        Dimension size = getSize();

        int unitSize = size.width / Constants.WINDOW_WIDTH;

        if (Constants.WINDOW_HEIGHT * unitSize > size.height) {
            unitSize = size.height / Constants.WINDOW_HEIGHT;
        }

        int marginX = (size.width - (Constants.WINDOW_WIDTH * unitSize)) / 2;
        int marginY = (size.height - (Constants.WINDOW_HEIGHT * unitSize)) / 2;

        screen.setMargins(marginX, marginY);
        screen.setUnitSize(unitSize);

        add(screen);
    }
}
