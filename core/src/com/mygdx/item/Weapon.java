package com.mygdx.item;

import com.mygdx.item.Item;

import java.io.File;
import java.util.Objects;

public class Weapon  extends Item {
    private int damage;
    private float criticalStrikeProb;



    private float criticalFailureProb;

    public Weapon(String name, int cost, Rarity rare, int durability, int damage, float criticalStrikeProb, float criticalFailureProb, String pathToAsset) {
        super(name, cost, rare, durability, pathToAsset);
        this.damage = damage;
        this.criticalStrikeProb = criticalStrikeProb;
        this.criticalFailureProb = criticalFailureProb;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getCriticalStrikeProb() {
        return criticalStrikeProb;
    }

    public void setCriticalStrikeProb(float criticalStrikeProb) {
        this.criticalStrikeProb = criticalStrikeProb;
    }

    public float getCriticalFailureProb() {
        return criticalFailureProb;
    }

    public void setCriticalFailureProb(float criticalFailureProb) {
        this.criticalFailureProb = criticalFailureProb;
    }

    protected int attack(Character character) {
        return 0;
    }

    static public String getRandomWeaponPath() {
        File dir = new File("item/weapon");
        File[] monsterAssets = dir.listFiles();
        assert monsterAssets != null;
        return monsterAssets[(int) (Math.random() *(monsterAssets.length-1))].getPath();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Weapon weapon = (Weapon) o;
        return damage == weapon.damage && Float.compare(weapon.criticalStrikeProb, criticalStrikeProb) == 0 && Float.compare(weapon.criticalFailureProb, criticalFailureProb) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), damage, criticalStrikeProb, criticalFailureProb);
    }

}
