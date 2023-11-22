package com.mygdx.game.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.character.Monster;
import com.mygdx.character.Stat;
import com.mygdx.interfaces.TextureType;
import com.mygdx.interfaces.TileDisplay;
import com.mygdx.item.Supplies.BigPotion;
import com.mygdx.item.Supplies.LittlePotion;
import com.mygdx.item.Supplies.Potion;
import com.mygdx.utils.PathFinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Room {
    private int width;
    private int height;
    private List<Tile> tiles;
    private int relativeWidth;
    private int relativeHeight;
    private boolean isDoorOpen;
    private int roomNumber;
    private Monster monster;

    private Tile chestTile;
    Potion potion;


    public Tile getExitTile() {
        return tiles.get(tiles.size() - height - 2);
    }

    public Room(int width, int height, Monster monster, int roomNumber) {
        this.width = width;
        this.height = height;
        this.monster = monster;
        this.roomNumber = roomNumber;
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
        List<Tile> neighbors = new CopyOnWriteArrayList<>();
        for (Tile tile : tiles) {
            if (originTile.isNeighbor(this, tile)) {
                neighbors.add(tile);
            }
        }
        return neighbors;
    }

    public List<Tile> getNeighborsWalkable(Tile originTile, int range) {
        List<Tile> neighbors = getNeighbors(originTile, range);
        for (Tile tile : neighbors) {
            if (!tile.getTileDisplay().isWalkable())
                neighbors.remove(tile);
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

    public Tile getEntry() {
        return tiles.get(height + 1);
    }

    //TODO implement the room level management and display the right texture
    public List<Tile> createMap(int roomLevel, int width, int height) {
        List<Tile> tiles = new ArrayList<>();
        TileDisplay border = new TileDisplay(true, false, TextureType.CHILL_OUTSIDE);
        TileDisplay wall = new TileDisplay(false, false, TextureType.CHILL_OUTSIDE);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((i == 0) || (j % height == 0) || (j % width == width - 1) || (i % height == height - 1)) {
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

    public void displayRandomPath(Tile begin, Tile end, int roomLevel) {
//        TODO : room level management change texture*
        List<Tile> path = PathFinding.findAPath(this, begin, end);
        for (Tile tile : path) {
            tile.setTileDisplay(new TileDisplay(false, true, TextureType.CHILL_OUTSIDE));
        }
        getExitTile().getTileDisplay().setTexturePath("allTextures/closedDoor.png");
    }

    public boolean isDoorOpen() {
        return isDoorOpen;
    }

    public void setDoorOpen(Tile fightTile) {
        if (!isDoorOpen) {
            isDoorOpen = true;
            setChestTile(getRandomTile());
            setPotionTile(getChestTileAfterFight(fightTile));
            getExitTile().getTileDisplay().setTexturePath("allTextures/openDoor.png");
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

    public Tile getChestTileAfterFight(Tile fightTile) {
        List<Tile> tiles = getNeighbors(fightTile, 1);
        Collections.shuffle(tiles);
        for (Tile tile : tiles) {
            if (tile.getTileDisplay().isWalkable()) {
                return tile;
            }
        }
        return fightTile;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Potion generateAPotion() {
        int n = (int) (Math.random() * 2) + 1;
        Potion pot = new LittlePotion(Stat.HP);
        switch (n) {
            case 1:
                pot = new LittlePotion(Stat.HP);
                break;
            case 2:
                pot = new BigPotion(Stat.HP);
        }
        setPotion(pot);
        return pot;
    }

    public void setPotionTile(Tile tile) {
        potion.setPosition(tile);
    }

    public Tile findTilePotion(Tile entry) {
        for (Tile tile : getNeighbors(entry, 100)) {
            System.out.println(tile);
            if (tile.getTileDisplay().isWalkable() && !tile.getTileDisplay().isBorder()) {

                return tile;
            }
        }
        return getEntry();
    }

    public Potion getPotion() {
        return potion;
    }

    public void setPotion(Potion potion) {
        this.potion = potion;
    }
}
