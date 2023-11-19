package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.character.Player;
import com.mygdx.item.Chest;
import com.mygdx.item.Item;
import com.mygdx.item.Weapon;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChestInterface {
    OrthographicCamera camera;


    Item itemSelected;
    Sprite spriteSelected;

    SpriteBatch batch;
    Sprite chestSprite;

    Sprite buyButton;

    Sprite sellButton;

    int nbSpriteChestX;
    int nbSpriteChestY;
    int pageWidth;
    int pageHeight;
    Player player;
    int weaponNb;
    Chest chest;
    List<Sprite> spritesBuy;
    List<Sprite> spritesSell;
    Map<Sprite, Item> linkItemsSprite;
    float spriteWidthChest;
    float spriteHeightChest;

    float startX;
    float startY;
    final int MARGIN_COIN = 20;

    public ChestInterface(Player player, SpriteBatch batch, int weaponNb, Chest chest) {
        this.chest = chest;
        this.player = player;
        this.batch = batch;
        this.buyButton = new Sprite(new Texture("buy.png"));
        this.sellButton = new Sprite(new Texture("sell.png"));
        this.pageWidth = Gdx.graphics.getWidth();
        this.pageHeight = Gdx.graphics.getHeight();
        this.nbSpriteChestX = 4;
        this.nbSpriteChestY = 3;
        this.weaponNb = weaponNb;
        this.camera = new OrthographicCamera(pageWidth, pageHeight);
        this.camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 2);
        this.camera.update();

        spriteWidthChest = pageWidth / 2f / nbSpriteChestX;
        spriteHeightChest = pageHeight / 1.5f / nbSpriteChestY;
        startX = (pageWidth - spriteWidthChest * nbSpriteChestX) / 2f;
        startY = (pageHeight - spriteHeightChest * nbSpriteChestY) / 2f;

    }

    public List<Sprite> displayChestInterface() {
        linkItemsSprite = new HashMap<>();
        List<Sprite> displayItems = new ArrayList<>();
        spritesBuy = new CopyOnWriteArrayList<>();
        spritesSell = new CopyOnWriteArrayList<>();
        batch.setProjectionMatrix(camera.combined);


        for (int i = 0; i < nbSpriteChestX; i++) {
            for (int j = 0; j < nbSpriteChestY; j++) {
                float x = startX + i * spriteWidthChest;
                float y = startY + j * spriteHeightChest;
                chestSprite = new Sprite(new Texture("chestBackground.png"));
                chestSprite.setPosition(x, y);
                chestSprite.setSize(spriteWidthChest, spriteHeightChest);
                displayItems.add(chestSprite);
            }
        }
        int index = 0;
        //Add all the sprite of the weapon from the chest and set their coordinates to the left part of the chest
        for (Item item : chest.getItems()) {
            Texture texture = new Texture(item.getPathToAsset());
            Sprite itemSprite = new Sprite(texture);
            // X axis : startX + (index % (nbSpriteChestX / 2)) * spriteWidthChest + spriteWidthChest / 4
            //startX : bottom left corner
            //+ (index % (nbSpriteChestX / 2)) : col number of the chest
            //* spriteWidthChest : size of a tile in chest
            //+ spriteWidthChest / 4 : center

            //Y axis : (nbSpriteChestY * spriteHeightChest - ((index % nbSpriteChestX / 2) * spriteHeightChest) - spriteWidthChest / 4) + MARGIN_COIN);
            //nbSpriteChestY * spriteHeightChest : start from the top
            //- ((index % nbSpriteChestX / 2) : col number of the chest * spriteHeightChest : height)
            //- spriteWidthChest / 4 : center
            // MARGIN_COIN : for the price
            itemSprite.setPosition(startX + (index % (nbSpriteChestX / 2)) * spriteWidthChest + spriteWidthChest / 4, (nbSpriteChestY * spriteHeightChest - ((index % nbSpriteChestX / 2) * spriteHeightChest) - spriteWidthChest / 4) + MARGIN_COIN);
            itemSprite.setSize(spriteWidthChest / 1.5f, spriteHeightChest / 1.5f);
            displayItems.add(itemSprite);
            spritesSell.add(itemSprite);
            linkItemsSprite.put(itemSprite, item);
            index++;
        }
        //TODO: discuss with Pierre about a game rule : maybe the player could have 4 item maximum in his inventory
        index = 0;
        int inventorySize = player.getInventory().size();
        for (Item item : player.getInventory()) {
            Texture texture = new Texture(item.getPathToAsset());
            Sprite itemSprite = new Sprite(texture);
            //X axis : same but + 2 for the col
            //Y axis : same
            itemSprite.setPosition(startX + ((index % (nbSpriteChestX / 2) + 2) * spriteWidthChest + spriteWidthChest / 4), (nbSpriteChestY * spriteHeightChest - ((index % nbSpriteChestX / 2) * spriteHeightChest) - spriteWidthChest / 4) + MARGIN_COIN);
            itemSprite.setSize(spriteWidthChest / 1.5f, spriteHeightChest / 1.5f);
            displayItems.add(itemSprite);
            spritesBuy.add(itemSprite);
            linkItemsSprite.put(itemSprite, item);
            index++;
        }
        buyButton.setSize(spriteWidthChest, spriteHeightChest / 3f);
        buyButton.setPosition((startX + spriteWidthChest) - spriteWidthChest / 2, startY + buyButton.getHeight());
        displayItems.add(buyButton);
        sellButton.setSize(spriteWidthChest, spriteHeightChest / 3f);
        sellButton.setPosition((startX + 3 * spriteWidthChest) - spriteWidthChest / 2, startY + buyButton.getHeight());
        displayItems.add(sellButton);
        return displayItems;

    }

    public boolean isIn(int x, int y, int mouseX, int mouseY, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    ;

    public void setSpriteSelected(Sprite spriteSelected) {
        this.spriteSelected = spriteSelected;
    }

    public void setItemSelected(Item itemSelected) {
        this.itemSelected = itemSelected;
    }

    public boolean handleClick(int mouseX, int mouseY) {
        Vector3 worldCoordinates = camera.unproject(new Vector3(mouseX, mouseY, 0));
        int mouseXWorld = (int) worldCoordinates.x;
        int mouseYWorld = (int) worldCoordinates.y;


        // iterate on all sprite for buy weapon
        for (Sprite itemSprite : spritesBuy) {
            //look if the click was on one of them
            if (isIn((int) itemSprite.getX(), (int) itemSprite.getY(), mouseXWorld, mouseYWorld, (int) itemSprite.getWidth(), (int) itemSprite.getHeight())) {
                //and set itemSelected with the map<Sprite,Item>
                itemSelected = linkItemsSprite.get(itemSprite);
                //set also the sprite selected
                setSpriteSelected(itemSprite);
            }
        }
        //same as buy
        for (Sprite sellItemSprite : spritesSell) {
            if (isIn((int) sellItemSprite.getX(), (int) sellItemSprite.getY(), mouseXWorld, mouseYWorld, (int) sellItemSprite.getWidth(), (int) sellItemSprite.getHeight())) {
                System.out.println("you clicked on weapons to sell");
                itemSelected = linkItemsSprite.get(sellItemSprite);
                setSpriteSelected(sellItemSprite);
                return true;
            }
        }

        if (isIn((int) buyButton.getX(), (int) buyButton.getY(), mouseXWorld, mouseYWorld, (int) buyButton.getWidth(), (int) buyButton.getHeight())) {
            System.out.println("you clicked on buyButton");
            Item item = getItemSelected();
            if (item == null) {
                return false;
            }
            if (player.pay(item.getCost())) {
                System.out.println("inv bef" + this.player.getInventory());
                this.player.addItem(item);
                this.chest.removeItem(item);
                setSpriteSelected(null);
                setItemSelected(null);
                return true;
            }
            return false;


        } else if (

                isIn((int) sellButton.

                        getX(), (int) sellButton.getY(), mouseXWorld, mouseYWorld, (int) sellButton.getWidth(), (int) sellButton.getHeight())) {
            System.out.println("you clicked on sellButton");
            Item item = getItemSelected();
            if (item == null) {
                return false;
            }
            if (chest.buy(item.getCost())) {
                //TODO: discuss with Pierre about selling the item and not put it on the chest
//                this.chest.addItem(item);

                //TODO: see how many percent the price decrease when you sell
                player.addMoney(item.getCost());
                this.player.removeItem(item);
                setSpriteSelected(null);
                setItemSelected(null);
                return true;
        }
    }
        return false;
}

    public Item getItemSelected() {
        return itemSelected;
    }

    public Sprite getSpriteSelected() {
        return spriteSelected;

    }

    //method to send the coordinate of the sprite selected to the render so all the sprites draw are only in render function (maybe useless)
    public int[] coordItemSelected(Sprite selected) {
        if (selected != null) {
            ShapeRenderer shape = new ShapeRenderer();
            shape.setProjectionMatrix(camera.combined);
            int[] coord = new int[4];
            coord[0] = (int) selected.getX();
            coord[1] = (int) selected.getY();
            coord[2] = (int) selected.getWidth();
            coord[3] = (int) selected.getHeight();
            return coord;
        }
        return null;
    }


    //TODO: clean : display and get in the same function
    public List<BitmapFont> displayWeaponCost() {
        List<BitmapFont> fonts = new CopyOnWriteArrayList<>();
        for (Sprite sprite : linkItemsSprite.keySet()) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("moneyFont.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 25;

            Item item = linkItemsSprite.get(sprite);
            Sprite spriteCost = new Sprite(new Texture("coin.png"));
            spriteCost.setPosition(sprite.getX() - sprite.getWidth() / 4, (sprite.getY() - sprite.getHeight() / 4) - MARGIN_COIN);
            spriteCost.setSize(20, 20);
            spriteCost.draw(batch);
            BitmapFont font12 = generator.generateFont(parameter);
            font12.draw(batch, String.valueOf(item.getCost()), spriteCost.getX() + sprite.getWidth() / 2, spriteCost.getY() + font12.getLineHeight());
            fonts.add(font12);
            generator.dispose();
        }
        return fonts;
    }

    public void displayAllMoney() {
        displayBothMoney(true);
        displayBothMoney(false);
    }

    private void displayBothMoney(boolean isPlayer) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("moneyFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        BitmapFont font12 = generator.generateFont(parameter);

        int money = chest.getMoneyAvailable();
        Sprite spriteCost = new Sprite(new Texture("coin.png"));
        spriteCost.setPosition(startX + 10, startY);
        if (isPlayer) {
            spriteCost.setPosition(startX + (nbSpriteChestX / 2) * spriteWidthChest + 10, startY);
            money = player.getMoney();
        }
        spriteCost.setSize(40, 40);
        spriteCost.draw(batch);
        font12.draw(batch, String.valueOf(money), spriteCost.getX() + spriteWidthChest, startY + font12.getLineHeight());
        generator.dispose();
    }

    public List<Sprite> getItemSprites() {
        List<Sprite> sprites = new ArrayList<>();
        sprites.addAll(spritesBuy);
        sprites.addAll(spritesSell);
        return sprites;
    }

    public Map<Sprite, Item> getLinkItemsSprite() {
        return linkItemsSprite;
    }

    public void setLinkItemsSprite(Map<Sprite, Item> linkItemsSprite) {
        this.linkItemsSprite = linkItemsSprite;
    }

}