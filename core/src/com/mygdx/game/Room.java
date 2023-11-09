package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
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

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.tiles.add(new Tile((Gdx.graphics.getHeight()/width)*i,j*(Gdx.graphics.getWidth()/height), false, "ground.png"));
                System.out.println("Tile added at " + (800/width)*i + ", " + j*(800/height));
            }
        }
    }

    public Tile getTile(int id) {
        return tiles.get(id);
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
