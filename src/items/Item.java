package items;
import game.Buff;
import game.Character;
import game.Position;

/**
 * Created by Johan on 2016-02-16.
 */

public class Item {
    public String name;
    public String description;
    public Position mapSprite;
    public Position equipSprite;
    public EquipSlot slot;
    public int damage;
    public int health;
    public int defense;
    //eric
    public int regenamount;
    public int regenrate; //denna tar minus c.regenrate vilket gör att den är oftare

    public double globalSpeed;
    public double itemSpeed;
    public Buff buff;
    public void equip(game.Character c){
        switch (slot){
            case ARMOR:
                if(c.getArmor()!= null){
                    c.getArmor().unequip(c);
                }
                c.setArmor(this);
                break;
            case MAIN_HAND:
                if(c.getMainHand()!= null){
                    c.getMainHand().unequip(c);
                }
                c.setMainHand(this);
                break;
            case NULL:
                c.removeInventoryItem(this);
                break;

        }
        c.modifyDamage(damage);
        c.modifyDefense(defense);
        c.modifyHealth(health);
        c.modifySpeed(globalSpeed);
        if(buff!=null){

            c.addBuff(buff);
        }
    }
    public void unequip(Character c){
        c.modifyDamage(-damage);
        c.modifyDefense(-defense);
        c.modifyHealth(-health);
        c.modifySpeed(-globalSpeed);
    }
}


