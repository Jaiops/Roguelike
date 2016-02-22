import java.util.ArrayList;

/**
 * Created by Johan on 2016-02-16.
 */
class Node {
    Position p;
    float valueG;
    float valueH;
    float valueF;
    Node parent;
    Position target;
    public Node(Position p, float value,Position target){
        this.target = target;
        this.p = p;
        this.valueG = value;
        float x = p.getX();
        float y = p.getY();
        float x2 = target.getX();
        float y2 = target.getY();
        valueH = (float)Math.sqrt(Math.pow(x-x2,2)+Math.pow(y-y2,2));
        valueF = valueG+valueH;
        parent = null;
    }

    public Position getTarget() {
        return target;
    }

    public void setParent(Node parent){
        this.parent = parent;
    }
    public Position getP() {
        return p;
    }
    public float getValueG(){
        return valueG;
    }

    public float getValueF() {
        return valueF;
    }
    public void setValueG(float g){
        this.valueG=g;
        this.valueF = valueG+valueH;
    }

    public Node getParent() {
        return parent;
    }

    @Override
    public boolean equals(Object obj) {
        Node n =  (Node)obj;
        return p.equals(n.getP());

    }
}
public class Pathfinding {

    public Pathfinding() {
    }
    public ArrayList<Position> getPath(Map m, Position start, Position target){
        ArrayList<Position> results = new ArrayList<>();
        boolean reached = false;

        ArrayList<Node> openList = new ArrayList<>();
        Node n = new Node(start,0,target);
        openList.add(n);
        ArrayList<Node> closeList = new ArrayList<>();

        while (!reached){
            n = getBestOpenNode(openList);
            if(n.getP().equals(target)){
                reached = true;
            }
            else{
                openList.remove(n);
                closeList.add(n);
                ArrayList<Node> neighbors = addNeighbors(m,n);
                for(Node neighbor : neighbors){
                    if(closeList.contains(neighbor)){
                        Node closedNode = getNodeFromList(closeList,neighbor.getP());
                        if(closedNode != null &&closedNode.getValueG()>neighbor.getValueG()){
                            closedNode.setValueG(neighbor.getValueG());
                            closedNode.setParent(neighbor.getParent());
                        }
                    }
                    else if(openList.contains(neighbor)){
                        Node opendNode = getNodeFromList(openList,neighbor.getP());
                        if(opendNode != null && opendNode.getValueG()>neighbor.getValueG()){
                            opendNode.setValueG(neighbor.getValueG());
                            opendNode.setParent(neighbor.getParent());
                        }
                    }else{
                        openList.add(neighbor);
                    }
                }
            }
        }
        while(n.getParent()!= null){
            results.add(n.getP());
            n = n.getParent();
        }
        return results;
    }
    private Node getNodeFromList(ArrayList<Node> list, Position p){

        for(Node n : list){
            if(n.getP().equals(p)){

                return  n;
            }
        }
        return null;
    }
    private ArrayList<Node> addNeighbors(Map m,Node n){
        ArrayList<Node> results = new ArrayList<>();
        Position p = n.getP();
        for(int i = -1;i<2;i++){
            for(int j = -1;j<2;j++){
                if(!m.getTiles()[p.getY()+j][p.getX()+i].isBlocking()){
                    Node newN = new Node(new Position(p.getX()+i,p.getY()+j)
                            ,n.getValueG()+1,
                            n.getTarget());
                    newN.setParent(n);
                    results.add(newN);
                }
            }
        }
        return results;
    }
    Node getBestOpenNode(ArrayList<Node> list){
        Node n = list.get(0);
        float f = n.getValueF();
        for(Node nod : list){
            if(nod.getValueF()<f){
                n = nod;
                f = n.getValueF();
            }
        }
        return n;
    }
}
