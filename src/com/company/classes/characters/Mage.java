package com.company.classes.characters;

import com.company.classes.AttackType;
import com.company.classes.CharacterClass;

import java.awt.event.KeyEvent;

public class Mage extends CharacterClass {

    public Mage(String name, int id) {
        super(id);

        this.setX(0);
        this.setY(0);
        this.setName(name);

        setup();
    }

    public Mage(String name, int id, int x, int y, int key1, int key2, int key3, int key4, int key5, int key6) {
        super(id, key1, key2, key3, key4, key5, key6);

        this.setX(x);
        this.setY(y);
        this.setName(name);

        setup();
    }

    @Override
    public void moveLeft() {
        tryChangePosition(getX() - 5, getY());
    }

    @Override
    public void moveRight() {
        tryChangePosition(getX() + 5, getY());
    }

    @Override
    public void moveUp() {
        tryChangePosition(getX(), getY() - 5);
    }

    @Override
    public void moveDown() {
        tryChangePosition(getX(), getY() + 5);
    }

    @Override
    public void attackLeft() {

    }

    @Override
    public void attackRight() {

    }
}
