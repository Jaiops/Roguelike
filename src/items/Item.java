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

}


