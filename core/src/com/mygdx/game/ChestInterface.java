package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.character.Player;
import com.mygdx.item.Item;
import com.mygdx.item.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChestInterface {
    OrthographicCamera camera;

    @Override
    public String toString() {
        return "ChestInterface{" +
                "weaponToBuy=" + weaponToBuy +
                ", weaponNb=" + weaponNb +
                '}';
    }

    SpriteBatch batch;
    Sprite chestSprite;

    Sprite buyButton;

    Sprite sellButton;

    int nbSpriteChestX;
    int nbSpriteChestY;
    int pageWidth;
    int pageHeight;
    List<Weapon> weaponToBuy;

    List<Weapon> weaponToSell;
    Player player;
    int weaponNb;
    List <Sprite> spritesBuy = new CopyOnWriteArrayList<>();
    List <Sprite> spritesSell= new CopyOnWriteArrayList<>();
    Map< Sprite,Item> linkItemsSprite  = new HashMap<>();

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
            spritesSell.add(itemSprite);
            linkItemsSprite.put(itemSprite, item);
            index++;
        }
        index = 0;
        for (Item item : player.getInventory()){
            Texture texture = new Texture(item.getPathToAsset());
            Sprite itemSprite = new Sprite(texture);
            itemSprite.setPosition(startX + ((index%nbSpriteChestX)+2)* spriteWidthChest + spriteWidthChest / 4, startY + index+2 * spriteHeightChest + spriteHeightChest / 4);
            itemSprite.setSize(spriteWidthChest / 1.5f, spriteHeightChest / 1.5f);
            displayItems.add(itemSprite);
            spritesBuy.add(itemSprite);
            linkItemsSprite.put(itemSprite, item);
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
        sellButton.setSize(  spriteWidthChest ,  spriteHeightChest /3f );
        sellButton.setPosition((startX +  3 * spriteWidthChest )  - spriteWidthChest / 2  , startY + buyButton.getHeight() );
        sellButton.setPosition((startX + spriteWidthChest )  - spriteWidthChest / 2 +300, startY + buyButton.getHeight() );
        displayItems.add(sellButton);
//        ShapeRenderer shapeRenderer = new ShapeRenderer();
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(1, 1, 1, 1);
//        shapeRenderer.line( startX + 2* spriteWidthChest, startY + buyButton.getHeight() * 2, startX + 2 * spriteWidthChest, startY + nbSpriteChestY * spriteHeightChest);
//        shapeRenderer.end();

        return displayItems;

    }
    public boolean isIn(int x, int y,int mouseX, int mouseY, int width, int height){
        return  mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    };
    public void handleClick(int mouseX, int mouseY){
        Vector3 worldCoordinates = camera.unproject(new Vector3(mouseX, mouseY, 0));
        int mouseXWorld = (int) worldCoordinates.x;
        int mouseYWorld = (int) worldCoordinates.y;


        for (Sprite sprite: spritesBuy) {
            if (isIn((int) sprite.getX(), (int) sprite.getY(), mouseXWorld, mouseYWorld, (int) sprite.getWidth(), (int) sprite.getHeight())) {
                System.out.println("you clicked on weapons to buy");
                spritesSell.add(sprite);
                spritesBuy.remove(sprite);
                System.out.println(spritesBuy);
                System.out.println( "current account"+ player.getMoney());
                player.pay(linkItemsSprite.get(sprite).getCost());
                System.out.println("player bought a weapon he has : " + player.getMoney() +" coins.");
                player.addItem(linkItemsSprite.get(sprite));

            }
        }
        for (Sprite sprite: spritesSell) {
            if (isIn((int) sprite.getX(), (int) sprite.getY(), mouseXWorld, mouseYWorld, (int) sprite.getWidth(), (int) sprite.getHeight())) {
                System.out.println("sprite to sell : " + spritesSell);
                System.out.println("you clicked on weapons to sell");
                System.out.println("player has : " + player.getMoney() +" coins.");
                spritesBuy.add(sprite);
                spritesSell.remove(sprite);
                player.addMoney(linkItemsSprite.get(sprite).getCost());
                System.out.println("player has now :" + player.getMoney() + " coins");
                player.removeItem(linkItemsSprite.get(sprite));
            }
        }

        if(isIn((int) buyButton.getX(), (int) buyButton.getY(),mouseXWorld, mouseYWorld, (int) buyButton.getWidth(), (int) buyButton.getHeight())){
            System.out.println("you clicked on buyButton");

        }
        else if(isIn((int)sellButton.getX(),(int)sellButton.getY(),mouseXWorld,mouseYWorld,(int)sellButton.getWidth(),(int)sellButton.getHeight())){
            System.out.println("you clicked on sellButton");


        }


    }



}