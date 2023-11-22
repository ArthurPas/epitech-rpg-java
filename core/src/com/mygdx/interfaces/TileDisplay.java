package com.mygdx.interfaces;

public class TileDisplay {

    public final static String ASSET_GROUND = "allTextures/tile624.png";
    public final static String ASSET_GRASS = "allTextures/tile736.png";
    public final static String ASSET_LAVA = "allTextures/tile299.png";
    public final static String ASSET_LAVA_BLOCK = "allTextures/tile288.png";
    public final static String ASSET_WATER = "allTextures/tile261.png";
    public final static String ASSET_FIRE = "allTextures/tile302.png";
    public final static String ASSET_WOOD_LOG = "allTextures/tile398.png";

    public final static String ASSET_WALL = "allTextures/tile137.png";
    public final static String ASSET_INSIDE_GROUND = "allTextures/tile167.png";
    public final static String ASSET_PORTAL= "allTextures/tile332.png";
    public final static String ASSET_DUNGEON_FLOOR= "allTextures/tile576.png";
    private boolean isBorder;
    private boolean isWalkable;
    private String texturePath;
    private TextureType textureType;

    public TileDisplay(boolean isBorder, boolean isWalkable, TextureType textureType) {
        this.isBorder = isBorder;
        this.isWalkable = isWalkable;
        this.textureType = textureType;
        setTextureByType(isBorder, isWalkable, textureType);
    }

    //TODO Pas beau ca
    public void setTextureByType(boolean isBorder, boolean isWalkable, TextureType textureType){
        if(isWalkable){
            switch (textureType){
                case LAVA_DUNGEON:
                    this.texturePath = ASSET_DUNGEON_FLOOR;
                    break;
                case CHILL_OUTSIDE:
                    this.texturePath = ASSET_GROUND;
                    break;
                case INSIDE:
                    this.texturePath = ASSET_INSIDE_GROUND;
                    break;
            }
        }else {
            switch (textureType){
                case LAVA_DUNGEON:
                    this.texturePath = ASSET_LAVA_BLOCK;
                    break;
                case CHILL_OUTSIDE:
                    this.texturePath = ASSET_GRASS;
                    break;
                case INSIDE:
                    this.texturePath = ASSET_WALL;
                    break;
            }
        }
        if(isBorder) {
            switch (textureType) {
                case LAVA_DUNGEON:
                    this.texturePath = ASSET_LAVA;
                    break;
                case CHILL_OUTSIDE:
                    this.texturePath = ASSET_WATER;
                    break;
                case INSIDE:
                    this.texturePath = ASSET_WALL;
                    break;
            }
        }
    }

    public boolean isBorder() {
        return isBorder;
    }

    public void setBorder(boolean border) {
        isBorder = border;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

    public String getTexturePath() {
        return texturePath;
    }

    @Override
    public String toString() {
        return "TileDisplay{" +
                "isBorder=" + isBorder +
                ", isWalkable=" + isWalkable +
                ", texturePath='" + texturePath + '\'' +
                ", textureType=" + textureType +
                '}';
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }

    public TextureType getTextureType() {
        return textureType;
    }

    public void setTextureType(TextureType textureType) {
        this.textureType = textureType;
    }
}
