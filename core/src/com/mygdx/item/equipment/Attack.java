package com.mygdx.item.equipment;

import com.mygdx.character.Stat;

import java.util.HashMap;
import java.util.Map;

public class Attack extends Equipment {
    Map<Stat, Integer> bonusCapacity = new HashMap<>();

    public Attack(String name, int nbAgility, boolean isCursed) {
        super(isCursed ? "cursed " + name + " for strength " : "blessed " + name + " for strength", "item/equipment/ringOfPower.png");
        bonusCapacity.put(Stat.STRENGTH, isCursed ? -nbAgility : nbAgility);
        super.setBonusCapacity(bonusCapacity);
    }
}
