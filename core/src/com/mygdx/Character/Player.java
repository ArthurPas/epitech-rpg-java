package com.mygdx.Character;

import com.mygdx.game.Room;
import com.mygdx.game.Tile;
import com.mygdx.item.Item;
import com.mygdx.item.Weapon;

import java.util.List;

public class Player extends Character {
    private int xpLevel;
    private List<Item> inventory;

    private int money;


    public Player(int xpLevel, List<Item> inventory, int money, Tile position) {
        //TODO : set weapon and stats
        super("Player", null, null, position);
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

    protected boolean changeWeapon(Weapon weapon) {
        return false;
    }

    @Override
    public void move(Room room, Tile actualPosition, int xMouse, int yMouse) {
        Tile tileClicked = room.getSpecificTile(xMouse, yMouse);
        if(actualPosition.isNeighbor(room, tileClicked)){
            setPosition(tileClicked);
        }
    }
}
