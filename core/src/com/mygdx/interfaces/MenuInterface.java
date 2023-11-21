package com.mygdx.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.character.Player;
import com.mygdx.character.Stat;
import com.mygdx.item.Item;
import com.mygdx.item.Weapon;

import javax.swing.border.LineBorder;
import java.util.ArrayList;
import java.util.List;

public class MenuInterface {
    private Player player;
    private SpriteBatch batch;
    private int width;
    private int height;
    private int startX;
    private int startY;

    private String pathToAsset;

    private Sprite quitButtonSprite;
    private Sprite playButtonSprite;

    List<WeaponSprite> weaponSprites = new ArrayList<>();

    public MenuInterface(Player player, SpriteBatch batch) {
        this.player = player;
        this.batch = batch;
        this.width = Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 4;
        this.height = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4;
        this.startX = Gdx.graphics.getWidth() / 8;
        this.startY = Gdx.graphics.getHeight() / 8;
        pathToAsset = "menuBackground.png";
    }

    public void draw() {
        Sprite backgroundSprite = new Sprite(new Texture(Gdx.files.internal(pathToAsset)));
        backgroundSprite.setSize(width, height);
        backgroundSprite.setPosition(startX, startY);
        backgroundSprite.draw(batch);

        playButtonSprite = new Sprite(new Texture(Gdx.files.internal("playButton.png")));
        playButtonSprite.setSize(width / 5, height / 6);
        playButtonSprite.setPosition(startX + 10, startY + 10);
        playButtonSprite.draw(batch);

        quitButtonSprite = new Sprite(new Texture(Gdx.files.internal("quitButton.png")));
        quitButtonSprite.setSize(width / 5, height / 6);
        quitButtonSprite.setPosition(startX + width - playButtonSprite.getWidth(), startY + 10);
        quitButtonSprite.draw(batch);

        Sprite inventoryBGSprite = new Sprite(new Texture(Gdx.files.internal("chestBackground.png")));
        inventoryBGSprite.setSize(width - (2 * width / 5), height - (2 * height / 8));
        inventoryBGSprite.setPosition(startX + width / 5, startY + height / 5);
        inventoryBGSprite.draw(batch);
//
//        for(int i= 1 ;i<=3;i++){
//            Sprite statsBgSprite = new Sprite(new Texture(Gdx.files.internal("statsBG.png")));
//            statsBgSprite.setSize((width-(2*width/5))/3, ((height-(2*height/8))/6));
//            statsBgSprite.setPosition((startX+width/5)*i/3+(startX+width/5)-25, (startY+10));
//            statsBgSprite.draw(batch);
//        }
        Sprite hpSprite = new Sprite(new Texture(Gdx.files.internal("character/HPStat.png")));
        hpSprite.setSize(40, 40);
        hpSprite.setPosition(startX + width / 5 + 20, startY + 20);
        hpSprite.draw(batch);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("moneyFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        BitmapFont fontHP = generator.generateFont(parameter);
        fontHP.draw(batch, String.valueOf(player.getStat().get(Stat.HP)), hpSprite.getX() + hpSprite.getWidth() + 10, hpSprite.getY() + fontHP.getLineHeight());

        Sprite maxHPSprite = new Sprite(new Texture(Gdx.files.internal("character/defenseStat.png")));
        maxHPSprite.setSize(40, 40);
        maxHPSprite.setPosition(hpSprite.getX(), hpSprite.getY() + hpSprite.getHeight() + 20);
        maxHPSprite.draw(batch);
        BitmapFont fontMaxHP = generator.generateFont(parameter);
        fontMaxHP.draw(batch, String.valueOf(player.getStat().get(Stat.MAX_HP)), maxHPSprite.getX() + maxHPSprite.getWidth() + 10, maxHPSprite.getY() + fontHP.getLineHeight());

        Sprite attackSprite = new Sprite(new Texture(Gdx.files.internal("character/attackStat.png")));
        attackSprite.setSize(40, 40);
        attackSprite.setPosition(startX + (width / 5) * 2 + 20, startY + 20);
        attackSprite.draw(batch);
        BitmapFont fontAtt = generator.generateFont(parameter);
        fontAtt.draw(batch, String.valueOf(player.getStat().get(Stat.STRENGTH)), attackSprite.getX() + attackSprite.getWidth() + 10, attackSprite.getY() + fontHP.getLineHeight());

        Sprite agilitySprite = new Sprite(new Texture(Gdx.files.internal("character/agilityStat.png")));
        agilitySprite.setSize(40, 40);
        agilitySprite.setPosition(startX + (width / 5) * 2 + 20, attackSprite.getY() + attackSprite.getHeight() + 20);
        agilitySprite.draw(batch);
        BitmapFont fontAgi = generator.generateFont(parameter);
        fontAtt.draw(batch, String.valueOf(player.getStat().get(Stat.AGILITY)), agilitySprite.getX() + agilitySprite.getWidth() + 10, agilitySprite.getY() + fontHP.getLineHeight());


        Sprite moneySprite = new Sprite(new Texture(Gdx.files.internal("character/coinStat.png")));
        moneySprite.setSize(40, 40);
        moneySprite.setPosition(startX + (width / 5 * 3) + 20, startY + 20);
        moneySprite.draw(batch);
        BitmapFont fontmoney = generator.generateFont(parameter);
        fontmoney.draw(batch, String.valueOf(player.getMoney()), moneySprite.getX() + moneySprite.getWidth() + 10, moneySprite.getY() + fontHP.getLineHeight());

        List<Item> inventory = player.getInventory();
        for (Item item : inventory) {
            Sprite itemSprite = new Sprite(new Texture(Gdx.files.internal(item.getPathToAsset())));
            itemSprite.setSize(inventoryBGSprite.getHeight() / 4, inventoryBGSprite.getHeight() / 4);
            itemSprite.setPosition(inventoryBGSprite.getX() + 10, inventoryBGSprite.getY() + itemSprite.getHeight() * inventory.indexOf(item));
            itemSprite.draw(batch);

            BitmapFont fontName = generator.generateFont(parameter);
            fontName.draw(batch, item.getName(), itemSprite.getX() + itemSprite.getWidth() + 50, itemSprite.getY() + itemSprite.getHeight() / 2 + fontName.getLineHeight() / 2);
            Sprite weaponAttackSprite = new Sprite(new Texture(Gdx.files.internal("item/weapon/weaponDamage.png")));
            weaponAttackSprite.setSize(40, 40);
            weaponAttackSprite.setPosition(itemSprite.getX() + itemSprite.getWidth() + 50, (itemSprite.getY() + itemSprite.getHeight() / 2) - 50);
            weaponAttackSprite.draw(batch);
            BitmapFont fontDamage = generator.generateFont(parameter);
            Weapon weapon = (Weapon) item;
            weaponSprites.add(new WeaponSprite(weapon, itemSprite));
            fontDamage.draw(batch, String.valueOf(weapon.getDamage()), itemSprite.getX() + itemSprite.getWidth() + 100, (itemSprite.getY() + itemSprite.getHeight() / 2 + fontDamage.getLineHeight() / 2) - 30);
        }
        Weapon selectedItem = player.getWeaponEquiped();
        for (WeaponSprite weaponSprite : weaponSprites) {
            if (weaponSprite.getItem().equals(selectedItem)) {
                Sprite easterEgs = new Sprite(new Texture("nem.jpg"));
                easterEgs.setSize(0, 0);
                easterEgs.draw(batch);
                ShapeRenderer shapeRenderer = new ShapeRenderer();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.GOLD);
                shapeRenderer.rect(weaponSprite.getSprite().getX(), weaponSprite.getSprite().getY(), inventoryBGSprite.getWidth() - 20, inventoryBGSprite.getHeight() / 4);
                shapeRenderer.end();
            }
        }
    }

    public void handleClick(int mouseX, int mouseY) {

        for (WeaponSprite weaponSprite : weaponSprites) {

            if (ChestInterface.isIn((int) weaponSprite.getSprite().getX(), (int) weaponSprite.getSprite().getY(), mouseX, mouseY, (int) weaponSprite.getSprite().getWidth(), (int) weaponSprite.getSprite().getHeight())) {
                player.setWeaponEquiped(weaponSprite.getItem());
                System.out.println(weaponSprite.getItem().getName());
                System.out.println(player.getWeaponEquiped().getName());
                System.out.println("yo oooo");
            }
        }

        if (ChestInterface.isIn((int) quitButtonSprite.getX(), (int) quitButtonSprite.getY(), mouseX, mouseY, (int) quitButtonSprite.getWidth(), (int) quitButtonSprite.getHeight())) {
            Gdx.app.exit();
        }
        if (ChestInterface.isIn((int) playButtonSprite.getX(), (int) playButtonSprite.getY(), mouseX, mouseY, (int) playButtonSprite.getWidth(), (int) playButtonSprite.getHeight())) {
            player.setInMenu(false);
        }

    }


}
