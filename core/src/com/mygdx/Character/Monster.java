package com.mygdx.Character;

import com.mygdx.Character.Character;
import com.mygdx.game.Tile;
import com.mygdx.item.Item;
import com.mygdx.item.Weapon;

import java.util.Map;

public class Monster extends Character {
    private  float dropWeaponProb;
    public Monster(String name, Map<Stat, Integer> stat, Weapon weaponEquiped, Tile position) {
        super(name, stat, weaponEquiped, position);
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
