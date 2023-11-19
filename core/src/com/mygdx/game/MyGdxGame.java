package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.character.Character;
import com.mygdx.character.Monster;
import com.mygdx.character.Player;
import com.mygdx.game.room.Room;
import com.mygdx.item.Chest;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyGdxGame extends ApplicationAdapter implements ApplicationListener {
    SpriteBatch batch;
    List<Tile> tileList;
    Room actualRoom;
    Animation<TextureRegion> animation;
    float elapsed;
    Player player;
    Monster monster;
    Game game;
    ChestInterface chestInterface;

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
    List<Sprite> chestSprites;
    List<Sprite> itemsSprites;

    List<BitmapFont> itemsPrices = new CopyOnWriteArrayList<>();
    Chest chest;

    Color itemSelectedColor = Color.GREEN;;


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
        chest = new Chest(1, 10);
        chestInterface = new ChestInterface(this.player, batch, 2, this.chest);
        chestSprites = chestInterface.displayChestInterface();
        itemsSprites = chestInterface.getItemSprites();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                player.move(actualRoom, player.getPosition(), x, Gdx.graphics.getHeight() - y);
                heroSprite = new Sprite(new Texture(player.getPathToAsset()));
                if (chestInterface.handleClick(x, y)) {
                    itemsSprites = chestInterface.getItemSprites();
                    itemSelectedColor = Color.GREEN;
                }
                else {
                    itemSelectedColor = Color.RED;
                }
                chestSprites = chestInterface.displayChestInterface();
                return true;

            }
        });
        Timer.schedule(new Timer.Task() {
                           @Override
                           public void run() {
                               if (!player.isInFight() && !monster.isDead()) {
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
        player.canFight(monster.getPosition().isNeighbor(actualRoom, player.getPosition()) && !monster.isDead() && !player.isDead());

        if (player.isInFight()) {
            Sprite heroLifeBar = new Sprite(new Texture("character/blueBar" + player.calculateLifeDividedBy4() + ".png"));
            Sprite monsterLifeBar = new Sprite(new Texture("character/redBar" + monster.calculateLifeDividedBy4() + ".png"));
            heroLifeBar.setPosition(player.getPosition().getX(), player.getPosition().getY() + actualRoom.getRelativeWidth());
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
            monsterSprite.setSize(actualRoom.getRelativeWidth() / 2, actualRoom.getRelativeHeight() / 2);
            player.setInFight(false);
            actualRoom.setDoorOpen();
        }
        if (player.isDead()) {
            heroSprite = new Sprite(new Texture("character/heroDied.png"));
            heroSprite.setPosition(player.getPosition().getX(), player.getPosition().getY());
            heroSprite.setSize(actualRoom.getRelativeWidth(), actualRoom.getRelativeHeight());
            player.setInFight(false);
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
        }

        //TODO: implement the chest interaction in game (set true for dev mode)
        player.setInChest(true);
        if (player.isInChest()) {
            for (Sprite allChestSprite : chestSprites) {
                allChestSprite.draw(batch);
            }
            int[] coords = chestInterface.coordItemSelected(chestInterface.getSpriteSelected());
            itemsPrices = chestInterface.displayWeaponCost();
            chestInterface.displayAllMoney();
            Sprite easterEgs = new Sprite(new Texture("nem.jpg"));
            easterEgs.setSize(0, 0);
            easterEgs.draw(batch);
            if (coords != null) {
                ShapeRenderer shapeRenderer = new ShapeRenderer();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(itemSelectedColor);
                shapeRenderer.rect(coords[0], coords[1], coords[2], coords[3]);
                shapeRenderer.end();
            }

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
