package com.mygdx.item;

import com.mygdx.item.Item;

public class Weapon  extends Item {
    private int damage;
    private float criticalStrikeProb;

    @Override
    public String toString() {
        return "Weapon{" +
                "damage=" + damage +
                ", criticalStrikeProb=" + criticalStrikeProb +
                ", criticalFailureProb=" + criticalFailureProb +
                '}';
    }

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

}
