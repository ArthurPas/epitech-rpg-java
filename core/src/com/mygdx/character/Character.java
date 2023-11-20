package com.mygdx.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.room.Room;
import com.mygdx.game.Tile;
import com.mygdx.item.Weapon;

import java.util.Map;

public class Character  extends InputAdapter {
    private String name;
    private Map<Stat, Integer> stat;

    private int x;
    private int y;

    private String pathToAsset;

    public void setStat(Map<Stat, Integer> stat) {
        this.stat = stat;
    }

    public void setInFight(boolean inFight) {
        isInFight = inFight;
    }

    private Weapon weaponEquiped;

    private Tile position;

    public boolean isInFight() {
        return isInFight;
    }

    public void canFight(boolean inFight) {
        isInFight = inFight;
    }

    public boolean isDead(){

        return this.getStat().get(Stat.HP) <= 0;
    }

    private boolean isInFight;

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Tile getPosition() {
        return position;
    }

    public void setPosition(Tile position) {
        this.position = position;
        this.x = position.getX();
        this.y = position.getY();
    }
    public void setPosition(int x, int y, Room room){
        this.x = x;
        this.y = y;
        this.position = room.getSpecificTile(x,y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Character(String name, Map<Stat, Integer> stat, Weapon weaponEquiped, Tile position) {
        this.name = name;
        this.stat = stat;
        this.weaponEquiped = weaponEquiped;
        this.position = position;

    }

    public String getPathToAsset() {
        return pathToAsset;
    }

    public void setPathToAsset(String pathToAsset) {
        this.pathToAsset = pathToAsset;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Stat, Integer> getStat() {
        return stat;
    }

    public void setStat(Stat stat, int value) {
        this.stat.put(stat, value);
    }

    public Weapon getWeaponEquiped() {
        return weaponEquiped;
    }

    public void setWeaponEquiped(Weapon weaponEquiped) {
        this.weaponEquiped = weaponEquiped;
    }

    protected void speak(String string, Character character) {
    }

    public void changeStat(Stat stat, int value) {
        this.stat.put(stat, this.stat.get(stat) + value);
    }


    public int calculateLifeDividedBy4() {
        if(this.getStat().get(Stat.HP) <= 0){
            return 0;
        }else if (this.getStat().get(Stat.HP) <= 25) {
            return 1;
        } else if (this.getStat().get(Stat.HP) <= 50) {
            return 2;
        } else if (this.getStat().get(Stat.HP) <= 75) {
            return 3;
        } else {
            return 4;
        }
    }


    //TODO : add an miss by luck exception for pretty print
    public int attack(Character character) {
        int critMultiplicator = 1;
        if ((int) (Math.random() * 100) <= getStat().get(Stat.AGILITY)*weaponEquiped.getCriticalStrikeProb()) {


            System.out.println("Critical strike");
            critMultiplicator = 2;
        }
        if ((int) (Math.random() * 100) <= 100*weaponEquiped.getCriticalFailureProb()) {
            System.out.println("missed");
            return 0;
        } else {
            int dammage = (int) (getStat().get(Stat.STRENGTH) * weaponEquiped.getDamage() * critMultiplicator);

            System.out.println("dammage = " + dammage);

            System.out.println("old HP of "+ character.getName()+"= " + character.getStat().get(Stat.HP));

            character.changeStat(Stat.HP, -dammage);
            System.out.println("new HP of "+ character.getName()+"= " + character.getStat().get(Stat.HP));
            return dammage;
        }

    }
}
