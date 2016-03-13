package items;
import game.Character;
/**
 * Created by Johan on 2016-03-13.
 */
public class Potion implements Item {
    @Override
    public String getName() {
        return "Potion";
    }

    @Override
    public String getDescrition() {
        return "Heals for 20";
    }

    @Override
    public void use(Character c) {
        c.setCurrentHealth(c.getCurrentHealth()+20);
        System.out.println(getName()+ " heals "+ c.getName()+" for 20 health");
        c.getInventory().remove(this);
    }

    @Override
    public void wear(Character c) {
        System.out.println("Cannot wear "+getName());
    }
}
