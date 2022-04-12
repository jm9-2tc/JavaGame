package com.javagame.game;

import com.javagame.Constants;
import com.javagame.game.player.Player;

public class GameInstance {
    public final GameEvents gameEvents;

    public final Player[] players;

    public final byte[][] boardPlayers;
    public final byte[][] boardEnvironment;

    public final int width;
    public final int height;

    public GameInstance(GameEvents gameEvents, Player[] players, int width, int height) {
        this.gameEvents = gameEvents;
        this.players = players;

        this.width = width;
        this.height = height;

        this.boardPlayers = new byte[width][height];
        this.boardEnvironment = new byte[width][height];
    }

    public void tryAttackPlayer(int x, int y, int damage) {
        if (x < 0 || y < 0 || x >= width || y >= height) return;
        byte playerId = boardPlayers[x][y];
        if (playerId < 0) return;

        Player player = players[playerId];
        if (player != null) {
            player.loseHealth(damage);
        }
    }

    public void handleGameOver(String name, int x, int y) {
        //TODO: show UI

        while (true) {
            int newX = (int) (Math.random() * width);
            int newY = (int) (Math.random() * height);

            if (boardEnvironment[newX][newY] == Constants.BLOCK_GRASS) {
                boardPlayers[newX][newY] = boardPlayers[x][y];
                break;
            }
        }
        boardPlayers[x][y] = -1;
    }
}
