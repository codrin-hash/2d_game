package Monster;

import Entity.Entity;
import Main.GamePanel;
import object.OBJ_Claw;

import java.util.Random;

//Reprezintă un monstru de tip lup
public class MON_Wolf extends Entity {

    GamePanel gp;

    public MON_Wolf(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = 2;
        name = "wolf";
        speed = 1;
        maxLife = 1;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setAction();
    }

    public void getImage() {
        up1 = setup("/monsters/wolf_up1", gp.tileSize, gp.tileSize);
        up2 = setup("/monsters/wolf_up2", gp.tileSize, gp.tileSize);
        down1 = setup("/monsters/wolf_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/monsters/wolf_down2", gp.tileSize, gp.tileSize);
        right1 = setup("/monsters/wolf_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/monsters/wolf_right2", gp.tileSize, gp.tileSize);
        left1 = setup("/monsters/wolf_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/monsters/wolf_left2", gp.tileSize, gp.tileSize);

    }

    public void updateMonster() {
        super.updateMonster();
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if (!onPath && tileDistance < 2) {
            int i = new Random().nextInt(100) + 1;
            if (i > 50) {
                onPath = true;
            }
        }
        if (onPath && tileDistance > 20) {
            onPath = false;
        }
    }

    public void setAction() {

        if (onPath) {

            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow);
        } else {
            actionLockCounter++;

            if (actionLockCounter == 120) {
                Random random = new Random();

                int i = random.nextInt(100) + 1;

                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75) {
                    direction = "right";
                }

                actionLockCounter = 0;
            }
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
    }

    public void checkDrop() {

        int i = new Random().nextInt(100) + 1;

        if (i < 90) {
            dropItem(new OBJ_Claw(gp));
        }
    }

}
