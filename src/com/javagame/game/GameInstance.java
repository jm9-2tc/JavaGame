package com.javagame.game;

import com.javagame.game.player.Player;
import com.javagame.game.player.Team;

import java.util.List;
import java.util.Map;

public class GameInstance {
    public final GameEvents gameEvents;

    public final Team[] teams;
    public final Player[] players;

    public final int[][] board;
    public final int width;
    public final int height;

    public GameInstance(GameEvents gameEvents, Team[] teams, Player[] players, int width, int height) {
        this.gameEvents = gameEvents;
        this.teams = teams;
        this.players = players;

        this.width = width;
        this.height = height;
        this.board = new int[width][height];
    }

    public boolean validatePlayerSelection(int expectedTeam, int id) {
        return players[id].team == teams[expectedTeam];
    }

    public void attackPlayerById(int id, int damage) {
        Player player = players.get(id);
        if (player != null) {
            player.loseHealth(damage);
        }
    }

    public int getBoardField(int x, int y) {
        if (x < 0 || y < 0 || x > width || y > height) return -1;
        return board[x][y];
    }

    public void showGameOver(String name, int x, int y) {
        //TODO: show UI

        while (true) {
            int newX = (int) (Math.random() * width);
            int newY = (int) (Math.random() * height);

            if (board[newX][newY] == 0) {
                board[newX][newY] = board[x][y];
                break;
            }
        }
        board[x][y] = 0;
    }
}
