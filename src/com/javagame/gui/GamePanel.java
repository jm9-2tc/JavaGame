package com.javagame.gui;

import com.javagame.Constants;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;

import java.util.function.Consumer;

public class GamePanel extends JPanel {
    private final Color textColor;
    private final Color buttonsColor;

    private final GridBagConstraints gbc;
    private final List<GameComponent<?>> components;

    public GamePanel(int width, int height, Color textColor, Color buttonsColor, Color backgroundColor) {
        setPreferredSize(new Dimension(width, height));
        setBackground(backgroundColor);

        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        this.buttonsColor = buttonsColor;
        this.textColor = textColor;

        this.components = new ArrayList<>();
    }

    public <C extends JComponent> void addComponent(GameComponent<C> gameComponent) {
        gameComponent.setForeground(textColor);
        gameComponent.setBackground(buttonsColor);
        gameComponent.refresh();

        add(gameComponent.getComponent(), gbc);

        if (gameComponent instanceof GameLabel) {
            gbc.insets = new Insets(gameComponent.fontSize / 2, 0, 0, 0);
        } else {
            gbc.insets = new Insets(30, 0, 0, 0);
        }
    }

    public <C extends JComponent> void removeComponent(GameComponent<C> gameComponent) {
        remove(gameComponent.getComponent());
    }

    public static abstract class GameComponent<C extends JComponent> {
        protected int fontSize;
        protected Color foreground;
        protected Color background;

        protected C component;

        public GameComponent(int fontSize) {
            this.fontSize = fontSize;
        }

        public int getFontSize() {
            return fontSize;
        }

        public void setFontSize(int fontSize) {
            this.fontSize = fontSize;
            component.setFont(new Font("consolas", Font.PLAIN, fontSize));
        }

        public Color getForeground() {
            return foreground;
        }

        public void setForeground(Color foreground) {
            this.foreground = foreground;
            component.setForeground(foreground);
        }

        public Color getBackground() {
            return background;
        }

        public void setBackground(Color background) {
            this.background = background;
            component.setBackground(background);
        }


        public C getComponent() {
            return component;
        }

        public void refresh() {
            component.repaint();
        }

        protected abstract void setupComponent();
    }

    public static class GameLabel extends GameComponent<JLabel> {
        private String text;

        public GameLabel(String text) {
            this(text, 16);
        }

        public GameLabel(String text, int fontSize) {
            super(fontSize);
            this.text = text;
            setupComponent();
        }

        @Override
        protected void setupComponent() {
            JLabel label = new JLabel(text);
            label.setFont(new Font("consolas", Font.PLAIN, fontSize));
            component = label;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
            component.setText(text);
        }
    }

    public static class GameInput extends GameComponent<JTextField> {
        public final Dimension size;
        public final String defaultValue;

        private boolean enabled = true;

        public GameInput(Dimension size) {
            this(size, "", 16);
        }

        public GameInput(Dimension size, int fontSize) {
            this(size, "", fontSize);
        }

        public GameInput(Dimension size, String placeholder) {
            this(size, placeholder, 16);
        }

        public GameInput(Dimension size, String placeholder, int fontSize) {
            super(fontSize);
            this.size = size;
            this.defaultValue = placeholder;
            setupComponent();
        }

        @Override
        protected void setupComponent() {
            JTextField textField = new JTextField(defaultValue);
            textField.setFont(new Font("consolas", Font.PLAIN, fontSize));
            textField.setPreferredSize(size);
            component = textField;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
            component.setEnabled(enabled);
        }

        public String getValue() {
            return component.getText();
        }

        public void setValue(String value) {
            component.setText(value);
        }
    }

    public static class GameButton extends GameComponent<JButton> {
        public final Dimension size;
        public final Runnable onClick;
        private boolean enabled = true;

        private String text;

        public GameButton(Dimension size, String text, Runnable onClick) {
            this(size, text, 16, onClick);
        }

        public GameButton(Dimension size, String text, int fontSize, Runnable onClick) {
            super(fontSize);
            this.size = size;
            this.text = text;
            this.onClick = onClick;
            setupComponent();
        }

        @Override
        protected void setupComponent() {
            JButton button = new JButton(text);
            button.setPreferredSize(size);
            button.setFont(new Font("consolas", Font.PLAIN, fontSize));
            button.addActionListener(e -> onClick.run());
            component = button;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
            component.setText(text);
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
            component.setEnabled(enabled);
        }
    }

    public static class GameComboBox extends GameComponent<JComboBox<String>> {
        public final Dimension size;
        public final Consumer<Integer> onChange;

        private String[] options;
        private boolean enabled = true;

        public GameComboBox(Dimension size, String[] options, Consumer<Integer> onChange) {
            this(size, options, 16, onChange);
        }

        public GameComboBox(Dimension size, String[] options, int fontSize, Consumer<Integer> onChange) {
            super(fontSize);
            this.size = size;
            this.options = options;
            this.onChange = onChange;
            setupComponent();
        }

        @Override
        protected void setupComponent() {
            JComboBox<String> comboBox = new JComboBox<>();

            for(String option : options) {
                comboBox.addItem(option);
            }

            comboBox.setPreferredSize(size);
            comboBox.setFont(new Font("consolas", Font.PLAIN, fontSize));
            comboBox.addActionListener(e -> onChange.accept(comboBox.getSelectedIndex()));
            component = comboBox;
        }

        public String[] getOptions() {
            return options;
        }

        public void setOptions(String[] options) {
            this.options = options;
            component.removeAllItems();

            for(String option : options) {
                component.addItem(option);
            }
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
            component.setEnabled(enabled);
        }

        public int getSelectedIndex() {
            return component.getSelectedIndex();
        }

        public void setSelectedIndex(int index) {
            component.setSelectedIndex(index);
        }
    }
}
