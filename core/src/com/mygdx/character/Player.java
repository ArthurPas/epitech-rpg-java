package com.mygdx.character;

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
        super("Player", basicStat(), new Weapon("Basic sword", 1, Rarity.COMMON, 100, 10, 4, 0,"item/weapon/sword8.png"),position);
        this.xpLevel = xpLevel;
        this.inventory = new ArrayList<>();
        this.money = 0;
        setPathToAsset("character/heroFront.png");
    }

    public static Map<Stat,Integer> basicStat(){
        Map<Stat,Integer> stats = new HashMap<>();
        stats.put(Stat.STRENGTH,1);
        stats.put(Stat.AGILITY,20);
        stats.put(Stat.HP,100);
        return stats;
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
    public void move(Room room, Tile actualPosition, int xMouse, int yMouse) {
        Tile tileClicked = room.getSpecificTile(xMouse, yMouse);
        if (actualPosition.isNeighbor(room, tileClicked) && tileClicked.getTileDisplay().isWalkable() && !isInFight() && !isDead()) {
            setPosition(tileClicked);
        }

        if (room.getSpecificTile(xMouse,yMouse).getX() > actualPosition.getX()) {
            System.out.println("right position");
            setPathToAsset("character/heroRight.png");
        }  else if (room.getSpecificTile(xMouse,yMouse).getX() < actualPosition.getX()) {
            System.out.println("left position");
            setPathToAsset("character/heroLeft.png");
        } else if(room.getSpecificTile(xMouse,yMouse).getY() > actualPosition.getY()){
            System.out.println("top position");
            setPathToAsset("character/heroBack.png");
        } else if(room.getSpecificTile(xMouse,yMouse).getY() < actualPosition.getY() ){
            System.out.println("bottom position");
            setPathToAsset("character/heroFront.png");
        }

    }

    public void addMoney(int money){
        setMoney(getMoney() + money);
    };
    public void pay(int money){
        setMoney(getMoney() - money);
    }
    public void removeItem(Item item){
        inventory.remove(item);
    }
    public void addItem(Item item){
        inventory.add(item);
    }

}
