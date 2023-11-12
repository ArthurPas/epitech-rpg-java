package com.mygdx.character;

import com.mygdx.game.Game;
import com.mygdx.game.room.Room;
import com.mygdx.game.Tile;
import com.mygdx.item.Item;
import com.mygdx.item.Rarity;
import com.mygdx.item.Weapon;

import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
    private int xpLevel;
    private List<Item> inventory;
    private int money;

    public Player(int xpLevel, List<Item> inventory, int money, Tile position) {
        super("Player", Game.basicStat(100,2,10), new Weapon("Basic sword", 1, Rarity.COMMON, 100, 10, 4, 0,"item/weapon/sword8.png"),position);
        this.xpLevel = xpLevel;
        this.inventory = new ArrayList<>();
        this.money = 0;
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
        if (actualPosition.isNeighbor(room, tileClicked)) {
            setPosition(tileClicked);
        }
    }
}
