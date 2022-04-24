package com.javagame.game.monster;

import com.javagame.Constants;
import com.javagame.game.GameInstance;
import com.javagame.game.IEntity;
import com.javagame.game.arena.Arena;
import com.javagame.game.player.AttackMatrix;
import com.javagame.game.player.Player;

import java.awt.*;

public class Monster implements IMonster {

    private int health;
    private int x;
    private int y;

    private final int damage;
    public final Image texture;
    public final GameInstance gameInstance;
    public final AttackMatrix attackMatrix;

    public Monster(GameInstance gameInstance, Image texture, AttackMatrix attackMatrix, int damage) {
        this.x = 0;
        this.y = 0;
        this.attackMatrix = attackMatrix;
        this.gameInstance = gameInstance;
        this.texture = texture;
        this.damage = damage;
    }

    public void move() {
        attack();
        Player[] players = gameInstance.getPlayers();
        Player nearestPlayer = players[0];
        double dist = Math.sqrt(Math.pow(nearestPlayer.getX(), 2) + Math.pow(nearestPlayer.getY(), 2));
        for (Player player : players) {
            if (dist > Math.sqrt(Math.pow(player.getX(), 2) + Math.pow(player.getY(), 2))) {
                nearestPlayer = player;
            }
        }
        if (nearestPlayer.getX() < this.x) tryChangePos(this.x - 1, this.y);
        else if (nearestPlayer.getX() > this.x) tryChangePos(this.x + 1, this.y);
        if (nearestPlayer.getY() < this.y) tryChangePos(this.x, this.y - 1);
        else if (nearestPlayer.getY() > this.y) tryChangePos(this.x, this.y + 1);
    }

    private void tryChangePos(int newX, int newY) {
        if (gameInstance.isFieldEmpty(newX, newY)) {
            Arena arena = gameInstance.getArena();
            if (!onCollideWith(arena, newX, newY)) {
                x = newX;
                y = newY;
            }
        }
    }

    public boolean onCollideWith(Arena arena, int newX, int newY) {

        switch (arena.blocks[newX][newY]) {
            case Constants.BLOCK_WALL:
                return true;

            case Constants.BLOCK_SPIKY_BUSH:
                setHealth(health - Constants.DAMAGE_COLLIDE_SPIKY_BUSH);
                break;

            case Constants.BLOCK_WATER:
                break;

            case Constants.BLOCK_HP_POTION:
                setHealth(health + Constants.POTION_HEALTH_RESTORED);
                arena.blocks[newX][newY] = Constants.BLOCK_GRASS;
                break;
        }
        return false;
    }

    /*private byte[][] generateHeatMap(byte[][] arena){

        return arena;
    }*/

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public void levelUp() {

    }

    @Override
    public void attack() {
        switch (attackMatrix) {
            case ALL:
                for (int a = x - 2; a <= x + 2; a++) {
                    for (int b = y - 2; b <= y + 2; b++) {
                        if (a == x && b == y) continue;
                        gameInstance.tryAttackPlayer(a, b, damage);
                    }
                }
                break;

            case SQUARE:
                for (int a = x - 1; a <= x + 1; a++) {
                    for (int b = y - 1; b <= y + 1; b++) {
                        if (a == x && b == y) continue;
                        gameInstance.tryAttackPlayer(a, b, damage);
                    }
                }
                break;

            case CROSS:
                gameInstance.tryAttackPlayer(x - 1, y, damage);
                gameInstance.tryAttackPlayer(x + 1, y, damage);
                gameInstance.tryAttackPlayer(x, y - 1, damage);
                gameInstance.tryAttackPlayer(x, y + 1, damage);
                break;

            case CORNERS:
                gameInstance.tryAttackPlayer(x - 1, y - 1, damage);
                gameInstance.tryAttackPlayer(x + 1, y - 1, damage);
                gameInstance.tryAttackPlayer(x - 1, y + 1, damage);
                gameInstance.tryAttackPlayer(x + 1, y + 1, damage);
                break;

        }
    }

    @Override
    public void loseHealth(int amount) {

    }
}
