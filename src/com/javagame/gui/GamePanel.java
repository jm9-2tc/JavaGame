package com.javagame.gui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GamePanel extends JPanel {
    private final Color textColor;
    private final Color buttonsColor;

    private final GridBagConstraints gbc;

    public GamePanel(int width, int height, Color textColor, Color buttonsColor, Color backgroundColor) {
        setPreferredSize(new Dimension(width, height));
        setBackground(backgroundColor);

        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        this.buttonsColor = buttonsColor;
        this.textColor = textColor;
    }

    public void addLabel(GameLabel gameLabel) {
        JLabel label = new JLabel(gameLabel.text);
        label.setFont(new Font("consolas", Font.PLAIN, gameLabel.fontSize));

        label.setForeground(textColor);

        add(label, gbc);
        gbc.insets = new Insets(gameLabel.fontSize, 0, 0, 0);
    }

    public void addButton(GameButton gameButton) {
        JButton button = new JButton(gameButton.text);
        //button.setPreferredSize(gameButton.size);
        button.setFont(new Font("consolas", Font.PLAIN, gameButton.fontSize));
        button.addActionListener(e -> gameButton.onClick.run());

        button.setBackground(buttonsColor);
        button.setForeground(textColor);

        add(button, gbc);
        gbc.insets = new Insets(30, 0, 0, 0);
    }

    public void addComboBox(GameComboBox gameComboBox) {
        JComboBox<String> comboBox = new JComboBox<>();

        for(String option : gameComboBox.options) {
            comboBox.addItem(option);
        }

        comboBox.setPreferredSize(gameComboBox.size);
        comboBox.setFont(new Font("consolas", Font.PLAIN, gameComboBox.fontSize));
        comboBox.addActionListener(e -> gameComboBox.onChange.accept(comboBox.getSelectedIndex()));

        comboBox.setBackground(buttonsColor);
        comboBox.setForeground(textColor);

        add(comboBox, gbc);
        gbc.insets = new Insets(30, 0, 0, 0);
    }

    public static class GameLabel {
        public final String text;
        public final int fontSize;

        public GameLabel(String text) {
            this(text, 16);
        }

        public GameLabel(String text, int fontSize) {
            this.text = text;
            this.fontSize = fontSize;
        }
    }

    public static class GameButton {
        public final int fontSize;
        public final Dimension size;
        public final String text;
        public final Runnable onClick;

        public GameButton(Dimension size, String text, Runnable onClick) {
            this(size, text, 16, onClick);
        }

        public GameButton(Dimension size, String text, int fontSize, Runnable onClick) {
            this.size = size;
            this.fontSize = fontSize;
            this.text = text;
            this.onClick = onClick;
        }
    }

    public static class GameComboBox {
        public final int fontSize;
        public final Dimension size;
        public final String[] options;
        public final Consumer<Integer> onChange;

        public GameComboBox(Dimension size, String[] options, Consumer<Integer> onChange) {
            this(size, options, 16, onChange);
        }

        public GameComboBox(Dimension size, String[] options, int fontSize, Consumer<Integer> onChange) {
            this.size = size;
            this.fontSize = fontSize;
            this.options = options;
            this.onChange = onChange;
        }
    }
}
