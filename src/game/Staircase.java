package game;

/**
 * Created by Johan on 2016-02-20.
 */
public class Staircase extends Tile {
    Position sprite;
    Position newPos;
    Map m;
    public Staircase(Map m,Position newPos,boolean isDownStair) {
        super(false);
        this.m = m;
        this.newPos = newPos;
        if(isDownStair){
            sprite = new Position(41,15);
        }else{
            sprite = new Position(42,15);

        }
    }

    public Position getSprite() {
        return sprite;
    }

    public void use(Character c){
        this.setOccupant(null);
        m.getTiles()[newPos.getY()][newPos.getX()].setOccupant(c);
        c.setPos(newPos);
    }
}
