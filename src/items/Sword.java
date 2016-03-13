package items;

import game.Character;

/**
 * Created by Johan on 2016-03-13.
 */
public class Sword implements Item {
    @Override
    public String getName() {
        return "Sword of cray";
    }

    @Override
    public String getDescrition() {
        return "Offense +2";
    }

    @Override
    public void use(game.Character c) {
        System.out.println("Cannot use "+getName());
    }

    @Override
    public void wear(Character c) {

    }
}
