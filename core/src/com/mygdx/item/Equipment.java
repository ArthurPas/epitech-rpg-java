package com.mygdx.item;

import com.mygdx.character.Stat;

import java.util.Map;

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
}