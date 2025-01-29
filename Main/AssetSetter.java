package Main;

import Entity.NPC_Armorer;
import Entity.NPC_WeaponMerch;
import Entity.NPC_Wife;
import Monster.MON_Bandit;
import Monster.MON_Goblin;
import Monster.MON_Wolf;
import object.OBJ_Hammer;
import object.OBJ_Ring;
import object.OBJ_Topor;

//Setarea inițială a obiectelor, NPC-urilor și monștrilor în joc.
public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        int mapNum = 0;
        gp.obj[mapNum][0] = new OBJ_Topor(gp);
        gp.obj[mapNum][0].worldX = 45 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 43 * gp.tileSize;

        mapNum++;
        gp.obj[mapNum][1] = new OBJ_Hammer(gp);
        gp.obj[mapNum][1].worldX = 7 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 20 * gp.tileSize;

        mapNum++;

        gp.obj[mapNum][1] = new OBJ_Ring(gp);
        gp.obj[mapNum][1].worldX = 8 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 12 * gp.tileSize;


    }

    public void setNPC() {
        int mapNum = 0;
        gp.npc[mapNum][0] = new NPC_Wife(gp);
        gp.npc[mapNum][0].worldX = gp.tileSize * 15;
        gp.npc[mapNum][0].worldY = gp.tileSize * 30;

        gp.npc[mapNum][1] = new NPC_Armorer(gp);
        gp.npc[mapNum][1].worldX = gp.tileSize * 17;
        gp.npc[mapNum][1].worldY = gp.tileSize * 29;

        gp.npc[mapNum][2] = new NPC_WeaponMerch(gp);
        gp.npc[mapNum][2].worldX = gp.tileSize * 22;
        gp.npc[mapNum][2].worldY = gp.tileSize * 34;
    }

    public void setMonster() {
        int mapNum = 0;
        gp.monster[mapNum][0] = new MON_Wolf(gp);
        gp.monster[mapNum][0].worldX = gp.tileSize * 42;
        gp.monster[mapNum][0].worldY = gp.tileSize * 42;

        gp.monster[mapNum][1] = new MON_Wolf(gp);
        gp.monster[mapNum][1].worldX = gp.tileSize * 41;
        gp.monster[mapNum][1].worldY = gp.tileSize * 44;

        gp.monster[mapNum][2] = new MON_Wolf(gp);
        gp.monster[mapNum][2].worldX = gp.tileSize * 43;
        gp.monster[mapNum][2].worldY = gp.tileSize * 43;

        mapNum = 1;

        gp.monster[mapNum][0] = new MON_Bandit(gp);
        gp.monster[mapNum][0].worldX = gp.tileSize * 10;
        gp.monster[mapNum][0].worldY = gp.tileSize * 20;

        gp.monster[mapNum][1] = new MON_Bandit(gp);
        gp.monster[mapNum][1].worldX = gp.tileSize * 9;
        gp.monster[mapNum][1].worldY = gp.tileSize * 21;

        gp.monster[mapNum][2] = new MON_Bandit(gp);
        gp.monster[mapNum][2].worldX = gp.tileSize * 10;
        gp.monster[mapNum][2].worldY = gp.tileSize * 18;

        gp.monster[mapNum][3] = new MON_Bandit(gp);
        gp.monster[mapNum][3].worldX = gp.tileSize * 11;
        gp.monster[mapNum][3].worldY = gp.tileSize * 17;

        mapNum = 2;

        gp.monster[mapNum][0] = new MON_Goblin(gp);
        gp.monster[mapNum][0].worldX = gp.tileSize * 21;
        gp.monster[mapNum][0].worldY = gp.tileSize * 3;

        gp.monster[mapNum][1] = new MON_Goblin(gp);
        gp.monster[mapNum][1].worldX = gp.tileSize * 22;
        gp.monster[mapNum][1].worldY = gp.tileSize * 4;

        gp.monster[mapNum][2] = new MON_Goblin(gp);
        gp.monster[mapNum][2].worldX = gp.tileSize * 23;
        gp.monster[mapNum][2].worldY = gp.tileSize * 4;

        gp.monster[mapNum][3] = new MON_Goblin(gp);
        gp.monster[mapNum][3].worldX = gp.tileSize * 24;
        gp.monster[mapNum][3].worldY = gp.tileSize * 3;

        gp.monster[mapNum][4] = new MON_Goblin(gp);
        gp.monster[mapNum][4].worldX = gp.tileSize * 23;
        gp.monster[mapNum][4].worldY = gp.tileSize * 12;

        gp.monster[mapNum][5] = new MON_Goblin(gp);
        gp.monster[mapNum][5].worldX = gp.tileSize * 21;
        gp.monster[mapNum][5].worldY = gp.tileSize * 12;

        gp.monster[mapNum][6] = new MON_Goblin(gp);
        gp.monster[mapNum][6].worldX = gp.tileSize * 9;
        gp.monster[mapNum][6].worldY = gp.tileSize * 12;

        gp.monster[mapNum][7] = new MON_Goblin(gp);
        gp.monster[mapNum][7].worldX = gp.tileSize * 10;
        gp.monster[mapNum][7].worldY = gp.tileSize * 13;

        gp.monster[mapNum][8] = new MON_Goblin(gp);
        gp.monster[mapNum][8].worldX = gp.tileSize * 11;
        gp.monster[mapNum][8].worldY = gp.tileSize * 13;

        gp.monster[mapNum][9] = new MON_Goblin(gp);
        gp.monster[mapNum][9].worldX = gp.tileSize * 8;
        gp.monster[mapNum][9].worldY = gp.tileSize * 14;

        gp.monster[mapNum][10] = new MON_Goblin(gp);
        gp.monster[mapNum][10].worldX = gp.tileSize * 9;
        gp.monster[mapNum][10].worldY = gp.tileSize * 13;

        gp.monster[mapNum][11] = new MON_Goblin(gp);
        gp.monster[mapNum][11].worldX = gp.tileSize * 10;
        gp.monster[mapNum][11].worldY = gp.tileSize * 12;

    }
}
