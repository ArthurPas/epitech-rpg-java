package com.mygdx.item;

import com.mygdx.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Chest {
    private List<Item> items = new ArrayList<>();
    private int moneyAvailable;
    private boolean canOpen = false;

    public Chest(int roomNumber, int moneyAvailable) {
        setWeaponToSell(roomNumber);
        this.moneyAvailable = moneyAvailable;
    }

    @Override
    public String toString() {
        return "Chest{" +
                "items=" + items +
                '}';
    }

    public List<Item> getItems() {
        return items;
    }
    public boolean buy(int price){
        int newMoney = getMoneyAvailable()-price;
        if (newMoney>=0){
            setMoneyAvailable(newMoney);
            return true;
        }
        return false;

    }

    public void setWeaponToSell(int roomNumber) {
        for (int i = 0; i < 4; i++) {
            //TODO real implementation of the weapon stats
            Weapon weapon = new Weapon("Name", 0, calculateRarity(roomNumber), 10, 10, 10, 0.1f * roomNumber, Weapon.getRandomWeaponPath());
            ;
            weapon.setCost(calculateCostByRarity(weapon.getRarity()));
            this.items.add(weapon);
        }
    }
    public void addItem(Item item){
        this.items.add(item);
    }

    public int calculateCostByRarity(Rarity rarity) {
        int cost = 0;
        switch (rarity) {
            case COMMON:
                cost = (int) (Math.random() * 10)+3;
                break;
            case RARE:
                cost = (int) (Math.random() * 50)+10;
                break;
            case LEGENDARY:
                cost = (int) (Math.random() * 100)+50;
                break;
        }
        return cost;
    }

    public Rarity calculateRarity(int roomNumber) {
        int common = 10;
        int rare = 5;
        int legendary = 2;
        int random = (int) (Math.random() * (roomNumber * 10));
        if (random < common) {
            return Rarity.COMMON;
        } else if (random < common + rare) {
            return Rarity.RARE;
        } else if (random < common + rare + legendary) {
            return Rarity.LEGENDARY;
        } else {
            return Rarity.COMMON;
        }
    }

    public int getMoneyAvailable() {
        return moneyAvailable;
    }

    public void setMoneyAvailable(int moneyAvailable) {
        this.moneyAvailable = moneyAvailable;
    }
    public void removeItem(Item item){
        this.items.remove(item);
    }

    protected Item sell(int gold) {
        return null;
    }

    protected int Buy(Item item) {
        return 0;
    }
}
