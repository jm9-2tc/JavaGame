package com.javagame.game;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.Locale;

import com.javagame.Constants;
import com.javagame.Game;
import com.javagame.game.arena.ArenaLoader;
import com.javagame.gui.GameInterface;
import com.javagame.gui.GamePanel;
import com.javagame.gui.GameScreen;
import com.javagame.resources.Resources;
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
        "0.png",
        "1.png",
        "2.png",
        "3.png",
        "4.png"
    };

    static {
        Color bgColor = new Color(48, 48, 48, 192);
        Color btnColor = new Color(36, 36, 36, 255);
        Color textColor = new Color(225, 225, 225, 255);

        // Initialize arenas:

        String[] arenaMaps = new File(Resources.texturesPath + Resources.mapsPath).list();

        if (arenaMaps != null) {
            int index  = 0;

            for (String arenaMap : arenaMaps) {
                arenas.first[index] = prepareName(arenaMap);
                arenas.second[index] = arenaMap;
                index++;
            }
        }

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

    private static String prepareName(String rawName) {
        String[] words = rawName.split("[.]")[0].replaceAll("[-_]", " ").split(" ");
        StringBuilder resultBuilder = new StringBuilder();

        for (String word : words) {
            resultBuilder.append(word.substring(0, 1).toUpperCase(Locale.ROOT)).append(word.substring(1)).append(' ');
        }

        String result = resultBuilder.substring(0, resultBuilder.length() - 1);

        if (result.length() == 1) {
            result = "The " + result;
        }

        return result;
    }
}
