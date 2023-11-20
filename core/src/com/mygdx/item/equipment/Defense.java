package com.mygdx.item.equipment;

import com.mygdx.character.Stat;

import java.util.HashMap;
import java.util.Map;

public class Defense extends Equipment {
    public Defense(String name, int nbAgility, boolean isCursed) {
        super(isCursed ? "cursed " + name + " for the weak " : "blessed " + name + " for the brave", "item/equipment/shield.png");
        Map<Stat, Integer> bonusCapacity = new HashMap<>();
        bonusCapacity.put(Stat.HP, isCursed ? nbAgility : -nbAgility);
        super.setBonusCapacity(bonusCapacity);
    }
}
