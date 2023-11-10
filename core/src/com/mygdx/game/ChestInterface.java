package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.character.Player;
import com.mygdx.item.Item;
import com.mygdx.item.Weapon;

import java.util.ArrayList;
import java.util.List;

public class ChestInterface {
    OrthographicCamera camera;
    SpriteBatch batch;
    Sprite chestSprite;

    Sprite buyButton;

    Sprite sellButton;

    int nbSpriteChestX;
    int nbSpriteChestY;
    int pageWidth;
    int pageHeight;
    List<Weapon> weaponToBuy;
    Player player;
    int weaponNb;

    public ChestInterface(List<Weapon> weaponToBuy, Player player, SpriteBatch batch, int weaponNb ){
        this.weaponToBuy = weaponToBuy;
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
    }
    public List<Sprite> displayChestInterface() {
        List<Sprite>displayItems = new ArrayList<>();
        batch.setProjectionMatrix(camera.combined);

        float spriteWidthChest = pageWidth/ 2f / nbSpriteChestX;
        float spriteHeightChest = pageHeight / 1.5f / nbSpriteChestY;

        float startX = (pageWidth - spriteWidthChest * nbSpriteChestX) / 2f;
        float startY = (pageHeight - spriteHeightChest * nbSpriteChestY) / 2f;


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
        for (Item item : weaponToBuy){
            Texture texture = new Texture(item.getPathToAsset());
            Sprite itemSprite = new Sprite(texture);
            itemSprite.setPosition(startX + (index%(nbSpriteChestX / 2))* spriteWidthChest + spriteWidthChest / 4, startY + index+2 * spriteHeightChest + spriteHeightChest / 4);
            itemSprite.setSize(spriteWidthChest / 1.5f, spriteHeightChest / 1.5f);
            displayItems.add(itemSprite);
            index++;
        }
        index = 0;
        for (Item item : player.getInventory()){
            Texture texture = new Texture(item.getPathToAsset());
            Sprite itemSprite = new Sprite(texture);
            itemSprite.setPosition(startX + ((index%nbSpriteChestX)+2)* spriteWidthChest + spriteWidthChest / 4, startY + index+2 * spriteHeightChest + spriteHeightChest / 4);
            itemSprite.setSize(spriteWidthChest / 1.5f, spriteHeightChest / 1.5f);
            displayItems.add(itemSprite);
            index++;
        }
//        for (int i = 0;i<displayItems.size();i++) {
//            //Weapon sell by the player
//            Sprite itemSprite = displayItems.get(i);
//            if(i<2){
//                itemSprite.draw(batch);
//
//                System.out.println("firsts "+itemSprite);
//            }else{
//                itemSprite.setPosition(startX + ((i%nbSpriteChestX)+2) * spriteWidthChest + spriteWidthChest / 4, startY + 1 * spriteHeightChest + spriteHeightChest / 4);
//                itemSprite.setSize(spriteWidthChest / 1.5f, spriteHeightChest / 1.5f);
//                itemSprite.draw(batch);
//            }
//            System.out.println(displayItems.get(i));
//        }
        buyButton.setSize(spriteWidthChest ,  spriteHeightChest /3f );
        buyButton.setPosition( (startX + spriteWidthChest )  - spriteWidthChest / 2  , startY + buyButton.getHeight() );
        displayItems.add(buyButton);
        buyButton.draw(batch);
        Sprite test = new Sprite(new Texture("nem.jpg"));

        test.setSize(spriteWidthChest ,  spriteHeightChest /3f );
        test.setPosition((startX + spriteWidthChest )  - spriteWidthChest / 2  , startY + buyButton.getHeight() );
//        test.draw(batch);
        sellButton.setSize(  spriteWidthChest ,  spriteHeightChest /3f );
        sellButton.setPosition((startX +  3 * spriteWidthChest )  - spriteWidthChest / 2  , startY + buyButton.getHeight() );
        sellButton.setPosition((startX + spriteWidthChest )  - spriteWidthChest / 2 +300, startY + buyButton.getHeight() );
        displayItems.add(sellButton);
//        ShapeRenderer shapeRenderer = new ShapeRenderer();
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(1, 1, 1, 1);
//        shapeRenderer.line( startX + 2* spriteWidthChest, startY + buyButton.getHeight() * 2, startX + 2 * spriteWidthChest, startY + nbSpriteChestY * spriteHeightChest);
//        shapeRenderer.end();
        System.out.println(displayItems.size());
        return displayItems;
    }
}