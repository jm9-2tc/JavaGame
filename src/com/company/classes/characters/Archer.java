package com.company.classes.characters;

import com.company.classes.AttackType;
import com.company.classes.CharacterClass;

import java.awt.event.KeyEvent;

public class Archer extends CharacterClass {
    public Archer(String name) {
        super();

        this.setX(0);
        this.setY(0);
        this.setName(name);

        setup();
    }

    public Archer(String name, int x, int y, int key1, int key2, int key3, int key4, int key5, int key6) {
        super(key1, key2, key3, key4, key5, key6);

        this.setX(x);
        this.setY(y);
        this.setName(name);

        setup();
    }

    private void setup() {
        this.setLevel(0);
        this.setMaxHealthPoints(1000);
        this.setHealthPoints(1000);
        this.setManaPoints(200);
        this.setMaxManaPoints(200);
        this.setAttackType(AttackType.PHYSICAL);
        this.setAttackAmount(5);

        this.uploadImage("1.png", "2.png", "3.png");
    }
}
