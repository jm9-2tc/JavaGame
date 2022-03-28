package com.company.classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class CharacterClass implements BaseClass {
    private int healthPoints = 200;
    private int manaPoints;
    private int level;
    private AttackType attackType;
    private int attackAmount;
    private String name;
    private int maxHealthPoints;
    private int maxManaPoints;

    public final int id;

    public final int keyMoveUp;
    public final int keyMoveDown;
    public final int keyMoveLeft;
    public final int keyMoveRight;
    public final int keyAttackLeft;
    public final int keyAttackRight;

    /*public CharacterClass(int x, int y, int leftKey, int rightKey, int upKey) {

    }*/

    public CharacterClass(int id) {
        this.id = id;
        this.keyMoveUp= KeyEvent.VK_W;
        this.keyMoveDown = KeyEvent.VK_S;
        this.keyMoveLeft = KeyEvent.VK_A;
        this.keyMoveRight = KeyEvent.VK_D;
        this.keyAttackLeft = KeyEvent.VK_Q;
        this.keyAttackRight = KeyEvent.VK_E;
    }

    public CharacterClass(int id, int keyMoveUp, int keyMoveDown, int keyMoveLeft, int keyMoveRight, int keyAttackLeft, int keyAttackRight) {
        this.id = id;
        this.keyMoveUp= keyMoveUp;
        this.keyMoveDown = keyMoveDown;
        this.keyMoveLeft = keyMoveLeft;
        this.keyMoveRight = keyMoveRight;
        this.keyAttackLeft = keyAttackLeft;
        this.keyAttackRight = keyAttackRight;
    }

    public void setHealthPoints(int healthPoints) {
        if (healthPoints < 0) {
            this.healthPoints = 0;
        } else if (healthPoints > this.maxHealthPoints) {
            this.healthPoints = this.maxHealthPoints;
        }
        else {
            this.healthPoints = healthPoints;
        }
    }

    public void setManaPoints(int manaPoints) {
        if (manaPoints < 0) {
            this.manaPoints = 0;
        } else if (manaPoints > this.maxManaPoints) {
            this.manaPoints = this.maxManaPoints;
        }
        else {
            this.manaPoints = manaPoints;
        }
    }

    public void setLevel(int level) {
        if (level < 1) {
            System.out.println("We can't lose level");
        } else {
            this.level = level;
        }
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public void setAttackAmount(int attackAmount) {
        this.attackAmount = attackAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxHealthPoints(int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    public void setMaxManaPoints(int maxManaPoints) {
        this.maxManaPoints = maxManaPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getManaPoints() {
        return manaPoints;
    }

    public int getLevel() {
        return level;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public int getAttackAmount() {
        return attackAmount;
    }

    public String getName() {
        return name;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public int getMaxManaPoints() {
        return maxManaPoints;
    }

    @Override
    public void attack() {

    }

    @Override
    public void restoreHealth(int amount) {
        setHealthPoints(this.getMaxHealthPoints() + amount);
    }

    @Override
    public void loseHealth(int amount) {
        setHealthPoints(this.getMaxHealthPoints() - amount);
    }

    @Override
    public void restoreMana(int amount) {
        setManaPoints(this.getMaxManaPoints() + amount);
    }

    @Override
    public void loseMana(int amount) {
        setManaPoints(this.getMaxManaPoints() - amount);
    }

    @Override
    public void levelUp() {
        setLevel(getLevel() + 1);
    }

    @Override
    public void info() {
        System.out.println("Name: " + this.name + "\nCurrentHP: " + this.healthPoints + "\nCurrentmana: " + this.maxManaPoints + "\nLevel: " + this.level);
    }

    private Image image, baseImage, attackLeftImage, attackRightImage;
    private int x, y;

    public void setImage(Image image) {
        this.image = image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void uploadImage(String baseImage, String attackLeftImage, String attackRightImage) {
        this.baseImage = new ImageIcon(baseImage).getImage();
        this.attackLeftImage = new ImageIcon(attackLeftImage).getImage();
        this.attackRightImage = new ImageIcon(attackRightImage).getImage();
        setBaseImage();
    }

    public void setBaseImage() {
        this.image = this.baseImage;
    }

    public void setAttackLeftImage() {
        this.image = this.attackLeftImage;
    }

    public void setAttackRightImage() {
        this.image = this.attackRightImage;
    }

    public void tryChangePosition(int newX, int newY) {
        if(gameCells[newX][newY] == 0) {
            gameCells[x][y] = 0;
            gameCells[newX][newY] = id;
            x = newX;
            y = newY;
        } else {
            reduceHealth();
        }
    }

    public abstract void moveLeft();
    public abstract void moveRight();
    public abstract void moveUp();
    public abstract void moveDown();

    public abstract void attackLeft();
    public abstract void attackRight();

    protected void setup() {
        this.setLevel(0);
        this.setMaxHealthPoints(1000);
        this.setHealthPoints(1000);
        this.setManaPoints(200);
        this.setMaxManaPoints(200);
        this.setAttackType(AttackType.PHYSICAL);
        this.setAttackAmount(5);

        this.uploadImage("1.png", "2.png", "3.png");
    }

    private void reduceHealth() {
        setHealthPoints(healthPoints - 50);
    }
}
