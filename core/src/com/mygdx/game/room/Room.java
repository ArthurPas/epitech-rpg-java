package com.mygdx.game.room;

import com.badlogic.gdx.Gdx;
import com.mygdx.character.Monster;
import com.mygdx.game.Tile;
import com.mygdx.game.TileDisplay;
import com.mygdx.utils.PathFinding;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int width;
    private int height;
    private List<Tile> tiles;
    private int relativeWidth;
    private int relativeHeight;

    private Monster monster;

    private Tile chestTile;


    public Tile getExitTile() {
        return tiles.get(tiles.size() - height - 2);
    }

    public Room(int width, int height, Monster monster) {
        this.width = width;
        this.height = height;
        this.monster = monster;
        this.relativeWidth = Gdx.graphics.getHeight() / width;
        this.relativeHeight = Gdx.graphics.getWidth() / height;
        this.tiles = createMap(1, width, height);
        this.chestTile = tiles.get((int) (Math.random() * tiles.size()));
    }

    public Monster getMonster() {
        return monster;
    }

    public List<Tile> getFightsTiles() {
        List<Tile> fightTiles = new ArrayList<>(2);

        int indexMidle = ((width * height) / 2) - height / 2;
        if (width % 2 == 0) {
            fightTiles.add(tiles.get(indexMidle + 1));
            fightTiles.add(tiles.get(indexMidle));
            fightTiles.add(tiles.get(indexMidle - 1));
        } else {
            //Good luck for explain this : 23:30
            indexMidle = (((width * height) / 2) + (height % ((height / 2) + 1)) - (height / 2));
            fightTiles.add(tiles.get(indexMidle + 1));
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

    public Tile getSpecificTile(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.isInTile(this, x, y)) {
                return tile;
            }
        }
        return null;
    }
    public Tile getEntry(){
        return tiles.get(height+1);
    }

    public List<Tile> createMap(int roomLevel, int width, int height) {
        List<Tile> tiles = new ArrayList<>();
        TileDisplay border = new TileDisplay(true, false, TextureType.INSIDE);
        TileDisplay wall = new TileDisplay(false, false, TextureType.INSIDE);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((i == 0) || (j % height == 0) || (j % width == width-1 )|| (i % height == height-1)) {
                    tiles.add(new Tile(relativeWidth * i, relativeHeight * j, false, border));
                } else {
                    tiles.add(new Tile(relativeWidth * i, relativeHeight * j, false, wall));
                }
            }
        }
        return tiles;
//        Map<Tile, Sprite> map = new HashMap<>();
//        for (Tile tile : tiles) {
//            Texture texture = new Texture(Gdx.files.internal(tile.getPathToAsset()));
//            map.put(tile, new Sprite(texture));
//        }
//        System.out.println(map.size());
    }

    public void displayRandomPath(Tile begin, Tile end, int roomLevel ) {
//        TODO : room level management change texture*
//        TileDisplay ground = new TileDisplay(false, true, TextureType.CHILL_OUTSIDE);
//        List<Tile> path = new ArrayList<>();
//        Tile current = begin;
//        path.add(current);
//        while (current != end) {
//            List<Tile> neighbors = getNeighbors(current, 1);
//            current = neighbors.get((int) (Math.random() * neighbors.size()));
////            current = neighbors.get(1);
//            if(!path.contains(current) && (!current.getTileDisplay().isBorder())){
//                path.add(current);
//                current.setTileDisplay(ground);
//            }
//        }
        List<Tile> path = PathFinding.findAPath(this, begin, end);
        for (Tile tile : path) {
            tile.setTileDisplay(new TileDisplay(false, true, TextureType.INSIDE));

        }
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
