package Main;

import object.OBJ_Heart;
import Entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

//Gestionarea interfeței grafice a utilizatorului
public class UI {

    public String currentDialogue;
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    BufferedImage heart_full, heart_half, heart_empty;
    BufferedImage axeImage;
    public boolean messageOn = false;
    public String message = "";
    public int commandNum = 0;


    //Constructorul clasei, inițializează variabilele necesare și încarcă imaginile necesare pentru interfața utilizatorului.
    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);

        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_empty = heart.image3;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }


    //Desenează interfața utilizatorului în funcție de starea actuală a jocului.
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if (gp.gameState == gp.playState) {
            drawPlayerLife();
        }

        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }

        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }

        if(gp.gameState == gp.endingState){
            drawEndingState();
        }
//        if(gp.gameState == gp.optionsState){
//            drawOptionsScreen();
//        }

        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
    }

    private void drawEndingState() {

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "Congrats!!";
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }

        text = "Back to main menu";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }
    }

    private void drawGameOverScreen() {

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "You died..";
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x=getXforCenteredText(text);
        y+= gp.tileSize*4;
        g2.drawString(text,x,y);
        if(commandNum == 0)
        {
            g2.drawString(">",x-40,y);
        }

        text = "Back to main menu";
        x=getXforCenteredText(text);
        y+=55;
        g2.drawString(text,x,y);
        if(commandNum == 1)
        {
            g2.drawString(">",x-40,y);
        }


    }

//    private void drawOptionsScreen() {
//        g2.setColor(Color.WHITE);
//        g2.setFont(g2.getFont().deriveFont(32F));
//
//        int frameX = gp.tileSize*6;
//        int frameY = gp.tileSize;
//        int frameWidth = gp.tileSize*16;
//        int frameHeight = gp.tileSize*12;
//
//        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
//    }

    private void drawPlayerLife() {

        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_empty, x, y, null);
            i++;
            x += gp.tileSize;
        }

        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;

            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }

    }

    public void drawTitleScreen() {
        BufferedImage image;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/cover.png")));
            g2.drawImage(image, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        g2.setColor(Color.green);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));

        String text = "NEW GAME";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 4;
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }


        text = "QUIT GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }


    }

    public void drawPauseScreen() {
        String text = "The game is paused";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    //Desenează o fereastră secundară pe ecran.
    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 180);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    //Calculează poziția X pentru a centra un text pe ecran.
    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

}
