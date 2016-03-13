package game;

/**
 * Created by Johan on 2016-02-16.
 */
public class Position {
    private int x;
    private int y;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        Position p = (Position)obj;
        return (p.getX() == this.getX() && p.getY() == this.getY());
    }

    @Override
    public String toString() {
        return "game.Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
