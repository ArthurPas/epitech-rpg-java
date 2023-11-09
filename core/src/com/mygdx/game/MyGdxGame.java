package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.Character.Character;
import com.mygdx.Character.Monster;
import com.mygdx.Character.Player;
import com.mygdx.Character.Stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Map<Tile, Sprite> textures;
    Room firstRoom;
    Animation<TextureRegion> animation;
    float elapsed;
    Player player;
    Monster monster;
    Game game;


    @Override
    public void create() {
        batch = new SpriteBatch();
        List<Room> rooms = new ArrayList<>();
        Map<Stat, Integer> stat = new HashMap<>();
        stat.put(Stat.HP, 10);
        monster = new Monster("Wolf", stat, null, null, 0, 0.2f);
        rooms.add(new Room(10, 10, monster));
        player = new Player(0, null, 10, null);
        game = new Game(rooms, player, 1);
        firstRoom = game.getRooms().get(0);
        player.setPosition(firstRoom.getTiles().get(0));
        monster.setPosition(firstRoom.getTiles().get(1));
        textures = firstRoom.createMap();
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
                               monster.move(firstRoom, monster.getPosition());
                           }
                       }
                , 1        //    (delay)
                , monster.getVivacity() //    (seconds)
        );
    }

    @Override
    public void render() {
        batch.begin();
        for (Tile tile : textures.keySet()) {
            if (tile.isDoor()) {
                Sprite sprite = textures.get(tile);
                sprite.setSize(firstRoom.getRelativeWidth(), firstRoom.getRelativeHeight());
                sprite.setPosition(tile.getX(), tile.getY());
                sprite.draw(batch);
            }
            Sprite sprite = textures.get(tile);
            sprite.setSize(firstRoom.getRelativeWidth(), firstRoom.getRelativeHeight());
            sprite.setPosition(tile.getX(), tile.getY());
            sprite.draw(batch);
        }
        Sprite heroSprite = new Sprite(new Texture("character/hero.png"));
        heroSprite.setSize(firstRoom.getRelativeWidth(), firstRoom.getRelativeHeight());
        heroSprite.setPosition(player.getPosition().getX(), player.getPosition().getY());
        heroSprite.draw(batch);
        Sprite monsterSprite = new Sprite(new Texture("character/monsters/goblin_9.png"));
        monsterSprite.setSize(firstRoom.getRelativeWidth(), firstRoom.getRelativeHeight());
        monsterSprite.setPosition(monster.getPosition().getX(), monster.getPosition().getY());
        monsterSprite.draw(batch);
        if (game.isWin()) {
            //TODO : add a win screen
            System.out.println("You win");
        }
        batch.end();
    }

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
