package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Sound;
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
    Boolean youWillDieAudioPlayed = false;
    Boolean heroDiedAudioPlayed = false;

    boolean monsterDiedAudioPlayed = false;

    float timeBeforeDeath = 0f;
    float deathDelay = 1f;
    boolean moveRight = false;
    boolean moveLeft = false;
    boolean moveTop = false;
    boolean moveBottom = false;
    InputAdapter inputAdapter;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        game = new Game(4);
        actualRoom = game.getRooms().get(0);
        monsterSprite = new Sprite(new Texture(actualRoom.getMonster().getPathToAsset()));
        game.play(actualRoom);
        player = game.getPlayer();
        player.setX(player.getPosition().getX());
        player.setY(player.getPosition().getY());
        heroSprite = new Sprite(new Texture(player.getPathToAsset()));

        monster = actualRoom.getMonster();
        chest = new Chest(1, 10);
        chestInterface = new ChestInterface(this.player, batch, 2, this.chest);
        chestSprites = chestInterface.displayChestInterface();
        itemsSprites = chestInterface.getItemSprites();
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
        inputAdapter = new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                player.move(actualRoom, player.getPosition(), x, Gdx.graphics.getHeight() - y);
                heroSprite = new Sprite(new Texture(player.getPathToAsset()));
                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                int speed = 50;
                int newX = player.getX();
                int newY = player.getY();

                switch (keycode) {
                    case Input.Keys.W:
                        player.move(actualRoom, newX, newY, newX, newY + speed);
                        break;

                    case Input.Keys.A:
//                            newX -= speed;
                        player.move(actualRoom, newX, newY, newX - speed, newY);
                        break;
                    case Input.Keys.S:
//                                newY -= speed;
                        player.move(actualRoom, newX, newY, newX, newY - speed);
                        break;
                    case Input.Keys.D:
//                                    newX += speed;
                        player.move(actualRoom, newX, newY, newX + speed, newY);
                        break;
                    default:
                        System.out.println("not the right key");
                }
                Sound footStepAudio = Gdx.audio.newSound(Gdx.files.internal("soundEffects/footstep.wav"));
                footStepAudio.play(1.0f);
                heroSprite = new Sprite(new Texture(player.getPathToAsset()));
                return true;
            }
        };
    }
    public void drawFloor() {
        for (Tile tile : actualRoom.getTiles()) {
            Sprite sprite = new Sprite(new Texture(tile.getPathToAsset()));
            sprite.setSize(actualRoom.getRelativeWidth(), actualRoom.getRelativeHeight());
            sprite.setPosition(tile.getX(), tile.getY());
            sprite.draw(batch);
                }
    @Override
    public void render() {
        batch.begin();
        drawFloor();
        Gdx.input.setInputProcessor(inputAdapter);
        monsterSprite.draw(batch);
        heroSprite.draw(batch);

        heroSprite.setSize(actualRoom.getRelativeWidth(), actualRoom.getRelativeHeight());
        heroSprite.setPosition(player.getX(), player.getY());


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
            if (!youWillDieAudioPlayed) {
                Sound youwillDieAudio = Gdx.audio.newSound(Gdx.files.internal("soundEffects/youWillDie.wav"));
                youwillDieAudio.play(1.0f);
                youWillDieAudioPlayed = true;
            }
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
            Sound monsterDied = Gdx.audio.newSound(Gdx.files.internal("soundEffects/monsterDied.wav"));


            timeBeforeDeath += Gdx.graphics.getDeltaTime();
            if (timeBeforeDeath > deathDelay) {
                if (!monsterDiedAudioPlayed) {
                    monsterDied.play(1.0f);
                    monsterDiedAudioPlayed = true;
                }
                timeBeforeDeath = 0f;
            }
            youWillDieAudioPlayed = false;

        }
        if (player.isDead()) {
            heroSprite = new Sprite(new Texture("character/heroDied.png"));
            heroSprite.setPosition(player.getPosition().getX(), player.getPosition().getY());
            heroSprite.setSize(actualRoom.getRelativeWidth(), actualRoom.getRelativeHeight());
            player.setInFight(false);
            timeBeforeDeath += Gdx.graphics.getDeltaTime();
            if (timeBeforeDeath > deathDelay) {
                if (!heroDiedAudioPlayed) {
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

        if (actualRoom.isDoorOpen() && actualRoom.getNeighbors(actualRoom.getExitTile(),1).contains(player.getPosition())) {
            actualRoom = game.nextRoom(actualRoom);
            game.play(actualRoom);
            monster = actualRoom.getMonster();
            monsterSprite = new Sprite(new Texture(actualRoom.getMonster().getPathToAsset()));
            monsterDiedAudioPlayed = false;
        }
        List<Weapon> weaponsToSell = new ArrayList<>();
        weaponsToSell.add(new Weapon("test", 10, Rarity.RARE, 10, 10, 10f, 10, "item/weapon/sword8.png"));
        weaponsToSell.add(new Weapon("test2", 10, Rarity.RARE, 10, 10, 10f, 10, "item/weapon/sword22.png"));
        weaponsToSell.add(new Weapon("test3", 10, Rarity.RARE, 10, 10, 10f, 10, "item/weapon/sword25.png"));
        List<Item> weaponsToBuy = new ArrayList<>();
        weaponsToBuy.add(new Weapon("myWeapon1", 10, Rarity.RARE, 10, 10, 10f, 10, "item/weapon/sword24.png"));
        weaponsToBuy.add(new Weapon("myWeapon2", 10, Rarity.RARE, 10, 10, 10f, 10, "item/weapon/sword8.png"));

        player.setInventory(weaponsToBuy);
        testChestInterface = new ChestInterface(weaponsToSell, player, batch, 2);

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

