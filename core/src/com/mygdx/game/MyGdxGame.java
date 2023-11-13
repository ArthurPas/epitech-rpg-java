package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.character.Character;
import com.mygdx.character.Monster;
import com.mygdx.character.Player;
import com.mygdx.character.Stat;
import com.mygdx.game.room.Room;
import com.mygdx.item.Rarity;
import com.mygdx.item.Weapon;

import java.util.ArrayList;
import java.util.HashMap;

import com.mygdx.item.Item;

import java.util.List;
import java.util.Map;

public class MyGdxGame extends ApplicationAdapter implements ApplicationListener {
    SpriteBatch batch;
    List<Tile> tileList;
    Room firstRoom;
    Animation<TextureRegion> animation;
    float elapsed;
    Player player;
    Monster monster;
    Game game;

    float timeSeconds = 0f;
    float period = 2f;
    float timeSeconds2 = 0f;
    float timeSeconds3 = 0f;
    float period2 = 1f;
    boolean attacker = true;
    BitmapFont font;

    int dammageDeal;


    @Override
    public void create() {
        batch = new SpriteBatch();
        font  = new BitmapFont();


        List<Room> rooms = new ArrayList<>();
        Map<Stat, Integer> stat = new HashMap<>();
        stat.put(Stat.HP, 200);
        stat.put(Stat.STRENGTH, 1);
        stat.put(Stat.AGILITY, 10);
        monster = new Monster("Wolf", stat, new Weapon("testForDev", 1, Rarity.COMMON, 10, 5, 10, 0,"item/weapon/sword22.png"), null, 0, 0.7f);
        rooms.add(new Room(10, 10, monster));
        player = new Player(0, null, 10, null);
        game = new Game(rooms, player, 1);
        firstRoom = game.getRooms().get(0);

        player.setPosition(firstRoom.getEntry());
        firstRoom.displayRandomPath(firstRoom.getEntry(), firstRoom.getExitTile(),1);
//        monster.setPosition(firstRoom.getRandomTile());
        monster.setPosition(firstRoom.getTiles().get(33));
        tileList = firstRoom.getTiles();
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                player.move(firstRoom, player.getPosition(), x, Gdx.graphics.getHeight() - y);

                return true;
            }
        });
        Timer.schedule(new Timer.Task() {
                           @Override
                           public void run() {
                               if (!player.isInFight()) {
                                   monster.move(firstRoom, monster.getPosition());
                               }
                           }
                       }
                , 1        //    (delay)
                , monster.getVivacity() //    (seconds)
        );
    }


    public void drawFloor() {
        for (Tile tile : tileList) {
            Sprite sprite = new Sprite(new Texture(tile.getPathToAsset()));
            sprite.setSize(firstRoom.getRelativeWidth(), firstRoom.getRelativeHeight());
            sprite.setPosition(tile.getX(), tile.getY());
            sprite.draw(batch);
        }

    }

    @Override
    public void render() {
        batch.begin();
        drawFloor();

        Sprite heroSprite = new Sprite(new Texture("character/hero.png"));
        Sprite monsterSprite = new Sprite(new Texture("character/monsters/goblin_9.png"));
        if (!player.isInFight()) {

            heroSprite.setSize(firstRoom.getRelativeWidth(), firstRoom.getRelativeHeight());
            heroSprite.setPosition(player.getPosition().getX(), player.getPosition().getY());
            heroSprite.draw(batch);

            monsterSprite.setSize(firstRoom.getRelativeWidth(), firstRoom.getRelativeHeight());
            monsterSprite.setPosition(monster.getPosition().getX(), monster.getPosition().getY());
            monsterSprite.draw(batch);

            Sprite chestSprite = new Sprite(new Texture("chest_1.png"));
            chestSprite.setSize(firstRoom.getRelativeWidth(), firstRoom.getRelativeHeight());
            chestSprite.setPosition(firstRoom.getChestTile().getX(), firstRoom.getChestTile().getY());
            chestSprite.draw(batch);

            Sprite exitSprite = new Sprite(new Texture("fence.png"));
            exitSprite.setSize(firstRoom.getRelativeWidth(), firstRoom.getRelativeHeight() / 2);
            exitSprite.setPosition(firstRoom.getExitTile().getX(), firstRoom.getExitTile().getY() + firstRoom.getRelativeHeight() / 2);
            exitSprite.draw(batch);
            player.canFight(monster.getPosition().isNeighbor(firstRoom, player.getPosition()));
        } else {
            mooveCharacter(player, heroSprite);
            mooveCharacter(monster,monsterSprite);
            font.draw(batch, player.getName()+": "+String.valueOf(player.getStat().get(Stat.HP)), player.getPosition().getX()+firstRoom.getRelativeHeight()/2, player.getPosition().getY()+firstRoom.getRelativeWidth()/2);
            font.draw(batch, monster.getName()+": "+String.valueOf(monster.getStat().get(Stat.HP)), monster.getPosition().getX(), monster.getPosition().getY()+firstRoom.getRelativeWidth()/2);
            if(attacker){
                player.setPosition(firstRoom.getFightsTiles().get(1));
                monster.setPosition(firstRoom.getFightsTiles().get(2));

            }else{
                player.setPosition(firstRoom.getFightsTiles().get(0));
                monster.setPosition(firstRoom.getFightsTiles().get(1));
            }
            timeSeconds += Gdx.graphics.getDeltaTime();
            if(timeSeconds > period){
                timeSeconds-=period;
                dammageDeal = fightRound(player,monster,attacker);
                attacker = !attacker;
                player.setPosition(firstRoom.getFightsTiles().get(0));
                monster.setPosition(firstRoom.getFightsTiles().get(2));
            }
            //TODO loose and win management
            if(player.getStat().get(Stat.HP) < 0 || monster.getStat().get(Stat.HP)<0){
                player.setInFight(false);
            }
        }
        if (game.isWin()) {
            //TODO : add a win screen
            System.out.println("You win");
        }

//        List<Weapon> weapons = new ArrayList<>();
//        weapons.add(new Weapon("test",10, Rarity.RARE,10,10,10f,10,"item/weapon/sword8.png"));
//        weapons.add(new Weapon("test2",10, Rarity.RARE,10,10,10f,10,"item/weapon/sword22.png"));
//        List<Item> stuff = new ArrayList<>();
//        stuff.add(new Weapon("test",10, Rarity.RARE,10,10,10f,10,"item/weapon/sword24.png"));
//        player.setInventory(stuff);
//
//        ChestInterface test = new ChestInterface(weapons,player,batch,2);
//
//        for(Sprite sprite : test.displayChestInterface()){
//            sprite.draw(batch);
//        }
        batch.end();
    }

    public void mooveCharacter(Character character, Sprite sprite) {
        sprite.setSize(firstRoom.getRelativeWidth(), firstRoom.getRelativeHeight());
        sprite.setPosition(character.getPosition().getX(), character.getPosition().getY());
        sprite.draw(batch);
    }

    public int fightRound(Character char1, Character char2, boolean isChar1 ){
        if(isChar1) {
            return char1.attack(char2);

        }else {
           return char2.attack(char1);
        }
    }
    //.sleep not working in render
//    public Character fight(Character attacker, Character defender) {
//        Sprite heroSprite = new Sprite(new Texture("character/hero.png"));
//        Sprite monsterSprite = new Sprite(new Texture("character/monsters/goblin_9.png"));
//        System.out.println("yo1");
//
//        timeSeconds += Gdx.graphics.getDeltaTime();
//        if(timeSeconds > period){
//            timeSeconds-=period;
//            mooveCharacter(attacker, firstRoom.getFightsTiles().get(1), heroSprite);
//            timeSeconds = 0f;
//            period = 1f;
//            timeSeconds += Gdx.graphics.getDeltaTime();
//            if (timeSeconds > period) {
//                timeSeconds -= period;
//
//                attacker.attack(defender);
//                mooveCharacter(attacker, firstRoom.getFightsTiles().get(0), heroSprite);
//                if (defender.getStat().get(Stat.HP) <= 0) {
//                    return attacker;
//                }
//            }
//        }
//        mooveCharacter(defender, firstRoom.getFightsTiles().get(1), monsterSprite);
//        timeSeconds = 0f;
//        period = 1f;
//        timeSeconds += Gdx.graphics.getDeltaTime();
//        if (timeSeconds > period) {
//            timeSeconds -= period;
//            attacker.attack(defender);
//            mooveCharacter(defender, firstRoom.getFightsTiles().get(2), monsterSprite);
//            if (attacker.getStat().get(Stat.HP) <= 0) {
//                return defender;
//            }
//        }
//        return null;
//    }


    @Override
    public void dispose() {
        batch.dispose();
        /*
        for (Tile tile : textures.keySet()) {
            textures.get(tile).dispose();
        }

         */
    }
}
