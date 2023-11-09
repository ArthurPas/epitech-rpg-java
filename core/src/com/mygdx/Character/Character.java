package com.mygdx.Character;

import com.mygdx.game.Room;
import com.mygdx.game.Tile;
import com.mygdx.item.Weapon;

import java.util.Map;
public class Character {
    private String name;
    private Map<Stat, Integer> stat;

    private Weapon weaponEquiped;

    public Tile position;

    public Tile getPosition() {
        return position;
    }

    public void setPosition(Tile position) {
        this.position = position;
    }

    public Character(String name, Map<Stat, Integer> stat, Weapon weaponEquiped, Tile position) {
        this.name = name;
        this.stat = stat;
        this.weaponEquiped = weaponEquiped;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Stat, Integer> getStat() {
        return stat;
    }

    public void setStat(Map<Stat, Integer> stat) {
        this.stat = stat;
    }

    public Weapon getWeaponEquiped() {
        return weaponEquiped;
    }

    public void setWeaponEquiped(Weapon weaponEquiped) {
        this.weaponEquiped = weaponEquiped;
    }

    protected void speak(String string, Character character) {
    }
    protected boolean attack(Weapon weapon){
        return false;
    }

    public void move(Room room, Tile position, int x, int y) {
        setPosition(position);
    }
}
