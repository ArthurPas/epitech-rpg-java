package com.mygdx.game;

import com.mygdx.character.Monster;
import com.mygdx.character.Player;
import com.mygdx.character.Stat;
import com.mygdx.game.room.Tile;
import com.mygdx.game.room.Room;
import com.mygdx.item.Item;
import com.mygdx.item.Rarity;
import com.mygdx.item.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private List<Room> rooms;

    static private Player player;

    private static int difficulty;
    private boolean isWin = false;


    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void setPlayer(Player player) {
        Game.player = player;
    }

    public void setDifficulty(int difficulty) {
        Game.difficulty = difficulty;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public Game(int difficulty) {

        Game.difficulty = difficulty;
        this.rooms = createsAllRooms(difficulty);
        player = new Player(1, null, 25, null);
        List<Item> stuff = new ArrayList<>();
        stuff.add(new Weapon("testForDev4", 1, Rarity.COMMON, 10, 5, 10, 0, "item/weapon/sword22.png"));
        player.setInventory(stuff);
        player.setWeaponEquiped((Weapon) stuff.get(0));

    }

    public List<Room> createsAllRooms(int difficulty) {
        List<Room> rooms = new ArrayList<>();
        for (int i = 1; i <= difficulty; i++) {
            Map<Stat, Integer> stats = new HashMap<>();

            //TODO : implement with real monster stats depending on room and difficulty
            stats.put(Stat.STRENGTH, 1 + (i / 3));
            stats.put(Stat.AGILITY, 10 + i);
            stats.put(Stat.HP, 50 / (i * 4));
            System.out.println("yo yo yo" + stats.get(Stat.HP));
            Monster monster = new Monster("Wolf", stats, new Weapon("testForDev", 1, Rarity.COMMON, 10, 10, 1, 0, "item/weapon/sword22.png"), null, (float) 75 / i * 1.5f, 0.7f,false);
            Monster bossMonster = new Monster("Boss", stats, new Weapon("testForDev", 1, Rarity.LEGENDARY, 10, 10, 8, 0, "item/weapon/sword25.png"), null, (float) 75 / i * 1.5f, 0.7f, true);
            System.out.println(Monster.indexMonster);
            if(i == difficulty){
                Room room = new Room(10, 10, bossMonster, i);
                bossMonster.setPosition(room.getExitTile());
                rooms.add(room);
            }
            else{
                Room room = new Room(10, 10, monster, i);
                monster.setPosition(room.getExitTile());
                rooms.add(room);
            }
        }
        return rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public static Player getPlayer() {
        return player;
    }

    public static int getDifficulty() {
        return difficulty;
    }

    public boolean isWin() {
        return MyGdxGame.actualRoom.getRoomNumber() == difficulty && MyGdxGame.actualRoom.getMonster().isDead();

    }

    public List<Tile> play(Room room) {
        Map<Stat, Integer> stat = new HashMap<>();
        stat.put(Stat.HP, 100);
        stat.put(Stat.STRENGTH, 1);
        stat.put(Stat.AGILITY, 10);
        room.displayRandomPath(room.getEntry(), room.getExitTile(), 1);
        player.setPosition(room.getEntry());
        room.getMonster().drop();
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
        devMonsterStat.put(Stat.HP, 100);
        return nextRoom;
    }

}
