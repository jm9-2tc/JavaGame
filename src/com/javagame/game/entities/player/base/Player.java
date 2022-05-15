package com.javagame.game.entities.player.base;

import com.javagame.Constants;
import com.javagame.game.entities.AttackMatrix;
import com.javagame.game.GameInstance;
import com.javagame.game.entities.IEntity;
import com.javagame.game.arena.Arena;

import java.awt.*;

public class Player implements IEntity {
    public final String name;

    public final Type type;
    public final KeyBinds keyBinds;
    public final Image texture;
    public final GameInstance gameInstance;
    public final AttackMatrix attackMatrix;

    public final int maxHealth;
    public final int maxStamina;
    public final int maxMana;
    public final int stepInterval;
    private final int damage;
    private int health;
    private int stamina;
    private int mana;

    private int x;
    private int y;

    private long lastStep = 0;

    private boolean attackDisabled = false;

    public Player(String name, GameInstance gameInstance, KeyBinds keyBinds, Image texture, Type type, AttackMatrix attackMatrix, int damage, int x, int y, int stepInterval, int maxHealth, int maxStamina, int maxMana) {
        this.gameInstance = gameInstance;
        this.keyBinds = keyBinds;
        this.texture = texture;
        this.type = type;
        this.name = name;

        this.maxHealth = maxHealth;
        this.maxStamina = maxStamina;
        this.maxMana = maxMana;

        this.attackMatrix = attackMatrix;
        this.damage = damage;

        this.x = x;
        this.y = y;

        this.stepInterval = stepInterval;

        this.health = maxHealth;
        this.stamina = maxStamina;
        this.mana = maxMana;
    }

    public void loseHealth(int amount) {
        setHealth(health - amount);
        if (health == 0) {
            gameInstance.handleGameOver(this);
        }
    }

    public void restoreHealth(int amount) {
        setHealth(health + amount);
    }

    public void loseStamina(int amount) {
        setStamina(stamina - amount);
    }

    public void restoreStamina(int amount) {
        setStamina(stamina + amount);
    }

    public void loseMana(int amount) {
        setMana(mana - amount);
    }

    public void restoreMana(int amount) {
        setMana(mana + amount);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = Math.max(0, Math.min(stamina, maxStamina));
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = Math.max(0, Math.min(mana, maxMana));
    }

    public Image getTexture() {
        return texture;
    }

    public void handleKeyEvent(int keyCode) {
        if (keyCode == keyBinds.moveUp) {
            tryChangePos(x, y - 1);
        } else if (keyCode == keyBinds.moveDown) {
            tryChangePos(x, y + 1);
        } else if (keyCode == keyBinds.moveLeft) {
            tryChangePos(x - 1, y);
        } else if (keyCode == keyBinds.moveRight) {
            tryChangePos(x + 1, y);
        }

        if (keyCode == keyBinds.attack && !attackDisabled) {
            gameInstance.attack(attackMatrix, x, y, damage);
        }
    }

    public boolean onCollideWith(Arena arena, int newX, int newY) {
        attackDisabled = false;

        switch (arena.blocks[newX][newY]) {
            case Constants.BLOCK_WALL:
                return true;

            case Constants.BLOCK_SPIKY_BUSH:
                loseHealth(Constants.DAMAGE_COLLIDE_SPIKY_BUSH);
                break;

            case Constants.BLOCK_WATER:
                attackDisabled = true;
                break;

            case Constants.BLOCK_HP_POTION:
                setHealth(health + Constants.POTION_HEALTH_RESTORED);
                arena.blocks[newX][newY] = Constants.BLOCK_GRASS;
                break;
        }
        return false;
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private void tryChangePos(int newX, int newY) {
        long frame = gameInstance.gameEvents.getFrame();

        if (lastStep + stepInterval > frame) {
            return;
        }

        lastStep = frame;

        if (gameInstance.isFieldEmpty(newX, newY)) {
            Arena arena = gameInstance.getArena();
            if (!onCollideWith(arena, newX, newY)) {
                x = newX;
                y = newY;
            }
        }
    }

    public enum Type {
        KNIGHT,
        ARCHER,
        NINJA,
        SUMO
    }

    public static class KeyBinds {
        public final int moveUp;
        public final int moveDown;
        public final int moveLeft;
        public final int moveRight;
        public final int attack;

        public KeyBinds(int moveUp, int moveDown, int moveLeft, int moveRight, int attack) {
            this.moveUp = moveUp;
            this.moveDown = moveDown;
            this.moveLeft = moveLeft;
            this.moveRight = moveRight;
            this.attack = attack;
        }
    }
}
