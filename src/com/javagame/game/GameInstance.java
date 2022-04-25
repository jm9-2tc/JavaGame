package com.javagame.game;

import com.javagame.Constants;
import com.javagame.game.arena.Arena;
import com.javagame.game.entities.AttackMatrix;
import com.javagame.game.entities.monster.Monster;
import com.javagame.game.entities.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameInstance {
    public final GameEvents gameEvents;

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Player[] players;
    public byte[][] boardPlayers;

    public int width = 0;
    public int height = 0;

    public boolean ready = false;

    public final List<Monster> monsters;

    private Arena arena;

    public GameInstance(GameEvents gameEvents) {
        this.gameEvents = gameEvents;
        this.players = new Player[0];
        this.monsters = new ArrayList<>();
    }

    public boolean isFieldEmpty(int x, int y) {
        if (x < 0 || x >= boardPlayers.length || y < 0 || y >= boardPlayers[x].length) {
            return false;
        }
        return boardPlayers[x][y] == 0;
    }

    public void attack(AttackMatrix matrix, int x, int y, int damage) {
        switch (matrix) {
            case ALL:
                for (int a = x - 2; a <= x + 2; a++) {
                    for (int b = y - 2; b <= y + 2; b++) {
                        if (a == x && b == y) continue;
                        tryAttackPlayer(a, b, damage);
                    }
                }
                break;

            case SQUARE:
                for (int a = x - 1; a <= x + 1; a++) {
                    for (int b = y - 1; b <= y + 1; b++) {
                        if (a == x && b == y) continue;
                        tryAttackPlayer(a, b, damage);
                    }
                }
                break;

            case CROSS:
                tryAttackPlayer(x - 1, y, damage);
                tryAttackPlayer(x + 1, y, damage);
                tryAttackPlayer(x, y - 1, damage);
                tryAttackPlayer(x, y + 1, damage);
                break;

            case CORNERS:
                tryAttackPlayer(x - 1, y - 1, damage);
                tryAttackPlayer(x + 1, y - 1, damage);
                tryAttackPlayer(x - 1, y + 1, damage);
                tryAttackPlayer(x + 1, y + 1, damage);
                break;

        }
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
        this.boardPlayers = new byte[arena.width][arena.height];
    }
}
