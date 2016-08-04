package game;

import java.util.ArrayList;

/**
 * Created by Johan on 2016-02-16.
 */
public class StandardEvilAi implements Ai{
    private Pathfinding p;
    private FieldOfView fov;
    private Character target;
    public StandardEvilAi(){
        fov = new FieldOfView();
        p = new Pathfinding();
        target = null;
    }

    @Override
    public void takeTurn(Map m, Character c) {
        ArrayList<Position> positions= fov.calculateFov(m,c.getPos());
        findGoodTarget(m,positions);
        if(target!= null){
            ArrayList<Position> path = p.getPath(m, c.getPos(), target.getPos());
            if(path.size()>0){
                c.moveOrAttack(path.get(path.size() - 1).getX() - c.getPos().getX(),
                        path.get(path.size() - 1).getY() - c.getPos().getY(), m);
            }else{
                System.err.println("Evil AI found no moveOrAttack");
            }
        }
        else{
            c.increaseTurn(1.0);
        }
    }
    private Character findGoodTarget(Map m,ArrayList<Position> positions){
        for(Position p : positions){
            Tile t = m.getTiles()[p.getY()][p.getX()];
            if(t.hasOccupant() && t.getOccupant().getAlignment() == Alignment.GOOD){
                target = m.getTiles()[p.getY()][p.getX()].getOccupant();
                return target;
            }
        }
        return null;
    }
}
