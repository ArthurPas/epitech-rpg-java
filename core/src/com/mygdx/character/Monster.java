package com.mygdx.character;

import com.mygdx.game.room.Room;
import com.mygdx.game.Tile;
import com.mygdx.item.Item;
import com.mygdx.item.Weapon;

import java.util.List;
import java.util.Map;

public class Monster extends Character {
    private  float dropWeaponProb;

    private float vivacity;
    public Monster(String name, Map<Stat, Integer> stat, Weapon weaponEquiped, Tile position, float dropWeaponProb, float vivacity) {
        super(name, stat, weaponEquiped, position);
        this.dropWeaponProb = dropWeaponProb;
        this.vivacity = vivacity;
    }
    public void move(Room room, Tile position) {
        List<Tile> neighbors = room.getNeighbors(position,1);
        Tile destination = neighbors.get((int) (Math.random() * neighbors.size()));
        setPosition(destination);
    }

    public float getVivacity() {
        return vivacity;
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
