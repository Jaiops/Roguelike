package game;

/**
 * Created by Johan on 2016-02-20.
 */
public class Staircase extends Tile {
    Position newPos;
    Map m;

    public Staircase(Map m, Position newPos, Position spritePosition) {
        super(false, spritePosition);

        this.m = m;
        this.newPos = newPos;

    }

    public void use(Character c) {
        this.setOccupant(null);
        m.getTiles()[newPos.getY()][newPos.getX()].setOccupant(c);
        c.setPos(newPos);
    }
}
