package com.javagame.game.player;

import com.javagame.game.GameInstance;
import com.javagame.game.IEntity;

import java.awt.*;

public abstract class Player implements IEntity {
    private int health;
    private int stamina;
    private int mana;

    private int x;
    private int y;

    private int level = 1;

    private Image idleImage;
    private Image attackLeftImage;
    private Image attackRightImage;

    private final int maxHealth;
    private final int maxStamina;
    private final int maxMana;

    private final GameInstance gameInstance;

    public Player(GameInstance gameInstance, int x, int y, int maxHealth, int maxStamina, int maxMana) {
        this.gameInstance = gameInstance;

        this.maxHealth = maxHealth;
        this.maxStamina = maxStamina;
        this.maxMana = maxMana;

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


    public void attack();

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

    }

    private void tryChangePos(int newX, int newY) {
        int field = gameInstance.getBoardField(newX, newY);
        if (field == -1) {

        } else if (field == 0) {
            x = newX;
            y = newY;
        }
    }

}
