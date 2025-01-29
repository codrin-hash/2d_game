package AI;

//Utilizată pentru algoritmi de căutare a drumului, reprezentând un punct într-o rețea sau hartă.
public class Node {

    Node parent;
    public int col;
    public int row;
    int gCost;
    int hCost;
    int fCost;
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int col, int row){
        this.col = col;
        this.row = row;
    }

}
