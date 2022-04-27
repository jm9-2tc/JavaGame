package com.javagame.game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.javagame.Game;
import com.javagame.game.arena.ArenaLoader;
import com.javagame.game.entities.player.Player;
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

    private final List<PlayerData> players = new ArrayList<>();

    private static final GamePanel welcomePanel;
    private static final GamePanel playersCreatorPanel;

    private static final String[] blocksTextures;
    private static final Pair<String[], String[]> arenas = new Pair<>(null, null);
    private static final Pair<String[], Player.KeyBinds[]> keyBindsPresets = new Pair<>(null, null);
    private static final PlayerPreset[] playerPresets;


    private GameComboBox playerSelectCombo;
    private GameComboBox playerClassCombo;
    private GameComboBox playerKeyBindsCombo;

    private GameButton playerAddBtn;
    private GameButton playerUpdateBtn;
    private GameButton playerRemoveBtn;

    private GameInput playerNameInput;
    private GameLabel playerCreatorWarningLabel;

    private int selectedPlayer = -1;

    static {
        Color bgColor = new Color(48, 48, 48, 192);
        Color btnColor = new Color(36, 36, 36, 255);
        Color textColor = new Color(225, 225, 225, 255);

        // Initialize arenas:

        String[] arenaMaps = new File(Resources.texturesPath + Resources.mapsPath).list();

        if (arenaMaps != null) {
            int index  = 0;

            arenas.first = new String[arenaMaps.length];
            arenas.second = new String[arenaMaps.length];

            for (String arenaMap : arenaMaps) {
                arenas.first[index] = prepareName(arenaMap);
                arenas.second[index] = arenaMap;
                index++;
            }
        }

        // Initialize blocks textures:

        String[] blocksTexturesFiles = new File(Resources.texturesPath + Resources.blockTexturesPath).list();

        if (blocksTexturesFiles != null) {
            int index = 0;

            blocksTextures = new String[blocksTexturesFiles.length];

            for (String blockTextureFile : blocksTexturesFiles) {
                blocksTextures[index] = blockTextureFile;
                index++;
            }
        } else {
            blocksTextures = null;
        }

        // Initialize player presets:

        playerPresets = new PlayerPreset[1];

        playerPresets[0] = new PlayerPreset("Knight", Resources.playerTexturesPath + "knight.png", Player.Type.KNIGHT);

        // Initialize key binds:

        keyBindsPresets.first = new String[2];
        keyBindsPresets.second = new Player.KeyBinds[2];

        keyBindsPresets.first[0] = "W S A D Space";
        keyBindsPresets.second[0] = new Player.KeyBinds(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);

        keyBindsPresets.first[1] = "↑ ↓ ← → Enter";
        keyBindsPresets.second[1] = new Player.KeyBinds(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);

        // Initialize panels:

        welcomePanel = new GamePanel(480, 420, textColor, btnColor, bgColor);
        playersCreatorPanel = new GamePanel(720, 800, textColor, btnColor, bgColor);
    }

    public GameMechanics(GameInstance gameInstance, GameScreen gameScreen, GameInterface game) {
        this.gameInstance = gameInstance;
        this.gameScreen = gameScreen;
        this.gameInterface = game;
    }

    public void setup() {
        Dimension btnSize = new Dimension(200, 40);

        // Welcome panel:

        Runnable onclick = () -> System.out.println("clicked");

        welcomePanel.addComponent(new GameLabel("Welcome", 48));
        welcomePanel.addComponent(new GameButton(btnSize, "create players", this::showPlayersCreatorPanel));

        welcomePanel.addComponent(new GameLabel("select arena:"));
        welcomePanel.addComponent(new GameComboBox(btnSize, arenas.first, this::loadArena));

        welcomePanel.addComponent(new GameButton(btnSize, "start", onclick));

        // Players creator panel:

        playerSelectCombo = new GameComboBox(btnSize, new String[0], this::selectPlayer);
        playerClassCombo = new GameComboBox(btnSize, getPlayersClasses(), (arg) -> {});
        playerKeyBindsCombo = new GameComboBox(btnSize, keyBindsPresets.first, (arg) -> {});

        playerAddBtn = new GameButton(btnSize, "add player", Game.mechanics::addPlayer);
        playerUpdateBtn = new GameButton(btnSize, "update player", Game.mechanics::updatePlayer);
        playerRemoveBtn = new GameButton(btnSize, "remove player", Game.mechanics::removePlayer);

        playerNameInput = new GameInput(btnSize);
        playerCreatorWarningLabel = new GameLabel("");

        playerNameInput.setAlphanumeric(true);


        playersCreatorPanel.addComponent(new GameLabel("Create players", 48));

        playersCreatorPanel.addComponent(new GameLabel("select player:"));
        playersCreatorPanel.addComponent(playerSelectCombo);

        playersCreatorPanel.addComponent(new GameLabel("select player class:"));
        playersCreatorPanel.addComponent(playerClassCombo);

        playersCreatorPanel.addComponent(new GameLabel("select player key binds:"));
        playersCreatorPanel.addComponent(playerKeyBindsCombo);

        playersCreatorPanel.addComponent(new GameLabel("insert player name:"));
        playersCreatorPanel.addComponent(playerNameInput);

        playersCreatorPanel.addComponent(playerCreatorWarningLabel);

        playersCreatorPanel.addComponent(playerAddBtn);
        playersCreatorPanel.addComponent(playerUpdateBtn);
        playersCreatorPanel.addComponent(playerRemoveBtn);

        playersCreatorPanel.addComponent(new GameButton(btnSize, "accept all", Game.mechanics::showWelcomePanel));

        loadArena(0);
    }

    public void start() {
        showWelcomePanel();
    }

    // welcome panel methods:

    private void showWelcomePanel() {
        gameInterface.setPanel(welcomePanel);
    }

    private void loadArena(int number) {
        gameInstance.setArena(ArenaLoader.load(arenas.second[number], blocksTextures));
        gameScreen.recenter();
    }

    // players creator panel methods:

    private void showPlayersCreatorPanel() {
        gameInterface.setPanel(playersCreatorPanel);
        newPlayerPanel();
    }

    private void existingPlayerPanel() {
        //PlayerData currentPlayer = selectedPlayer > -1 ? players.get(selectedPlayer) : null;

        String[] playerNames = getPlayersNames();

        boolean anyPlayers = playerNames.length > 0;

        playerSelectCombo.setOptions(playerNames);
        playerSelectCombo.setEnabled(anyPlayers);

        playerAddBtn.setEnabled(false);
        playerUpdateBtn.setEnabled(anyPlayers);
        playerRemoveBtn.setEnabled(anyPlayers);

        playerSelectCombo.refresh();
        playerKeyBindsCombo.refresh();

        playerAddBtn.refresh();
        playerUpdateBtn.refresh();
        playerRemoveBtn.refresh();
    }

    private void newPlayerPanel() {
        String[] keyBinds = getAvailableKeyBinds(null);

        playerSelectCombo.setOptions(getPlayersNames());
        playerSelectCombo.setSelectedIndex(players.size());

        playerKeyBindsCombo.setOptions(keyBinds);

        playerAddBtn.setEnabled(keyBinds.length > 0);
        playerUpdateBtn.setEnabled(false);
        playerRemoveBtn.setEnabled(false);

        playerSelectCombo.refresh();

        playerAddBtn.refresh();
        playerUpdateBtn.refresh();
        playerRemoveBtn.refresh();
    }

    private void selectPlayer(int index) {
        if (index < players.size() - 1) {
            newPlayerPanel();
            return;
        }

        selectedPlayer = index;
        PlayerData currentPlayer = players.get(index);

        playerKeyBindsCombo.setOptions(getAvailableKeyBinds(currentPlayer));
        playerKeyBindsCombo.refresh();

        playerNameInput.setValue(currentPlayer.name);
        playerClassCombo.setSelectedIndex(getPresetIndex(currentPlayer.playerClass));
        playerKeyBindsCombo.setSelectedIndex(getKeyBindsIndex(currentPlayer.keyBinds));

        playerNameInput.refresh();
        playerClassCombo.refresh();
        playerKeyBindsCombo.refresh();
    }

    private int getPresetIndex(Player.Type playerClass) {
        for (int index = 0; index < playerPresets.length; index++) {
            if (playerPresets[index].playerClass == playerClass) {
                return index;
            }
        }
        return 0;
    }

    private int getKeyBindsIndex(Player.KeyBinds keyBinds) {
        for (int index = 0; index < keyBindsPresets.second.length; index++) {
            if (keyBindsPresets.second[index] == keyBinds) {
                return index;
            }
        }
        return 0;
    }

    private String[] getPlayersNames() {
        String[] result = new String[players.size() + 1];
        int index = 0;

        for (PlayerData player : players) {
            result[index] = player.name;
            index++;
        }

        result[result.length - 1] = "<New player>";

        return result;
    }

    private String[] getPlayersClasses() {
        String[] result = new String[playerPresets.length];
        int index = 0;

        for (PlayerPreset preset : playerPresets) {
            result[index] = preset.name;
            index++;
        }
        return result;
    }

    private String[] getAvailableKeyBinds(PlayerData playerToSkip) {
        List<String> result = new ArrayList<>(); // new String[keyBindsPresets.first.length];
        List<Player.KeyBinds> availableKeyBinds = new ArrayList<>(Arrays.asList(keyBindsPresets.second));

        for (PlayerData player : players) {
            if (player == playerToSkip) {
                continue;
            }
            availableKeyBinds.remove(player.keyBinds);
        }

        for(int index = 0; index < keyBindsPresets.first.length; index++) {
            if (availableKeyBinds.contains(keyBindsPresets.second[index])) {
                result.add(keyBindsPresets.first[index]);
            }
        }
        return result.toArray(new String[0]);
    }

    private void addPlayer() {
        PlayerPreset preset = playerPresets[playerClassCombo.getSelectedIndex()];
        String name = playerNameInput.getValue();

        if (validateName(name, isPlayerNameUsed(name, null))) {
            players.add(new PlayerData(name, preset.texturePath, preset.playerClass, keyBindsPresets.second[playerKeyBindsCombo.getSelectedIndex()]));

            newPlayerPanel();
            //playerSelectCombo.setSelectedIndex(players.size());
            playerCreatorWarningLabel.setText("");
        }
    }

    private void updatePlayer() {
        PlayerPreset preset = playerPresets[playerClassCombo.getSelectedIndex()];
        PlayerData player = players.get(selectedPlayer);

        String name = playerNameInput.getValue();

        if (validateName(name, isPlayerNameUsed(name, player.name))) {
            player.name = name;
            player.texturePath = preset.texturePath;
            player.playerClass = preset.playerClass;
            player.keyBinds = keyBindsPresets.second[playerKeyBindsCombo.getSelectedIndex()];

            existingPlayerPanel();
            playerCreatorWarningLabel.setText("");
        }
    }

    private void removePlayer() {
        players.remove(selectedPlayer);
        newPlayerPanel();
    }

    private boolean isPlayerNameUsed(String name, String nameToSkip) {
        return !name.equals(nameToSkip) && Arrays.asList(getPlayersNames()).contains(name);
    }

    private boolean validateName(String name, boolean condition) {
        if (name.equals("")) {
            playerCreatorWarningLabel.setText("name field cannot be empty");
        } else if (condition) {
            playerCreatorWarningLabel.setText("this name is already in use");
        } else {
            return true;
        }
        return false;
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

    private static class PlayerPreset {
        public final String name;
        public final String texturePath;
        public final Player.Type playerClass;

        private PlayerPreset(String name, String texturePath, Player.Type playerClass) {
            this.name = name;
            this.texturePath = texturePath;
            this.playerClass = playerClass;
        }
    }

    private static class PlayerData {
        public String name;
        public String texturePath;
        public Player.Type playerClass;
        public Player.KeyBinds keyBinds;

        private PlayerData(String name, String texturePath, Player.Type playerClass, Player.KeyBinds keyBinds) {
            this.name = name;
            this.texturePath = texturePath;
            this.playerClass = playerClass;
            this.keyBinds = keyBinds;
        }
    }
}
