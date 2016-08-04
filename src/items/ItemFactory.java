package items;

import game.*;
import game.Character;

import java.util.Random;

/**
 * Created by Johan on 2016-08-01.
 */
public class ItemFactory{

    public static Item healthPotion(){
        ItemBuilder builder = new ItemBuilder("Health Potion",new Position(2,25));
        return builder.setHealth(15).build();

    }
    public static Item healingSalve(){
        ItemBuilder builder = new ItemBuilder("Healing Salve",new Position(1,25));
        return builder.setBuff(new Buff(5) {
            @Override
            public void startEffect(Character c) {

            }

            @Override
            public void update(game.Character c) {
                c.modifyHealth(3);
            }

            @Override
            public void endEffect(Character c) {

            }
        }).build();
    }
    public static Item speedPotion(){
        ItemBuilder builder = new ItemBuilder("Speed Potion",new Position(1,25));
        return builder.setBuff(new Buff(15) {
            @Override
            public void startEffect(Character c) {
                c.modifySpeed(-0.5);
            }

            @Override
            public void update(game.Character c) {

            }

            @Override
            public void endEffect(Character c) {
                c.modifySpeed(0.5);
            }
        }).build();
    }
    public static Item greatsword(){
        ItemBuilder builder = new ItemBuilder("Greatsword",new Position(5,28));
        return builder.setDamage(10).setSlot(EquipSlot.MAIN_HAND).setEquipSprite(new Position(43,36)).setItemSpeed(1.3).build();

    }
    public static Item chainmail(){
        ItemBuilder builder = new ItemBuilder("Chainmail",new Position(0,21));
        return builder.setDefense(5).setSlot(EquipSlot.ARMOR).setEquipSprite(new Position(6,32)).build();
    }
    public static Item randomArmor(){
        RandomNaming rn = new RandomNaming();
        Random r = new Random();
        ItemBuilder builder = new ItemBuilder("The Magic Armor "+rn.getRandomName(),new Position(0,22));

        return builder.setDefense(r.nextInt(15)).setSlot(EquipSlot.ARMOR).setEquipSprite(new Position(59,32)).build();
    }
    public static Item randomWeapon(){
        RandomNaming rn = new RandomNaming();
        Random r = new Random();
        ItemBuilder builder = new ItemBuilder(rn.getRandomName() + " The Magic GreatSword",new Position(8,29));

        return builder.setDamage(r.nextInt(15)).setSlot(EquipSlot.MAIN_HAND).setEquipSprite(new Position(12,38)).setItemSpeed(1.5).build();
    }
}