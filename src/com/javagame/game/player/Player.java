package com.javagame.game.player;

import com.javagame.Constants;
import com.javagame.game.GameInstance;
import com.javagame.game.IEntity;

import java.awt.*;

public abstract class Player implements IEntity {
    private int health;
    private int stamina;
    private int mana;

    private int x;
    private int y;

    private int damage;
    private int attackDistance;

    private int level = 1;

    private Image idleImage;
    private Image attackLeftImage;
    private Image attackRightImage;

    private final Type type;
    private final KeyBinds keyBinds;

    private final int maxHealth;
    private final int maxStamina;
    private final int maxMana;

    private final GameInstance gameInstance;

    public Player(GameInstance gameInstance, KeyBinds keyBinds, Type type, int damage, int attackDistance, int x, int y, int maxHealth, int maxStamina, int maxMana) {
        this.gameInstance = gameInstance;
        this.keyBinds = keyBinds;
        this.type = type;

        this.maxHealth = maxHealth;
        this.maxStamina = maxStamina;
        this.maxMana = maxMana;

        this.damage = damage;
        this.attackDistance = attackDistance;

        this.x = x;
        this.y = y;

        this.health = maxHealth;
        this.stamina = maxStamina;
        this.mana = maxMana;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }

    public void setStamina(int stamina) {
        this.stamina = Math.max(0, Math.min(stamina, maxStamina));
    }

    public void setMana(int mana) {
        this.mana = Math.max(0, Math.min(mana, maxMana));
    }

    public void levelUp() {
        level++;
    }

    public void loseHealth(int amount) {
        setHealth(health - amount);
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

    public void restoreMana(int amount){
        setMana(mana + amount);
    }

    public void loadImages(Image idle, Image attackLeft, Image attackRight) {
        this.idleImage = idle;
        this.attackLeftImage = attackLeft;
        this.attackRightImage = attackRight;
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
    }

    private void tryChangePos(int newX, int newY) {
        int field = gameInstance.getBoardField(newX, newY);
        if (field == -1) {
            setHealth(health - Constants.DAMAGE_COLLIDE_WALL);
        } else if (field == 0) {
            x = newX;
            y = newY;
        }
    }

    public void attack(int attackX, int attackY) {
        int distX = x - attackX;
        int distY = y - attackY;

        if(Math.sqrt((distX * distX) + (distY * distY)) <= attackDistance) {
            gameInstance.attackPlayerById(gameInstance.getBoardField(attackX, attackY), damage);
        }
    }

    public static class KeyBinds {
        public final int moveUp;
        public final int moveDown;
        public final int moveLeft;
        public final int moveRight;


        public KeyBinds(int moveUp, int moveDown, int moveLeft, int moveRight) {
            this.moveUp = moveUp;
            this.moveDown = moveDown;
            this.moveLeft = moveLeft;
            this.moveRight = moveRight;
        }
    }

    public enum Type {
        WARRIOR,
        HEALER,
        ARCHER,
        MAGE
    }
}
