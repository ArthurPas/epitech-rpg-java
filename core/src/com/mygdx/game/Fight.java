//package com.mygdx.game;
//
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.mygdx.Character.Character;
//import com.mygdx.Character.Stat;
//
//import java.util.concurrent.TimeUnit;
//
//public class Fight {
//    private Character attacker;
//    private Character attacked;
//
//    public Fight(Character attacker, Character attacked) {
//        this.attacker = attacker;
//        this.attacked = attacked;
//    }
//    public Character getAttacker() {
//        return attacker;
//    }
//
//    public void setAttacker(Character attacker) {
//        this.attacker = attacker;
//    }
//
//    public Character getAttacked() {
//        return attacked;
//    }
//
//    public void setAttacked(Character attacked) {
//        this.attacked = attacked;
//    }
//
//    public Character fight() throws InterruptedException {
//        while (attacker.getStat().get(Stat.HP) > 0 && attacked.getStat().get(Stat.HP) > 0) {
//            attacker.attack(attacked);
//            if (attacked.getStat().get(Stat.HP) <= 0){
//                return attacker;
//            }
//            TimeUnit.SECONDS.sleep(1);
//            attacked.attack(attacker);
//            if (attacker.getStat().get(Stat.HP) <= 0){
//                return attacked;
//            }
//            TimeUnit.SECONDS.sleep(1);
//        }
//        return null;
//    }
//}
