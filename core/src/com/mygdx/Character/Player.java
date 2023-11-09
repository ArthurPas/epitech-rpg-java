package com.mygdx.Character;

import com.mygdx.item.Item;
import com.mygdx.item.Weapon;

import java.util.List;

public class Player {
    private int xpLevel;
    private List<Item> inventory;

    private int money;

    public Player(int xpLevel, List<Item> inventory, int money) {
        this.xpLevel = xpLevel;
        this.inventory = inventory;
        this.money = money;
    }

    public int getXpLevel() {
        return xpLevel;
    }

    public void setXpLevel(int xpLevel) {
        this.xpLevel = xpLevel;
    }

    public List<Item> getInventory() {
        return inventory;
    }


    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
protected  boolean changeWeapon(Weapon weapon){
    return false;
}
}
