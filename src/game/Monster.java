package game;

/**
 * Created by Johan on 2016-02-16.
 */
public class Monster extends Character {
    private Ai ai;
    public Monster(Position pos,Ai ai,String name,int maxHealth) {
        super(pos,name,maxHealth);
        this.ai = ai;
        this.setAlignment(Alignment.EVIL);
    }
    public void takeTurn(Map m){
        ai.takeTurn(m,this);
    }
}
