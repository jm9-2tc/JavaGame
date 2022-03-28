package com.company;

import com.company.classes.Constants;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow(Team team) {
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        /*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int locationX = (int) ((screenSize.getWidth() - weight) / 2);
        int locationY = (int) ((screenSize.getHeight() - height) / 2);
        setLocation(locationX, locationY);*/
        setLocationRelativeTo(null);
        add(new GameField(team));
        setVisible(true);
    }
}
