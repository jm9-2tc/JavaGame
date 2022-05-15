package com.javagame.game;

import com.javagame.Constants;
import com.javagame.game.arena.Arena;
import com.javagame.game.entities.AttackMatrix;
import com.javagame.game.entities.monster.Dino;
import com.javagame.game.entities.monster.Glazojad;
import com.javagame.game.entities.monster.base.Monster;
import com.javagame.game.entities.player.base.Player;
import com.javagame.resources.Resources;

import java.util.*;

import static com.javagame.Game.gameInstance;
import static com.javagame.game.entities.AttackMatrix.CROSS;

public class GameInstance {
    public final GameEvents gameEvents;
    public final List<Monster> monsters;
    public Player[] players;
    //public byte[][] boardPlayers;
    private Arena arena;

    public GameInstance(GameEvents gameEvents) {
        this.gameEvents = gameEvents;
        this.players = new Player[0];
        this.monsters = new ArrayList<>();
    }

    public boolean isFieldEmpty(int x, int y) {
        if (x < 0 || x >= arena.width || y < 0 || y >= arena.height) {
            return false;
        }
        for (Monster monster : monsters) {
            if (monster.getX() == x && monster.getY() == y) {
                return false;
            }
        }
        for (Player player : players) {
            if (player.getX() == x && player.getY() == y) {
                return false;
            }
        }
        return true;//boardPlayers[x][y] == 0;
    }

    public void attack(AttackMatrix matrix, int x, int y, int damage) {
        switch (matrix) {
            case ALL:
                for (int a = x - 2; a <= x + 2; a++) {
                    for (int b = y - 2; b <= y + 2; b++) {
                        if (a == x && b == y) continue;
                        tryAttack(a, b, damage);
                    }
                }
                break;

            case SQUARE:
                for (int a = x - 1; a <= x + 1; a++) {
                    for (int b = y - 1; b <= y + 1; b++) {
                        if (a == x && b == y) continue;
                        tryAttack(a, b, damage);
                    }
                }
                break;

            case CROSS:
                tryAttack(x - 1, y, damage);
                tryAttack(x + 1, y, damage);
                tryAttack(x, y - 1, damage);
                tryAttack(x, y + 1, damage);
                break;

            case CORNERS:
                tryAttack(x - 1, y - 1, damage);
                tryAttack(x + 1, y - 1, damage);
                tryAttack(x - 1, y + 1, damage);
                tryAttack(x + 1, y + 1, damage);
                break;

            case SAMOJEB:
                tryAttack(x, y, damage);
                break;
        }
    }

    public void tryAttack(int x, int y, int damage) {
        tryAttackPlayer(x, y, damage);
        tryAttackMonster(x, y, damage);
    }

    public void tryAttackPlayer(int x, int y, int damage) {
        if (x < 0 || y < 0 || x >= arena.width || y >= arena.height) return;
        int playerId = -1;
        for (Player player : players) {
            if (player.getX() == x && player.getY() == y) {
                player.loseHealth(damage);
                return;
            }
        }
    }

    public void tryAttackMonster(int x, int y, int damage) {
        if (x < 0 || y < 0 || x >= arena.width || y >= arena.height) return;
        for (Monster monster : monsters) {
            if (monster.getX() == x && monster.getY() == y) {
                monster.loseHealth(damage);
            }
        }
        updateMonsterList();
    }

    public void spawnPlayer(Player player, byte playerId) {
        while (true) {
            int newX = (int) (Math.random() * arena.width);
            int newY = (int) (Math.random() * arena.height);
            for (Monster monster : monsters) {
                if (monster.getX() == newX && monster.getY() == newY) {
                    spawnPlayer(player, playerId);
                }
            }
            if (isFieldEmpty(newX,newY) && arena.blocks[newX][newY] == Constants.BLOCK_GRASS) {
                //boardPlayers[newX][newY] = playerId;
                player.moveTo(newX, newY);
                break;
            }
        }
    }

    public void handleGameOver(String name, int x, int y) {
        //TODO: show UI

        while (true) {
            int newX = (int) (Math.random() * arena.width);
            int newY = (int) (Math.random() * arena.height);

            if (arena.blocks[newX][newY] == Constants.BLOCK_GRASS) {
                arena.blocks[newX][newY] = arena.blocks[x][y];
                break;
            }
        }
        //boardPlayers[x][y] = -1;
    }

    public void spawnMonster(Monster newMonster) {
        if (monsters.size() < Constants.MONSTER_LIMIT) {
            while (true) {
                int newX = (int) (Math.random() * arena.width);
                int newY = (int) (Math.random() * arena.height);
                for (Monster monster : monsters) {
                    if (monster.getX() == newX && monster.getY() == newY) {
                        spawnMonster(newMonster);
                    }
                }
                if (isFieldEmpty(newX, newY) && arena.blocks[newX][newY] == Constants.BLOCK_GRASS) {
                    newMonster.setX(newX);
                    newMonster.setY(newY);
                    monsters.add(newMonster);
                    break;
                }
            }
        }
    }

    public void startSpawner() {
        TimerTask spawnerTask = new SpawnerTask();
        Timer t = new Timer(true);
        t.scheduleAtFixedRate(spawnerTask, 0, 10000);
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
        byte index = 0;

        for (Player player : players) {
            spawnPlayer(player, index++);
        }
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
        //this.boardPlayers = new byte[arena.width][arena.height];
    }

    public void updateMonsterList(){
        int i = 0;
        while(i< monsters.size()){
            if(monsters.get(i).getHealth() == 0){
                monsters.remove(i);
            }
            else{
                i++;
            }
        }
    }

    public class SpawnerTask extends TimerTask {
        @Override
        public void run() {
            Random r = new Random();
            int los = r.nextInt(2);
            Monster newMonster;
            switch(los) {
                case 0:
                    newMonster = new Dino(gameInstance);
                    break;
                case 1:
                    newMonster = new Glazojad(gameInstance);
                    break;
                default:
                    newMonster = new Monster(gameInstance,Resources.loadTexture("default.png"),CROSS,0);
                    break;
            }
            spawnMonster(newMonster);
        }
    }
}
