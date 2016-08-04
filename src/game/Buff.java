package game;

/**
 * Created by Johan on 2016-08-02.
 */
public abstract class Buff {

    public Buff(double turns) {
        this.turns = turns;
        nextTick = turns-1;
    }

    private double nextTick;
    private double turns;
    public abstract void startEffect(Character c);
    public abstract void update(Character c);
    public abstract void endEffect(Character c);
    public void decrementTurn(double dec){
        turns-= dec;
    }

    public double getTurns() {
        return turns;
    }

    public double getNextTick() {
        return nextTick;
    }

    public void setNextTick() {
        nextTick = nextTick-1;
    }

}
