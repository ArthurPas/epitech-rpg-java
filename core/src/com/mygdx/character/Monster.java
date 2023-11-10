package com.mygdx.character;

import com.mygdx.item.Item;
import com.mygdx.item.Weapon;

import java.util.Map;

public class Monster extends Character {
    private  float dropWeaponProb;
    public Monster(String name, Map<Stat, Integer> stat, Weapon weaponEquiped) {
        super(name, stat, weaponEquiped);
    }

    public float getDropWeaponProb() {
        return dropWeaponProb;
    }

    public void setDropWeaponProb(float dropWeaponProb) {
        this.dropWeaponProb = dropWeaponProb;
    }

    protected Item drop(float prob) {
        return null;
    }
}
