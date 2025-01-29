package AI;

import Main.GamePanel;

import java.util.ArrayList;

import Entity.Entity;

//Gestionarea căutării drumurilor, inclusiv setarea și resetarea nodurilor, deschiderea nodurilor și urmărirea traseului.
public class Pathfinder {

    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;

    int step = 0;

    //Constructorul clasei, inițializează GamePanel și apelează metoda instantiateNodes.
    public Pathfinder(GamePanel gp) {
        this.gp = gp;

        instantiateNodes();
    }

    //Creează și inițializează matricea de noduri pentru întreaga lume de joc, setând coordonatele fiecărui nod.
    public void instantiateNodes() {
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row] = new Node(col, row);
            col++;

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    //Resetează starea tuturor nodurilor, inclusiv marcajele pentru deschis, verificat și solid, și resetează listele de noduri deschise și de traseu.
    public void resetNodes() {
        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    //Setează nodurile de început și de sfârșit, marchează nodurile solide pe baza hărții și calculează costurile pentru fiecare nod.
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow,Entity entity) {

        resetNodes();
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
            if (gp.tileM.tile[tileNum].collision) {
                node[col][row].solid = true;
            }

            getCost(node[col][row]);

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }

    }

    //Calculează costurile G (de la start la nodul curent), H (de la nodul curent la țintă) și F (suma costurilor G și H) pentru un nod.
    public void getCost(Node node) {

        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost + node.hCost;
    }

    //Execută algoritmul de căutare A*, deschizând noduri și actualizând nodul curent până când ținta este atinsă sau numărul de pași maxim este depășit.
    public boolean search() {
        while (!goalReached && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.checked = true;
            openList.remove(currentNode);

            if (row - 1 >= 0) {
                openNode(node[col][row - 1]);
            }
            if (col - 1 >= 0) {
                openNode(node[col - 1][row]);
            }

            if (row + 1 < gp.maxWorldRow) {
                openNode(node[col][row + 1]);
            }
            if (row + 1 < gp.maxWorldCol) {
                openNode(node[col + 1][row]);
            }

            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            if (openList.isEmpty()) {
                break;
            }

            currentNode = openList.get(bestNodeIndex);
            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step++;

        }

        return goalReached;
    }

    //Urmărește traseul de la nodul de țintă înapoi la nodul de start și îl adaugă în pathList.
    private void trackThePath() {

        Node current = goalNode;

        while (current != startNode) {
            pathList.addFirst(current);
            current = current.parent;
        }

    }

    //Deschide un nod, dacă nu este deja deschis, verificat sau solid, îl marchează ca deschis și setează părintele nodului la nodul curent, apoi adaugă nodul în lista deschisă.
    private void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
}


