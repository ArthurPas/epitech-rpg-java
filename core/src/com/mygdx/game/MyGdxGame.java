package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.List;
import java.util.Map;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Map<Tile, Sprite> textures;
    Room room;

    @Override
    public void create() {
        batch = new SpriteBatch();
        room = new Room(10, 10);
        textures = room.createMap();
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.begin();
        for (Tile tile : textures.keySet()) {
            Sprite sprite = textures.get(tile);
            sprite.setSize(Gdx.graphics.getWidth()/ room.getWidth(),Gdx.graphics.getHeight()/ room.getHeight());
            sprite.setPosition(tile.getX(), tile.getY());
            sprite.draw(batch);
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
