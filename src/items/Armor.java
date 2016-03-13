package items;

import game.Character;

/**
 * Created by Johan on 2016-03-13.
 */
public class Armor implements Item {
    @Override
    public String getName() {
        return "Cool Armor";
    }

    @Override
    public String getDescrition() {
        return "Defense +2";
    }

    @Override
    public void use(game.Character c) {
        System.out.println("Cannot use "+getName());
    }

    @Override
    public void wear(Character c) {

    }
}
