package Main;

import java.awt.*;

import Main.GamePanel;

//Gestionarea evenimentelor din joc, inclusiv teleportarea și interacțiunea cu evenimentele de joc.
public class EventHandler {

    GamePanel gp;
    EventRect[][][] eventRect;
    boolean canTouchEvent = true;
    int previousEventX, previousEventY;
    int eventRectDefaultX, eventRectDefaultY;

    //Constructorul clasei, inițializează obiectul GamePanel și matricea de evenimente eventRect.
    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;

                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    //Verifică dacă un eveniment este activat de către jucător și acționează în consecință. De exemplu, teleportarea între hărți sau activarea unui eveniment special.
    public void checkEvent() {

        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(0, 28, 20, "any")) {
                teleport(1, 9, 4);
                gp.loadDB();
            } else if (hit(1, 9, 4, "any")) {
                teleport(0, 28, 20);
            } else if (hit(0, 36, 34, "any")) {
                teleport(2, 2, 3);
                gp.loadDB();
            } else if (hit(2, 2, 3, "any")) {
                teleport(0, 36, 34);
            } else if (hit(2, 8, 12, "any")) {
                gp.loadDB();
                gp.gameState = gp.endingState;
            }
        }
    }

    //Teleportează jucătorul la o altă poziție pe hartă specificată de parametrii map, col și row.
    private void teleport(int map, int col, int row) {

        gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;

        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;

    }

    //Verifică dacă jucătorul intersectează un eveniment specificat de parametrii map, col și row, și verifică direcția necesară pentru activarea evenimentului.
    public boolean hit(int map, int col, int row, String reqDirection) {

        boolean hit = false;

        if (map == gp.currentMap) {

            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }

        return hit;
    }

}
