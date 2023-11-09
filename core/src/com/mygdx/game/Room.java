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
    private int RELATIVEWIDTH;
    private int RELATIVEHEIGHT;

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new ArrayList<>();
        this.RELATIVEWIDTH = Gdx.graphics.getHeight() / width;
        this.RELATIVEHEIGHT = Gdx.graphics.getWidth() / height;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(i == 2 && j == 1) {
                    this.tiles.add(new Tile(RELATIVEWIDTH * i, RELATIVEHEIGHT * j, false, "chest_1.png"));
                }
                this.tiles.add(new Tile(RELATIVEWIDTH * i, RELATIVEHEIGHT * j, false, "ground.png"));
                if (i == 3 && j == height - 1) {
                    this.tiles.add(new Tile(RELATIVEWIDTH * i, RELATIVEHEIGHT * j, true, "fence.png"));
                }
            }
        }
    }

    public Tile getTile(int id) {
        return tiles.get(id);
    }

    public int getRELATIVEWIDTH() {
        return RELATIVEWIDTH;
    }

    public void setRELATIVEWIDTH(int RELATIVEWIDTH) {
        this.RELATIVEWIDTH = RELATIVEWIDTH;
    }

    public int getRELATIVEHEIGHT() {
        return RELATIVEHEIGHT;
    }

    public void setRELATIVEHEIGHT(int RELATIVEHEIGHT) {
        this.RELATIVEHEIGHT = RELATIVEHEIGHT;
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
