package com.mygdx.item;

import com.mygdx.character.Stat;

import java.util.Map;
import java.util.Objects;

public class Equipment {
    private Map<Stat, Integer> bonusCapacity;

    public Equipment(Map<Stat, Integer> bonusCapacity){
        this.bonusCapacity =bonusCapacity;

    }

    public Map<Stat, Integer> getBonusCapacity() {
        return bonusCapacity;
    }

    public void setBonusCapacity(Map<Stat, Integer> bonusCapacity) {
        this.bonusCapacity = bonusCapacity;
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
