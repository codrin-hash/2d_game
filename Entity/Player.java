
package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;
import Monster.MON_Wolf;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

//Reprezintă jucătorul în joc, incluzând toate acțiunile și interacțiunile posibile, cum ar fi atacarea monștrilor, interacțiunea cu NPC-uri etc.
public class Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public int hasAxe = 0;

    //Constructorul clasei Player care inițializează jucătorul cu un GamePanel și un KeyHandler.
    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }
    //Returnează viața curentă a jucătorului.
    public int getplayerhealth()
    {
        return life;
    }

    //Setează valorile implicite pentru poziția, viteza, direcția și viața jucătorului.
    public void setDefaultValues() {
        worldX = gp.tileSize * 35;
        worldY = gp.tileSize * 35;
        speed = 4;
        direction = "down";

        maxLife = 6;
        life = maxLife;
    }

    //Încarcă imaginile sprite-urilor pentru animația de mișcare a jucătorului.
    public void getPlayerImage() {

        up1 = setup("/player/boy_up1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/boy_up2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/boy_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/boy_left2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/boy_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/boy_right2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/boy_down2", gp.tileSize, gp.tileSize);

    }

//    public void getPlayerAxeImage() {
//
//        axe_up1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize);
//        axe_up2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize);
//        axe_left1 = setup("/player/boy_axe_left_1", gp.tileSize, gp.tileSize);
//        axe_left2 = setup("/player/boy_axe_left_2", gp.tileSize, gp.tileSize);
//        axe_right1 = setup("/player/boy_axe_right_1", gp.tileSize, gp.tileSize);
//        axe_right2 = setup("/player/boy_axe_right_2", gp.tileSize, gp.tileSize);
//        axe_down1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize);
//        axe_down2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize);
//
//    }

    //ncarcă imaginile sprite-urilor pentru animația de atac a jucătorului.
    public void getPlayerAttackImage() {
        attackUp1 = setup("/player/attack_up1", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setup("/player/attack_up2", gp.tileSize, gp.tileSize * 2);
        attackRight1 = setup("/player/attack_right1", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setup("/player/attack_right2", gp.tileSize * 2, gp.tileSize);
        attackDown1 = setup("/player/attack_down1", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setup("/player/attack_down2", gp.tileSize, gp.tileSize * 2);
        attackLeft1 = setup("/player/attack_left1", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setup("/player/attack_left2", gp.tileSize * 2, gp.tileSize);
    }

    //Setează pozițiile implicite ale jucătorului în lumea de joc.
    public void setDefaultPositions(){
        worldX = gp.tileSize * 35;
        worldY = gp.tileSize * 35;
        direction = "down";
    }

    //Restaurează viața jucătorului la maxim și resetează invincibilitatea.
    public void restoreLife(){
        life = maxLife;
        invincible = false;
    }

    //Actualizează starea jucătorului pe baza acțiunilor curente, verifică coliziunile și gestionează animațiile și invincibilitatea.
    public void update() {

        if (attacking) {
            attacking();
        } else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.spacePressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else {
                direction = "right";
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            gp.eHandler.checkEvent();
            gp.keyH.spacePressed = false;


            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                }

                spriteCounter++;
                if (spriteCounter > 10) {
                    if (spriteNum == 1)
                        spriteNum = 2;
                    else if (spriteNum == 2)
                        spriteNum = 1;
                    spriteCounter = 0;
                }
            }

        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if(life <=0)
        {
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
            gp.playSE(1);
        }

    }

    //Gestionează contactul cu un monstru. Reduce viața jucătorului și setează invincibilitatea.
    private void contactMonster(int i) {
        if (i != 999) {
            if (!invincible) {
                gp.playSE(3);
                life = life - 1;
                invincible = true;
            }
        }
    }

    //Aplică daune monstrului specificat și gestionează starea monstrului (inclusiv moartea acestuia).
    private void damageMonster(int i) {
        if (i != 999) {
            if (!gp.monster[gp.currentMap][i].invincible) {
                gp.monster[gp.currentMap][i].life -= 1;
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if (gp.monster[gp.currentMap][i].life <= 0) {
                    gp.playSE(2);
                    gp.monster[gp.currentMap][i].dying = true;
                }
            }

        }

    }

    //Permite jucătorului să interacționeze cu NPC-uri. Dacă jucătorul apasă tasta de interacțiune (space), inițiază atacul sau dialogul.
    public void interactNPC(int i) {

        if (gp.keyH.spacePressed) {
            gp.playSE(5);
            attacking = true;
        }

        gp.keyH.spacePressed = false;

        if (i != 999) {
            gp.gameState = gp.dialogueState;
            gp.npc[gp.currentMap][i].speak();
        }
    }

    //Gestionează secvența de atac a jucătorului, inclusiv animația și verificarea coliziunilor cu monștri.
    public void attacking() {

        spriteCounter++;

        if (spriteCounter <= 5)
            spriteNum = 1;
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }


    //Gestionează ridicarea obiectelor de către jucător. Actualizează inventarul jucătorului și afisează mesaje corespunzătoare.
    public void pickUpObject(int i) {

        if (i != 999) {

            String objectName = gp.obj[gp.currentMap][i].name;

            if(gp.obj[gp.currentMap][i].type == type_pickupOnly){

                gp.playSE(4);
                hasHammer++;
                gp.obj[gp.currentMap][i] = null;
            }

            if (objectName.equals("axe")) {
                gp.playSE(4);
                hasAxe++;
                gp.obj[gp.currentMap][i] = null;
                gp.ui.showMessage("You got the axe!");
            }

            if (objectName.equals("Hammer")) {
                gp.playSE(4);
                hasHammer++;
                gp.obj[gp.currentMap][i] = null;
            }

            if (objectName.equals("ring")) {
                gp.playSE(4);
                gp.obj[gp.currentMap][i] = null;
                gp.gameState = gp.gameOverState;
            }

        }

    }

    //Desenează jucătorul pe ecran în funcție de direcția și starea sa (mișcare, atac, invincibilitate).
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (!attacking) {
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                }
                if (attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1)
                        image = attackUp1;
                    if (spriteNum == 2)
                        image = attackUp2;
                }
                break;
            case "down":
                if (!attacking) {
                    if (spriteNum == 1)
                        image = down1;
                    if (spriteNum == 2)
                        image = down2;
                }
                if (attacking) {
                    if (spriteNum == 1)
                        image = attackDown1;
                    if (spriteNum == 2)
                        image = attackDown2;
                }
                break;
            case "left":
                if (!attacking) {
                    if (spriteNum == 1)
                        image = left1;
                    if (spriteNum == 2)
                        image = left2;
                }
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1)
                        image = attackLeft1;
                    if (spriteNum == 2)
                        image = attackLeft2;
                }
                break;
            case "right":
                if (!attacking) {
                    if (spriteNum == 1)
                        image = right1;
                    if (spriteNum == 2)
                        image = right2;
                }
                if (attacking) {
                    if (spriteNum == 1)
                        image = attackRight1;
                    if (spriteNum == 2)
                        image = attackRight2;
                }
                break;
        }
        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));

        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


    }

}

