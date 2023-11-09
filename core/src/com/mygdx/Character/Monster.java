package com.mygdx.Character;

import com.mygdx.Character.Character;
import com.mygdx.game.Room;
import com.mygdx.game.Tile;
import com.mygdx.item.Item;
import com.mygdx.item.Weapon;

import java.util.List;
import java.util.Map;

public class Monster extends Character {
    private  float dropWeaponProb;
    public Monster(String name, Map<Stat, Integer> stat, Weapon weaponEquiped, Tile position) {
        super(name, stat, weaponEquiped, position);
    }
    public void move(Room room, Tile position) {
        List<Tile> neighbors = room.getNeighbors(position,1);
        Tile destination = neighbors.get((int) (Math.random() * neighbors.size()));
        setPosition(destination);
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
