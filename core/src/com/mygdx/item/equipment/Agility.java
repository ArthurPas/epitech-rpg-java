package com.mygdx.item.equipment;

import com.mygdx.character.Stat;

import java.util.HashMap;
import java.util.Map;

public class Agility extends Equipment {
    public Agility(String name, int nbAgility, boolean isCursed) {
        super(isCursed ? "cursed " + name + " of agility" : "blessed " + name + " of agility", "item/equipment/oldPaper.png");
        Map<Stat, Integer> bonusCapacity = new HashMap<>();
        bonusCapacity.put(Stat.AGILITY, isCursed ? nbAgility : -nbAgility);
        super.setBonusCapacity(bonusCapacity);
    }
}
