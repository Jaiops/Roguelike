package items;

import game.Buff;
import game.Position;

/**
 * Created by Johan on 2016-08-01.
 */
public class ItemBuilder {
    Item item;


    public ItemBuilder(String n, Position mapSprite){
        item = new Item();
        item.mapSprite = mapSprite;
        item.name = n;
        item.slot = EquipSlot.NULL;
        item.description = "Undescribed";
        item.damage = 0;
        item.health = 0;
        item.defense = 0;
        item.globalSpeed = 0;
        item.itemSpeed = 1.0;
        item.buff = null;
    }

    public ItemBuilder setEquipSprite(Position position){
        item.equipSprite = position;
        return this;
    }

    public ItemBuilder setSlot(EquipSlot slot){
        item.slot = slot;
        return this;
    }

    public ItemBuilder setDamage(int damage) {
        item.damage = damage;
        return this;
    }

    public ItemBuilder setHealth(int health) {
        item.health = health;
        return this;
    }

    public ItemBuilder setDefense(int defense) {
        item.defense = defense;
        return this;
    }
    public ItemBuilder setBuff(Buff buff) {
        item.buff = buff;
        return this;
    }
    public ItemBuilder setGlobalSpeed(double speed){
        item.globalSpeed = speed;
        return this;
    }
    public ItemBuilder setItemSpeed(double speed){
        item.itemSpeed = speed;
        return this;
    }

    public Item build(){
        return item;
    }

}
