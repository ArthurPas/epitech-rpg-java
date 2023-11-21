package com.mygdx.interfaces;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.item.Item;
import com.mygdx.item.Weapon;

public class WeaponSprite {
    Weapon weapon;
    Sprite sprite;

    public WeaponSprite(Weapon weapon, Sprite sprite) {
        this.weapon = weapon;
        this.sprite = sprite;
    }

    public Weapon getItem() {
        return weapon;
    }

    public void setItem(Weapon item) {
        this.weapon = item;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
