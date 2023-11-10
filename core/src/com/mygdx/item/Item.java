package com.mygdx.item;

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
        return durability;
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
}
