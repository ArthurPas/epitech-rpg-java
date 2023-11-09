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

import java.lang.reflect.Modifier;
import java.util.Map;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Map<Tile, Sprite> textures;
    Room room;
    Animation<TextureRegion> animation;
    float elapsed;
    Character player;
    Monster monster;


    @Override
    public void create() {
        batch = new SpriteBatch();
        room = new Room(10, 10);
        textures = room.createMap();
        player = new Player(0, null, 10, room.getSpecificTile(10, 10));
        monster = new Monster("Wolf", null, null, room.getSpecificTile(500, 500));
        System.out.println("init" + monster.getPosition().getX() + " " + monster.getPosition().getY());
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                player.move(room, player.getPosition(), x, Gdx.graphics.getHeight()-y);
                return true;
            }
        });
        Timer.schedule(new Timer.Task(){
                           @Override
                           public void run() {
                           System.out.println("coucou");
                            monster.move(room, monster.getPosition());
                               System.out.println(monster.getPosition().getX() + " " + monster.getPosition().getY());
                           }
                       }
                , 1        //    (delay)
                , 1 //    (seconds)
        );
    }

    @Override
    public void render() {
        batch.begin();
        for (Tile tile : textures.keySet()) {
            if (tile.isDoor()) {
                Sprite sprite = textures.get(tile);
                sprite.setSize(room.getRelativeWidth(), room.getRelativeHeight());
                sprite.setPosition(tile.getX(), tile.getY());
                sprite.draw(batch);
            }
            Sprite sprite = textures.get(tile);
            sprite.setSize(room.getRelativeWidth(), room.getRelativeHeight());
            sprite.setPosition(tile.getX(), tile.getY());
            sprite.draw(batch);
        }
        Sprite heroSprite = new Sprite(new Texture("character/hero.png"));
        heroSprite.setSize(room.getRelativeWidth(), room.getRelativeHeight());
        heroSprite.setPosition(player.getPosition().getX(), player.getPosition().getY());
        heroSprite.draw(batch);
        Sprite monsterSprite = new Sprite(new Texture("character/monsters/wolf_1.png"));
        monsterSprite.setSize(room.getRelativeWidth(), room.getRelativeHeight());
        monsterSprite.setPosition(monster.getPosition().getX(), monster.getPosition().getY());
        monsterSprite.draw(batch);
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
