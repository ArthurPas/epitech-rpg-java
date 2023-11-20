package com.mygdx.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.room.Room;
import com.mygdx.game.Tile;
import com.mygdx.item.Item;
import com.mygdx.item.Rarity;
import com.mygdx.item.Weapon;
import com.mygdx.item.equipment.Agility;
import com.mygdx.item.equipment.Attack;
import com.mygdx.item.equipment.Defense;
import com.mygdx.item.equipment.Equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends Character implements InputProcessor {
    boolean soundplayed = false;
    private int xpLevel;
    private List<Item> inventory;
    private int money;

    private boolean isInChest;

    public Player(int xpLevel, List<Item> inventory, int money, Tile position) {
        super("Player", basicStat(), new Weapon("Basic sword", 1, Rarity.COMMON, 100, 10, 4, 0, "item/weapon/sword8.png"), position);
        this.xpLevel = xpLevel;
        this.inventory = new ArrayList<>();
        this.money = money;
        this.isInChest = false;
        setPathToAsset("character/heroFront.png");
    }

    public static Map<Stat, Integer> basicStat() {
        Map<Stat, Integer> stats = new HashMap<>();
        stats.put(Stat.STRENGTH, 1);
        stats.put(Stat.AGILITY, 20);
        stats.put(Stat.HP, 100);
        stats.put(Stat.MAX_HP, stats.get(Stat.HP));
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

    public boolean isInChest() {
        return isInChest;
    }

    public void setInChest(boolean inChest) {
        isInChest = inChest;
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


    Sound swordAttackAudio = Gdx.audio.newSound(Gdx.files.internal("soundEffects/swordAttack.wav"));
    Sound footStepAudio = Gdx.audio.newSound(Gdx.files.internal("soundEffects/footstep.wav"));

    public void move(Room room, Tile actualPosition, int xMouse, int yMouse) {
        Tile tileClicked = room.getSpecificTile(xMouse, yMouse);
        System.out.println(tileClicked);
        System.out.println(tileClicked.getX() + " " + tileClicked.getY());

        if (actualPosition.isNeighbor(room, tileClicked) && tileClicked.getTileDisplay().isWalkable() && !isInFight() && !isDead()) {
            setPosition(tileClicked);
            footStepAudio.play(1.0f);
        }

        if (room.getSpecificTile(xMouse, yMouse).getX() > actualPosition.getX()) {
            System.out.println("right position");
            setPathToAsset("character/heroRight.png");
        } else if (room.getSpecificTile(xMouse, yMouse).getX() < actualPosition.getX()) {
            System.out.println("left position");
            setPathToAsset("character/heroLeft.png");
        } else if (room.getSpecificTile(xMouse, yMouse).getY() > actualPosition.getY()) {
            System.out.println("top position");
            setPathToAsset("character/heroBack.png");
        } else if (room.getSpecificTile(xMouse, yMouse).getY() < actualPosition.getY()) {
            System.out.println("bottom position");
            setPathToAsset("character/heroFront.png");
        }

    }

    public void move(Room room, int actualX, int actualY, int mooveX, int mooveY) {
        Tile tileClicked = room.getSpecificTile(mooveX + 10, mooveY + 10);

        if (tileClicked.getTileDisplay().isWalkable() && !isInFight() && !isDead()) {
            setPosition(mooveX, mooveY, room);
            System.out.println("cc" + this.getX());
            System.out.println("ccc" + this.getY());
        }
        if (mooveX > actualX) {
            System.out.println("right position");
            setPathToAsset("character/heroRight.png");
        } else if (mooveX < actualX) {
            System.out.println("left position");
            setPathToAsset("character/heroLeft.png");
        } else if (mooveY > actualY) {
            System.out.println("top position");
            setPathToAsset("character/heroBack.png");
        } else if (mooveY < actualY) {
            System.out.println("bottom position");
            setPathToAsset("character/heroFront.png");
        }
    }

    public void equip(Item item) {
        if (item instanceof Weapon) {
            inventory.add(item);
        } else {
            if (item instanceof Attack) {
                setStat(Stat.STRENGTH, getStat().get(Stat.STRENGTH) + ((Attack) item).getBonusCapacity().get(Stat.STRENGTH));
                System.out.println("Hop a moi le " + item.getName());
            } else if (item instanceof Defense) {
                setStat(Stat.MAX_HP, getStat().get(Stat.MAX_HP) + ((Defense) item).getBonusCapacity().get(Stat.MAX_HP));
                System.out.println("Hop a moi le " + item.getName());
            } else if (item instanceof Agility) {
                setStat(Stat.AGILITY, getStat().get(Stat.AGILITY) + ((Agility) item).getBonusCapacity().get(Stat.AGILITY));
                System.out.println("Hop a moi le " + item.getName());
            }
        }
    }


    @Override
    public int attack(Character character) {
        swordAttackAudio.play(1.0f);
        return super.attack(character);

    }

    public void addMoney(int money) {
        setMoney(getMoney() + money);
    }

    ;

    public boolean pay(int money) {
        System.out.println(money + " p " + getMoney());
        if (getMoney() >= money) {
            setMoney(getMoney() - money);
            return true;
        } else {
            //TODO: exception
            System.out.println("you don't have enough money");
            return false;
        }
    }


    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public void addItem(Item item) {
        inventory.add(item);
    }
}