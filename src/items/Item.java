package items;
import game.Character;
/**
 * Created by Johan on 2016-02-16.
 */
public interface Item {
    /*public items.Item(String name){
        this.name = name;
    }*/

    public String getName();
    public String getDescrition();
    public void use(Character c);
    public void wear(Character c);

}
