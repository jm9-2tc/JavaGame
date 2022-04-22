package com.javagame.game;

import com.javagame.Constants;
import com.javagame.game.arena.Arena;
import com.javagame.game.player.Player;

public class GameInstance {
    public final GameEvents gameEvents;

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Player[] players;
    public final byte[][] boardPlayers;

    public final int width;
    public final int height;

    private Arena arena;

    public GameInstance(GameEvents gameEvents, int width, int height) {
        this.gameEvents = gameEvents;

        this.width = width;
        this.height = height;

        this.boardPlayers = new byte[width][height];
    }

    public boolean isFieldEmpty(int x, int y) {
        if (x < 0 || x > boardPlayers.length || y < 0 || y > boardPlayers[x].length) {
            return false;
        }
        return boardPlayers[x][y] == 0;
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

            if (arena.blocks[newX][newY] == Constants.BLOCK_GRASS) {
                boardPlayers[newX][newY] = boardPlayers[x][y];
                break;
            }
        }
        boardPlayers[x][y] = -1;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }
}
