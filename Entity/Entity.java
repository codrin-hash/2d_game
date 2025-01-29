package Entity;

import Main.GamePanel;
import Main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import Main.CollisionChecker;

//Game entities
public class Entity {

    public int claw = 0;
    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public int actionLockCounter = 0;
    public int id;
    public int hasHammer = 0;

    public boolean invincible = false;
    public int invincibleCounter = 0;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage axe_up1, axe_up2, axe_down1, axe_down2, axe_left1, axe_left2, axe_right1, axe_right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;

    public boolean collisionOn = false;
    String[] dialogues = new String[20];
    int dialogueIndex = 0;

    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter = 0;

    public int value;
    public boolean onPath;

    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public int type;
    public int type_pickupOnly = 7;

    public int maxLife;
    public int life;

    //Constructorul clasei, inițializează obiectul GamePanel.
    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    //Metodă destinată reacției entității la daune. Implementarea este specifică subclasei.
    protected void damageReaction() {
    }

    //Metodă pentru setarea acțiunii entității. Implementarea este specifică subclasei.
    public void setAction() {
    }

    //Metodă pentru a face entitatea să vorbească. Implementarea este specifică subclasei.
    public void speak() {
    }

    //Verifică dacă entitatea trebuie să lase un obiect la moarte. Implementarea este specifică subclasei.
    public void checkDrop() {

    }

    //Lansează un obiect în lumea de joc la poziția entității curente.
    public void dropItem(Entity droppedItem) {
        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }

    //Verifică coliziunile entității cu tile-urile, obiectele și alte entități (NPC-uri și jucători).
    public void checkCollisionNPC(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkPlayer(this);
    }

    //Verifică coliziunile entității cu tile-urile, obiectele, jucătorul și alte monștri, și actualizează direcția entității în funcție de coliziuni.
    public void checkCollisionMonster() {

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkPlayer(this);

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

        if (this.type == 2 && contactPlayer) {
            if (!gp.player.invincible) {
                gp.player.life -= 1;
                gp.player.invincible = true;
            }

        }

    }

    //Actualizează starea NPC-ului, verificând coliziunile.
    public void updateNPC() {
        checkCollisionNPC();
    }

    //Actualizează starea monstrului, setând acțiunea și verificând coliziunile.
    public void updateMonster(){
        setAction();
        checkCollisionMonster();
    }

    //Desenează entitatea pe ecran la poziția sa curentă, ținând cont de direcție și de animație.
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                    break;
                case "down":
                    if (spriteNum == 1)
                        image = down1;
                    if (spriteNum == 2)
                        image = down2;
                    break;
                case "left":
                    if (spriteNum == 1)
                        image = left1;
                    if (spriteNum == 2)
                        image = left2;
                    break;
                case "right":
                    if (spriteNum == 1)
                        image = right1;
                    if (spriteNum == 2)
                        image = right2;
                    break;
            }

        }
        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        if (dying) {
            dyingAnimation(g2);
        }

        g2.drawImage(image, screenX, screenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    //Animația entității atunci când moare, alternând între transparență și opacitate.
    private void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 8) {
            dying = false;
            alive = false;
        }
    }

    //Schimbă valoarea de transparență (alpha) pentru graficul curent.
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    //Încarcă și scalează o imagine pentru entitate, utilizând UtilityTool.
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, width, height);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    //Caută un drum de la poziția curentă a entității la o poziție țintă, utilizând Pathfinder pentru a determina direcția de mișcare.
    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow, this);

        if (gp.pFinder.search()) {
            int nextX = gp.pFinder.pathList.getFirst().col * gp.tileSize;
            int nextY = gp.pFinder.pathList.getFirst().row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                direction = "up";
                checkCollisionMonster();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                checkCollisionMonster();
                if (collisionOn) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollisionMonster();
                if (collisionOn) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollisionMonster();
                if (collisionOn) {
                    direction = "right";
                }
            }

//            int nextCol = gp.pFinder.pathList.get(0).col;
//            int nextRow = gp.pFinder.pathList.get(0).row;
//            if(nextCol == goalCol && nextRow == goalRow){
//                onPath = false;
//            }

        }


    }


}