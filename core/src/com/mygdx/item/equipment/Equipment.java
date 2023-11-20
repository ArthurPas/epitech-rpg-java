package com.mygdx.item.equipment;

import com.mygdx.character.Stat;
import com.mygdx.item.Item;
import com.mygdx.item.Rarity;

import java.util.Map;
import java.util.Objects;

public abstract class Equipment extends Item {
    private Map<Stat, Integer> bonusCapacity;
    private String name;

    private String pathToAsset;

    public Equipment(String name, String pathToAsset) {
        super(name, 0, Rarity.COMMON, 1000, pathToAsset);
        this.name = name;
        this.pathToAsset = pathToAsset;
    }

    public Map<Stat, Integer> getBonusCapacity() {
        return bonusCapacity;
    }

    public void setBonusCapacity(Map<Stat, Integer> bonusCapacity) {
        this.bonusCapacity = bonusCapacity;
    }

    public static Equipment getRandomEquipment(int roomLevel) {
        int random = (int) (Math.random() * 3);
        int cursed = (int) (Math.random() * roomLevel * 1.5);
        //TODO find cool names for equipment
        switch (random) {
            case 0:
                return new Attack("ring", (int) (roomLevel * 1.5), cursed > roomLevel);
            case 1:
                return new Defense("shield", (int) (roomLevel * 1.5), cursed > roomLevel);
            case 2:
                return new Agility("oldPaper", (int) (roomLevel * 1.5), cursed > roomLevel);
            default:
                return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return Objects.equals(bonusCapacity, equipment.bonusCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bonusCapacity);
    }
}
