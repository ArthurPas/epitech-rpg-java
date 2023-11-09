package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.List;
import java.util.Map;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Map<Tile, Sprite> textures;
    Room room;
    Animation<TextureRegion> animation;
    float elapsed;

    @Override
    public void create() {
        batch = new SpriteBatch();
        room = new Room(5, 5);
        textures = room.createMap();
        ScreenUtils.clear(1, 0, 0, 1);
    }

    @Override
    public void render() {
        batch.begin();
        for (Tile tile : textures.keySet()) {
            if(tile.isDoor()){
                Sprite sprite = textures.get(tile);
                sprite.setSize(room.getRELATIVEWIDTH(), room.getRELATIVEHEIGHT());
                sprite.setPosition(tile.getX(), tile.getY());
                sprite.draw(batch);
            }
            Sprite sprite = textures.get(tile);
            sprite.setSize(room.getRELATIVEWIDTH(), room.getRELATIVEHEIGHT());
            sprite.setPosition(tile.getX(), tile.getY());
            sprite.draw(batch);
        }
        Sprite hero = new Sprite(new Texture("character/hero.png"));
        hero.setSize(room.getRELATIVEWIDTH(), room.getRELATIVEHEIGHT());
        hero.setPosition(0, 0);
        hero.draw(batch);
        Sprite monster = new Sprite(new Texture("character/monsters/wolf_1.png"));
        monster.setSize(room.getRELATIVEWIDTH(), room.getRELATIVEHEIGHT());
        monster.setPosition(room.getRELATIVEWIDTH(), room.getRELATIVEHEIGHT());
        monster.draw(batch);
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
