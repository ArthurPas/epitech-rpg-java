package com.mygdx.item;

import com.mygdx.item.Item;

import java.util.List;

public class Chest {
    private List<Item> items;
    private int moneyAvailable;
    private boolean canOpen = false;

    public Chest(List<Item> items, int moneyAvailable) {
        this.items = items;
        this.moneyAvailable = moneyAvailable;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getMoneyAvailable() {
        return moneyAvailable;
    }

    public void setMoneyAvailable(int moneyAvailable) {
        this.moneyAvailable = moneyAvailable;
    }
    protected Item sell (int gold){
       return null;
    }
   protected int Buy(Item item){
        return 0;
   }
}
