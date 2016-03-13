package game;

/**
 * Created by Johan on 2016-02-20.
 */
public class Staircase extends Tile {
    Tile landingStairCase;
    Position newPos;
    public Staircase(Boolean blocking,Tile landingStairCase,Position newPos) {
        super(blocking);
        this.landingStairCase = landingStairCase;
        this.newPos = newPos;
    }
    public void use(Character c){
        this.setOccupant(null);
        landingStairCase.setOccupant(c);
        c.setPos(newPos);
    }
}
