package com.javagame.game;

import java.awt.Color;
import java.awt.Dimension;

import com.javagame.Constants;
import com.javagame.Game;
import com.javagame.game.arena.ArenaLoader;
import com.javagame.gui.GameInterface;
import com.javagame.gui.GamePanel;
import com.javagame.gui.GameScreen;
import com.javagame.types.Pair;

import static com.javagame.gui.GamePanel.*;

public class GameMechanics {
    private final GameInstance gameInstance;
    private final GameScreen gameScreen;
    private final GameInterface gameInterface;

    private static final Pair<String[], String[]> arenas = new Pair<>(new String[Constants.ARENAS_COUNT], new String[Constants.ARENAS_COUNT]);

    private static final GamePanel welcomePanel;
    private static final GamePanel playersCreatorPanel;

    private static final String[] blockTextures = {
        "0",
        "1",
        "2",
        "3",
        "4"
    };

    static {
        Color bgColor = new Color(48, 48, 48, 192);
        Color btnColor = new Color(36, 36, 36, 255);
        Color textColor = new Color(225, 225, 225, 255);

        // Initialize arenas:

        arenas.first[0] = "Backrooms"; arenas.second[0] = "backrooms";
        arenas.first[1] = "Castle"; arenas.second[1] = "castle";
        arenas.first[2] = "Great Labyrinth"; arenas.second[2] = "great-labyrinth";
        arenas.first[3] = "Lake"; arenas.second[3] = "lake";
        arenas.first[4] = "Small Town"; arenas.second[4] = "small-town";
        arenas.first[5] = "The T"; arenas.second[5] = "t";

        // Initialize panels:

        welcomePanel = new GamePanel(480, 360, textColor, btnColor, bgColor);
        playersCreatorPanel = new GamePanel(720, 640, textColor, btnColor, bgColor);
    }

    public GameMechanics(GameInstance gameInstance, GameScreen gameScreen, GameInterface game) {
        this.gameInstance = gameInstance;
        this.gameScreen = gameScreen;
        this.gameInterface = game;
    }

    public void setup() {
        Dimension btnSize = new Dimension(480, 120);

        // Welcome panel:

        Runnable onclick = () -> System.out.println("clicked");

        welcomePanel.addLabel(new GameLabel("Welcome", 48));
        welcomePanel.addButton(new GameButton(btnSize, "create players", this::showPlayersCreatorPanel));

        welcomePanel.addLabel(new GameLabel("select arena:"));
        welcomePanel.addComboBox(new GameComboBox(btnSize, arenas.first, this::loadArena));

        welcomePanel.addButton(new GameButton(btnSize, "start", onclick));

        // Players creator panel:

        playersCreatorPanel.addLabel(new GameLabel("Create players", 48));

        playersCreatorPanel.addButton(new GameButton(btnSize, "apply", Game.mechanics::showWelcomePanel));

        loadArena(0);
    }

    public void start() {
        showWelcomePanel();
    }

    private void showWelcomePanel() {
        gameInterface.setPanel(welcomePanel);
    }

    private void showPlayersCreatorPanel() {
        gameInterface.setPanel(playersCreatorPanel);
    }

    private void loadArena(int number) {
        gameInstance.setArena(ArenaLoader.load(arenas.second[number], blockTextures));
        gameScreen.recenter();
    }
}
