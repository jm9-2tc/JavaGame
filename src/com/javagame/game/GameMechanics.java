package com.javagame.game;

import java.awt.*;
import java.util.function.Supplier;

import com.javagame.gui.GameInterface;
import com.javagame.gui.GamePanel;

import static com.javagame.gui.GamePanel.*;

public class GameMechanics {
    private final GameInterface gameInterface;

    private static final GamePanel welcomePanel;
    //private static final GamePanel createPlayerPanel;
    //private static final GamePanel selectArenaPanel;

    static {
        Color bgColor = new Color(48, 48, 48, 192);
        Color btnColor = new Color(36, 36, 36, 255);
        Color textColor = new Color(225, 225, 225, 255);

        Dimension btnSize = new Dimension(480, 120);

        // Welcome panel:

        Supplier<Void> onclick = () -> {System.out.println("clicked"); return null;};

        welcomePanel = new GamePanel(480, 360, textColor, btnColor, bgColor);

        welcomePanel.addLabel(new GameLabel("Welcome", 48));
        welcomePanel.addButton(new GameButton(btnSize, "create players", onclick));
        welcomePanel.addButton(new GameButton(btnSize, "select arena", onclick));
        welcomePanel.addButton(new GameButton(btnSize, "start", onclick));

        //
    }

    public GameMechanics(GameInterface game) {
        this.gameInterface = game;
    }

    public void start() {
        showWelcomePanel();
    }

    private void showWelcomePanel() {
        gameInterface.setPanel(welcomePanel);
    }

    private void showCreatePlayerPanel() {

    }
}
