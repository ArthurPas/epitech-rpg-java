package com.mygdx.game;

import com.mygdx.character.Monster;
import com.mygdx.character.Player;
import com.mygdx.character.Stat;
import com.mygdx.game.Tile;
import com.mygdx.game.room.Room;
import com.mygdx.item.Rarity;
import com.mygdx.item.Weapon;

import java.io.File;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private List<Room> rooms;

    private Player player;

    private int difficulty;
    private boolean isWin = false;


    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public Game(int difficulty) {
        this.rooms = createsAllRooms(difficulty);
        this.player = new Player(0, null, 10, null);
        this.difficulty = difficulty;

    }

    public List<Room> createsAllRooms(int difficulty) {
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < difficulty; i++) {
            Map<Stat, Integer> stats = new HashMap<>();

            //TODO : implement with real monster stats depending on room and difficulty
            stats.put(Stat.STRENGTH, 1000);
            stats.put(Stat.AGILITY, 10);
            stats.put(Stat.HP, 1000);

            Monster monster = new Monster("Wolf", stats, new Weapon("testForDev", 1, Rarity.COMMON, 10, 5, 10, 0, "item/weapon/sword22.png"), null, 0, 0.7f);
            System.out.println(Monster.indexMonster);
            Room room = new Room(5, 5, monster);
            monster.setPosition(room.getExitTile());
            rooms.add(room);
        }
        return rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Player getPlayer() {
        return player;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public boolean isWin() {
        return isWin;
    }

    public List<Tile> play(Room room) {
        Map<Stat, Integer> stat = new HashMap<>();
        stat.put(Stat.HP, 200);
        stat.put(Stat.STRENGTH, 1);
        stat.put(Stat.AGILITY, 10);
        room.displayRandomPath(room.getEntry(), room.getExitTile(), 1);
        player.setPosition(room.getEntry());
        return room.getTiles();
    }
    public Room nextRoom(Room oldRoom) {
        if (rooms.indexOf(oldRoom) == rooms.size() - 1) {
            isWin = true;
            return null;
        }
        Room nextRoom = rooms.get(rooms.indexOf(oldRoom) + 1);
        Map<Stat, Integer> devMonsterStat = new HashMap<>();
        devMonsterStat.put(Stat.STRENGTH, 10);
        devMonsterStat.put(Stat.AGILITY, 1);
        devMonsterStat.put(Stat.HP, 10);
        return nextRoom;
    }
}
