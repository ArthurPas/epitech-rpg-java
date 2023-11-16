package com.mygdx.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Game;
import com.mygdx.game.room.Room;
import com.mygdx.game.Tile;
import com.mygdx.item.Item;
import com.mygdx.item.Weapon;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Monster extends Character {
    private boolean deathSoundPlayed = false;
    private  float dropWeaponProb;
    private float vivacity;

    public static int indexMonster = 0;

    private String pathToAsset;
    public Monster(String name, Map<Stat, Integer> stat, Weapon weaponEquiped, Tile position, float dropWeaponProb, float vivacity) {
        super(name, stat, weaponEquiped, position);
        this.dropWeaponProb = dropWeaponProb;
        this.vivacity = vivacity;
        File dir = new File("character/monsters");
        File[] monsterAssets = dir.listFiles();
        assert monsterAssets != null;
        this.pathToAsset = monsterAssets[(indexMonster*3)%monsterAssets.length].getPath();
        System.out.println(pathToAsset);
        indexMonster++;
    }
    public void move(Room room, Tile position) {
        List<Tile> neighbors = room.getNeighborsWalkable(position,1);
        Tile destination = neighbors.get((int) (Math.random() * neighbors.size()));
        setPosition(destination);
    }


    public float getVivacity() {
        return vivacity;
    }
    public float getDropWeaponProb() {
        return dropWeaponProb;
    }

    public void setDropWeaponProb(float dropWeaponProb) {
        this.dropWeaponProb = dropWeaponProb;
    }

    protected Item drop(float prob) {
        return null;
    }
    public void setVivacity(float vivacity) {
        this.vivacity = vivacity;
    }

    public String getPathToAsset() {
        return pathToAsset;
    }

    public void setPathToAsset(String pathToAsset) {
        this.pathToAsset = pathToAsset;
    }


    @Override
    public int attack(Character character) {
         Sound monsterAttack = Gdx.audio.newSound(Gdx.files.internal("soundEffects/heroHurt.wav"));
            monsterAttack.play(1.0f);
        return super.attack(character);

    }

    @Override
    public boolean isDead() {
        if (!deathSoundPlayed) {
            Sound doorOpenedAudio = Gdx.audio.newSound(Gdx.files.internal("soundEffects/doorOpened.ogg"));
            doorOpenedAudio.play(1.0f);
            deathSoundPlayed = true; //
        }
        return super.isDead();
    }
}


