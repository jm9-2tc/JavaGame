package com.javagame.game.player;

import com.javagame.Constants;
import com.javagame.game.GameInstance;
import com.javagame.game.IEntity;
import com.javagame.game.arena.Arena;
import com.javagame.resources.Resources;

import java.awt.*;

public abstract class Player implements IEntity {
    public final AttackMatrix attackMatrix;

    public final Type type;
    public final KeyBinds keyBinds;

    public final int maxHealth;
    public final int maxStamina;
    public final int maxMana;

    public final int stepInterval;

    private final GameInstance gameInstance;

    private int health;
    private int stamina;
    private int mana;

    private int x;
    private int y;

    private long lastStep = 0;

    private State state;

    private Image idleImage;
    private Image attackLeftImage;
    private Image attackRightImage;

    private int damage;

    private int level = 1;

    private boolean attackDisabled = false;

    public Player(GameInstance gameInstance, KeyBinds keyBinds, Type type, AttackMatrix attackMatrix, int damage, int x, int y, int stepInterval, int maxHealth, int maxStamina, int maxMana) {
        this.gameInstance = gameInstance;
        this.keyBinds = keyBinds;
        this.type = type;

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

        this.state = State.IDLE;
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

    public void loadImages(String idle, String attackLeft, String attackRight) {
        this.idleImage = Resources.loadTexture("player/" + idle);
        this.attackLeftImage = Resources.loadTexture("player/" + attackLeft);
        this.attackRightImage = Resources.loadTexture("player/" + attackRight);
    }

    public Image getTexture() {
        switch (state) {
            case IDLE:
                return idleImage;

            case ATTACK_LEFT:
                return attackLeftImage;

            case ATTACK_RIGHT:
                return attackRightImage;
        }
        return null;
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

    public int getStamina() {
        return stamina;
    }

    public int getMana() {
        return mana;
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

        if (keyCode == keyBinds.attack) {
            attack();
        }
    }

    private void tryChangePos(int newX, int newY) {
        long frame = gameInstance.gameEvents.getFrame();

        if (lastStep + stepInterval > frame) {
            return;
        }

        lastStep = frame;

        //System.out.println(frame);

        if (gameInstance.isFieldEmpty(newX, newY)) {
            Arena arena = gameInstance.getArena();
            if (!onCollideWith(arena, newX, newY)) {
                x = newX;
                y = newY;
            }
        }
    }

    public boolean onCollideWith(Arena arena, int newX, int newY) {
        attackDisabled = false;

        switch (arena.blocks[newX][newY]) {
            case Constants.BLOCK_WALL:
                return true;

            case Constants.BLOCK_SPIKY_BUSH:
                setHealth(health - Constants.DAMAGE_COLLIDE_SPIKY_BUSH);
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

    public void attack() {
        if (attackDisabled) return;

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

        /*
        if(Math.sqrt((distX * distX) + (distY * distY)) <= attackDistance) {
            gameInstance.attackPlayerById(gameInstance.boardPlayers[attackX][attackY], damage);
        }
        */
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

    public enum State {
        IDLE,
        ATTACK_LEFT,
        ATTACK_RIGHT
    }

    public enum Type {
        WARRIOR,
        HEALER,
        ARCHER,
        MAGE
    }
}
