package com.mygdx.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Game;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.room.Room;
import com.mygdx.game.room.Tile;
import com.mygdx.item.Item;
import com.mygdx.item.Weapon;
import com.mygdx.item.equipment.Equipment;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Monster extends Character {
    private boolean deathSoundPlayed = false;
    private float dropWeaponProb;
    private float vivacity;
    private Item droped;

    private boolean alreadyEquiped = false;

    public static int indexMonster = 0;

    private String pathToAsset;

    boolean isBoss;
    public Monster(String name, Map<Stat, Integer> stat, Weapon weaponEquiped, Tile position, float dropWeaponProb, float vivacity, boolean isBoss) {
        super(name, stat, weaponEquiped, position);
        this.dropWeaponProb = dropWeaponProb;
        this.vivacity = vivacity;
        File dir = new File("character/monsters");
        File[] monsterAssets = dir.listFiles();
        assert monsterAssets != null;
        if(isBoss){
            setPathToAsset("character/monsters/lich.png");
        }
        else{
            this.pathToAsset = monsterAssets[(indexMonster*3)%monsterAssets.length].getPath();
            indexMonster++;
        }

        this.isBoss = isBoss;
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

    public void drop() {
        float dropProb = dropWeaponProb * 100;
        int random = (int) (Math.random() * 100);
        if (random < dropProb) {
            setDroped(Equipment.getRandomEquipment(MyGdxGame.getActualRoomLevel()));
            System.out.println("droped : " + getDroped().getName());
        }

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

    public Item getDroped() {
        return droped;
    }

    public void setDroped(Item droped) {
        this.droped = droped;
    }


    @Override
    public boolean isDead() {
        if (!deathSoundPlayed) {
            Sound doorOpenedAudio = Gdx.audio.newSound(Gdx.files.internal("soundEffects/doorOpened.ogg"));
            doorOpenedAudio.play(1.0f);
            deathSoundPlayed = true; //

        }
        if (super.isDead() && droped != null && !alreadyEquiped) {
            Game.getPlayer().equip(droped);
            System.out.println("eheh j'ai des nouvelles stats : " + Game.getPlayer().getStat());
            alreadyEquiped = true;
        }
        return super.isDead();
    }
}


