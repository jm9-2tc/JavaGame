package com.javagame.gui;

import javax.swing.*;
import java.awt.*;

public class GameInterface extends JPanel {
    private final GridBagConstraints gbc;

    public GameInterface() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(240, 0, 0, 0);
    }

    public void setPanel(GamePanel panel) {
        removeAll();
        add(panel, gbc);
        getParent().revalidate();
    }

    public void hidePanel() {
        removeAll();
        getParent().revalidate();
    }
}
