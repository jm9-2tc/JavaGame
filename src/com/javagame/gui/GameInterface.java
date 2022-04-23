package com.javagame.gui;

import javax.swing.*;
import java.awt.*;

public class GameInterface extends JPanel {
    private final GridBagConstraints gbc;

    public GameInterface() {
        //setPreferredSize(window.getSize());
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
    }

    public void setPanel(GamePanel panel) {
        removeAll();
        add(panel);
        gbc.insets = new Insets(128, 0, 0, 0);
    }
}
