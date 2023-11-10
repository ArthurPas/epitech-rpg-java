package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.Character.Monster;

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

    private Monster monster;

    private Tile chestTile;

    private Tile exitTile;


    public Tile getExitTile() {
        return exitTile;
    }

    public void setExitTile(Tile exitTile) {
        this.exitTile = exitTile;
    }

    public Room(int width, int height, Monster monster) {
        this.width = width;
        this.height = height;
        this.monster = monster;
        this.tiles = new ArrayList<>();
        this.relativeWidth = Gdx.graphics.getHeight() / width;
        this.relativeHeight = Gdx.graphics.getWidth() / height;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.tiles.add(new Tile(relativeWidth * i, relativeHeight * j, false, "ground.png"));
            }
        }
        this.chestTile = tiles.get((int) (Math.random() * tiles.size()));
        this.exitTile = tiles.get(tiles.size() - 1);
    }

    public Monster getMonster() {
        return monster;
    }

    public List<Tile> getFightsTiles(){
        List<Tile> fightTiles = new ArrayList<>(2);

        int indexMidle = ((width*height)/2)-height/2;
        if(width%2 == 0) {
            fightTiles.add(tiles.get(indexMidle + 1));
            fightTiles.add(tiles.get(indexMidle));
            fightTiles.add(tiles.get(indexMidle - 1));
        }else{
            //Good luck for explain this : 23:30
            indexMidle = (((width*height)/2)+(height%((height/2)+1))-(height/2));
            System.out.println(indexMidle);
            fightTiles.add(tiles.get(indexMidle+ 1));
            fightTiles.add(tiles.get(indexMidle));
            fightTiles.add(tiles.get(indexMidle - 1));
        }
        return fightTiles;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public Tile getRandomTile() {
        return tiles.get((int) (Math.random() * tiles.size()));
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

    public List<Tile> getTiles() {
        return tiles;
    }

    public Tile getChestTile() {
        return chestTile;
    }

    public void setChestTile(Tile chestTile) {
        this.chestTile = chestTile;
    }
}
