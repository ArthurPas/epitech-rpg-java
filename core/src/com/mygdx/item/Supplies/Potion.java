package com.mygdx.item.Supplies;

import com.mygdx.character.Stat;
import com.mygdx.game.room.Tile;

public abstract class Potion {
    String pathToAsset;
    int number;

    Stat stat;

    Tile position;

    public Potion(String pathToAsset, int number, Stat stat) {
        this.pathToAsset = pathToAsset;
        this.number = number;
        this.stat = stat;
    }

    public String getPathToAsset() {
        return pathToAsset;
    }

    public int getNumber() {
        return number;
    }

    public Stat getStat() {
        return stat;
    }

    public Tile getPosition() {
        return position;
    }

    public void setPosition(Tile position) {
        this.position = position;
    }
}
