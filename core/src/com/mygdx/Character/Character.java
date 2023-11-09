package com.mygdx.Character;

import com.mygdx.item.Weapon;

import java.util.Map;
public class Character {
    private String name;
    private Map<Stat, Integer> stat;

    private Weapon weaponEquiped;

    public Character(String name, Map<Stat, Integer> stat, Weapon weaponEquiped) {
        this.name = name;
        this.stat = stat;
        this.weaponEquiped = weaponEquiped;
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
    // :TODO replace paramaters with tile
    protected boolean move (int x, int y){
        return false;
    }
}
