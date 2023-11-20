package com.mygdx.item;

import java.util.Objects;
import java.util.Random;

public abstract class Item {
    private String name;
    private int cost;
    private Rarity rare;

    private int durability;

    private String pathToAsset;

    public Item(String name, int cost, Rarity rare, int durability, String pathToAsset){
        this.name = name;
        this.cost = cost;
        this.rare = rare;
        this.durability = durability;
        this.pathToAsset = pathToAsset;
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

    public int getCost() {
        return cost;
    }

    public Rarity getRare() {
        return rare;
    }
    public int getDurability() {
        return (int) (Math.random()*100);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setRare(Rarity rare) {
        this.rare = rare;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    protected void decreaseDurability(){
    };


    public Rarity getRarity() {
        return rare;
    }
     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return cost == item.cost && durability == item.durability && Objects.equals(name, item.name) && rare == item.rare && Objects.equals(pathToAsset, item.pathToAsset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost, rare, durability, pathToAsset);
    }
}
