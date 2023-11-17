package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
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
    Room actualRoom;
    Animation<TextureRegion> animation;
    float elapsed;
    Player player;
    Monster monster;
    Game game;
    ChestInterface testChestInterface;

    float timeSeconds = 0f;
    float period = 2f;
    float timeSeconds2 = 0f;
    float timeSeconds3 = 0f;
    float period2 = 1f;
    boolean attacker = true;
    BitmapFont font;

    int dammageDeal;
    Sprite heroSprite;
    Sprite monsterSprite;
    Boolean youWillDieAudioPlayed = false;

    Boolean heroDiedAudioPlayed = false;

    boolean monsterDiedAudioPlayed = false;

    float timeBeforeDeath = 0f;
    float deathDelay = 1f;
    boolean moveRight = false;
    boolean moveLeft = false;
    boolean moveTop = false;
    boolean moveBottom = false;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        game = new Game(4);
        actualRoom = game.getRooms().get(0);
        monsterSprite = new Sprite(new Texture(actualRoom.getMonster().getPathToAsset()));
        game.play(actualRoom);
        player = game.getPlayer();
        heroSprite = new Sprite(new Texture(player.getPathToAsset()));
        monster = actualRoom.getMonster();


        Timer.schedule(new Timer.Task() {
                           @Override
                           public void run() {
                               if (!player.isInFight()&& !monster.isDead()) {
                                   monster.move(actualRoom, monster.getPosition());
                               }
                           }
                       }
                , 1        //    (delay)
                , monster.getVivacity() //    (seconds)
        );
    }


    public void drawFloor() {
        for (Tile tile : actualRoom.getTiles()) {
            Sprite sprite = new Sprite(new Texture(tile.getPathToAsset()));
            sprite.setSize(actualRoom.getRelativeWidth(), actualRoom.getRelativeHeight());
            sprite.setPosition(tile.getX(), tile.getY());
            sprite.draw(batch);
        }

    }

    @Override
    public void render() {
        batch.begin();
        drawFloor();
        monsterSprite.draw(batch);
        heroSprite.draw(batch);
        Gdx.input.setInputProcessor(new InputAdapter() {


//            @Override
//            public boolean touchDown(int x, int y, int pointer, int button) {
//                player.move(actualRoom, player.getPosition(), x, Gdx.graphics.getHeight() - y);
//                heroSprite = new Sprite(new Texture(player.getPathToAsset()));
//                return true;
//            }
            @Override
            public boolean keyDown(int keycode) {
                float speed = 100f;
                float newX = player.getPosition().getX();
                float newY = player.getPosition().getY();

                switch (keycode) {
                    case Input.Keys.W:
                        newY += speed;
                        player.setPathToAsset("character/heroBack.png");
                        break;

                        case Input.Keys.A:
                            newX -= speed;
                            player.setPathToAsset("character/heroLeft.png");
                            break;
                            case Input.Keys.S:
                                newY -= speed;
                                player.setPathToAsset("character/heroFront.png");
                                break;
                                case Input.Keys.D:
                                    newX += speed;
                                    player.setPathToAsset("character/heroRight.png");
                                    break;
                                    default:
                                        System.out.println("not the right key");
                }

                player.getPosition().setX((int) newX);
                player.getPosition().setY((int) newY);
                Sound footStepAudio = Gdx.audio.newSound(Gdx.files.internal("soundEffects/footstep.wav"));
                footStepAudio.play(1.0f);

                heroSprite.setSize(actualRoom.getRelativeWidth(), actualRoom.getRelativeHeight());
                heroSprite.setPosition(player.getPosition().getX(), player.getPosition().getY());
                return true;
            }
        });

        heroSprite.setSize(actualRoom.getRelativeWidth(), actualRoom.getRelativeHeight());
        heroSprite.setPosition(player.getPosition().getX(), player.getPosition().getY());


        monsterSprite.setSize(actualRoom.getRelativeWidth(), actualRoom.getRelativeHeight());
        monsterSprite.setPosition(monster.getPosition().getX(), monster.getPosition().getY());

        Sprite chestSprite = new Sprite(new Texture("chest_1.png"));
        chestSprite.setSize(actualRoom.getRelativeWidth(), actualRoom.getRelativeHeight());
        chestSprite.setPosition(actualRoom.getChestTile().getX(), actualRoom.getChestTile().getY());
        chestSprite.draw(batch);

        Sprite exitSprite = new Sprite(new Texture("fence.png"));
        exitSprite.setSize(actualRoom.getRelativeWidth(), actualRoom.getRelativeHeight() / 2);
        exitSprite.setPosition(actualRoom.getExitTile().getX(), actualRoom.getExitTile().getY() + actualRoom.getRelativeHeight() / 2);
        exitSprite.draw(batch);
        player.canFight(monster.getPosition().isNeighbor(actualRoom, player.getPosition())&& !monster.isDead() && !player.isDead());

        if (player.isInFight()) {
            if(!youWillDieAudioPlayed){
                Sound youwillDieAudio = Gdx.audio.newSound(Gdx.files.internal("soundEffects/youWillDie.wav"));
                youwillDieAudio.play(1.0f);
                youWillDieAudioPlayed = true;
            }
            Sprite heroLifeBar = new Sprite(new Texture("character/blueBar"+player.calculateLifeDividedBy4()+".png"));
            Sprite monsterLifeBar = new Sprite(new Texture("character/redBar"+monster.calculateLifeDividedBy4()+".png"));
            heroLifeBar.setPosition(player.getPosition().getX(), player.getPosition().getY() +actualRoom.getRelativeWidth());
            monsterLifeBar.setPosition(monster.getPosition().getX(), monster.getPosition().getY() + actualRoom.getRelativeWidth());
            heroLifeBar.setSize(actualRoom.getRelativeWidth(), actualRoom.getRelativeHeight() / 2);
            monsterLifeBar.setSize(actualRoom.getRelativeWidth(), actualRoom.getRelativeHeight() / 2);
            heroLifeBar.draw(batch);
            monsterLifeBar.draw(batch);
            timeSeconds += Gdx.graphics.getDeltaTime();
            if (timeSeconds > period) {
                timeSeconds -= period;
                dammageDeal = fightRound(player, monster, attacker);
                attacker = !attacker;
            }
        }
            //TODO loose and win management
            if (monster.isDead()) {
                monsterSprite = new Sprite(new Texture("character/death.png"));
                monsterSprite.setPosition(monster.getPosition().getX(), monster.getPosition().getY());
                monsterSprite.setSize(actualRoom.getRelativeWidth()/2, actualRoom.getRelativeHeight()/2);
                player.setInFight(false);
                actualRoom.setDoorOpen();
                Sound monsterDied = Gdx.audio.newSound(Gdx.files.internal("soundEffects/monsterDied.wav"));


                timeBeforeDeath += Gdx.graphics.getDeltaTime();
                if (timeBeforeDeath > deathDelay) {
                    if(!monsterDiedAudioPlayed){
                        monsterDied.play(1.0f);
                        monsterDiedAudioPlayed = true;
                    }
                    timeBeforeDeath = 0f;
                }
                youWillDieAudioPlayed = false;

            }
            if(player.isDead()){
                heroSprite = new Sprite(new Texture("character/heroDied.png"));
                heroSprite.setPosition(player.getPosition().getX(), player.getPosition().getY());
                heroSprite.setSize(actualRoom.getRelativeWidth(), actualRoom.getRelativeHeight());
                player.setInFight(false);
                timeBeforeDeath += Gdx.graphics.getDeltaTime();
                if (timeBeforeDeath > deathDelay) {
                    if(!heroDiedAudioPlayed){
                        Sound heroDied = Gdx.audio.newSound(Gdx.files.internal("soundEffects/heroDied.wav"));
                        heroDied.play(1.0f);
                        heroDiedAudioPlayed = true;
                    }
                    timeBeforeDeath = 0f;
                }

            }
        if (game.isWin()) {
            //TODO : add a win screen
            System.out.println("You win");

        }
        if (actualRoom.isDoorOpen() && player.getPosition() == actualRoom.getExitTile()) {
            actualRoom = game.nextRoom(actualRoom);
            game.play(actualRoom);
            monster = actualRoom.getMonster();
            monsterSprite = new Sprite(new Texture(actualRoom.getMonster().getPathToAsset()));
            monsterDiedAudioPlayed = false;
        }
        List<Weapon> weaponsToSell = new ArrayList<>();
        weaponsToSell.add(new Weapon("test",10, Rarity.RARE,10,10,10f,10,"item/weapon/sword8.png"));
        weaponsToSell.add(new Weapon("test2",10, Rarity.RARE,10,10,10f,10,"item/weapon/sword22.png"));
        weaponsToSell.add(new Weapon("test3",10, Rarity.RARE,10,10,10f,10,"item/weapon/sword25.png"));
        List<Item> weaponsToBuy = new ArrayList<>();
        weaponsToBuy.add(new Weapon("myWeapon1",10, Rarity.RARE,10,10,10f,10,"item/weapon/sword24.png"));
        weaponsToBuy.add(new Weapon("myWeapon2",10, Rarity.RARE,10,10,10f,10,"item/weapon/sword8.png"));

        player.setInventory(weaponsToBuy);
        testChestInterface = new ChestInterface(weaponsToSell,player,batch,2);



        for(Sprite sprite : testChestInterface.displayChestInterface()){
//            sprite.draw(batch);
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
        sprite.setSize(actualRoom.getRelativeWidth(), actualRoom.getRelativeHeight());
        sprite.setPosition(character.getPosition().getX(), character.getPosition().getY());
        sprite.draw(batch);
    }

    public int fightRound(Character char1, Character char2, boolean isChar1) {
        if (isChar1) {
            return char1.attack(char2);

        } else {
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
