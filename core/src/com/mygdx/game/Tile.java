package com.mygdx.game;

public class Tile {
    private static int id = 0;
    private int x;
    private int y;
    private boolean isDoor;
    private String pathToAsset;

    public Tile(int x, int y, boolean isDoor, String pathToAsset) {
        Tile.id = id+1;
        this.x = x;
        this.y = y;
        this.isDoor = isDoor;
        this.pathToAsset = pathToAsset;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        Tile.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isDoor() {
        return isDoor;
    }

    public void setDoor(boolean door) {
        isDoor = door;
    }

    public String getPathToAsset() {
        return pathToAsset;
    }

    public void setPathToAsset(String pathToAsset) {
        this.pathToAsset = pathToAsset;
    }
}
