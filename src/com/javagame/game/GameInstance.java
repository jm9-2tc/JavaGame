package com.javagame.game;

import com.javagame.game.player.Player;
import com.javagame.game.player.Team;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameInstance {
    public final List<Team> teams;
    public final Map<Integer, Player> players;

    public final int[][] board;
    public final int width;
    public final int height;

    public GameInstance(List<Team> teams, Map<Integer, Player> players, int width, int height) {
        this.teams = teams;
        this.players = players;

        this.width = width;
        this.height = height;
        this.board = new int[width][height];
    }

    public void handleKeyEvent(int keyCode) {

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
