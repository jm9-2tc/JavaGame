package com.javagame.game;

import com.javagame.game.player.Player;

public class GameEvents implements Runnable {
    private final GameInstance gameInstance;

    public int currentTeamNumber;
    public int currentPlayerNumber;

    private boolean playerSelected = false;
    private boolean playerDidAction = false;

    public GameEvents(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
    }

    public void play() {
        while(playerSelected && playerDidAction)
        currentTeamNumber = currentTeamNumber < gameInstance.teams.length - 1 ? currentTeamNumber + 1 : 0;
    }

    public void handleClickEvent(int x, int y) {
        if (!playerSelected) {
            int nextPlayerNumber = gameInstance.getBoardField(x, y);

            if (gameInstance.validatePlayerSelection(currentTeamNumber, nextPlayerNumber)) {
                currentPlayerNumber = nextPlayerNumber;
                playerSelected = true;
            }
        } else {
            playerDidAction = true;
        }
    }

    public void handleKeyEvent(int keyCode) {
        if (playerSelected) {
            playerDidAction = true;
        }
    }

    @Override
    public void run() {
        play();
    }
}
