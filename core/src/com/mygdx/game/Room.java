package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private int width;
    private int height;
    private final List<Tile> tiles;
    private int relativeWidth;
    private int relativeHeight;

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new ArrayList<>();
        this.relativeWidth = Gdx.graphics.getHeight() / width;
        this.relativeHeight = Gdx.graphics.getWidth() / height;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(i == 2 && j == 1) {
                    this.tiles.add(new Tile(relativeWidth * i, relativeHeight * j, false, "chest_1.png"));
                }
                this.tiles.add(new Tile(relativeWidth * i, relativeHeight * j, false, "ground.png"));
                if (i == 3 && j == height - 1) {
                    this.tiles.add(new Tile(relativeWidth * i, relativeHeight * j, true, "fence.png"));
                }
            }
        }
    }

    public List<Tile> getNeighbors(Tile originTile, int range) {
        List<Tile> neighbors = new ArrayList<>();
        for (Tile tile : tiles) {
            if (originTile.isNeighbor(this, tile)) {
                neighbors.add(tile);
            }
        }
        return neighbors;
    }

    public int getRelativeWidth() {
        return relativeWidth;
    }

    public void setRelativeWidth(int relativeWidth) {
        this.relativeWidth = relativeWidth;
    }

    public int getRelativeHeight() {
        return relativeHeight;
    }

    public void setRelativeHeight(int relativeHeight) {
        this.relativeHeight = relativeHeight;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Tile getSpecificTile(int x, int y){
        for (Tile tile : tiles) {
            if(tile.isInTile(this, x, y)){
                return tile;
            }
        }
        return null;
    }
    public Map<Tile, Sprite> createMap() {
        Map<Tile, Sprite> map = new HashMap<>();
        for (Tile tile : tiles) {
            Texture texture = new Texture(Gdx.files.internal(tile.getPathToAsset()));
            map.put(tile, new Sprite(texture));
        }
        System.out.println(map.size());
        return map;
    }

}
