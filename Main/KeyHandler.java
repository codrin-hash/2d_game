package Main;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//Gestionează intrările de la tastatură, înregistrând apăsările de taste și traducându-le în acțiuni în joc.
public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, enterPressed;
    public boolean showDebugtext = false;

    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code==KeyEvent.VK_Q)
        {
            gp.unloadDB();
        }
        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                }
                if (gp.ui.commandNum == 1) {
                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }

        if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }

        if(gp.gameState == gp.endingState){
            endingState(code);
        }

        if (gp.gameState == gp.playState) {

            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }



//            if(code == KeyEvent.VK_ESCAPE){
//                gp.gameState = gp.optionsState;
//            }

        }

//        if(gp.gameState == gp.optionsState)
//        {
//            if(code == KeyEvent.VK_ESCAPE){
//                gp.gameState = gp.playState;
//            }
//        }

        if (code == KeyEvent.VK_T) {
            showDebugtext = !showDebugtext;
        }

        if (code == KeyEvent.VK_P) {
            if (gp.gameState == gp.playState)
                gp.gameState = gp.pauseState;
            else gp.gameState = gp.playState;

        }

        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

        if (gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }
    }

    private void endingState(int code) {

        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
        }

        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.retry();
                gp.playMusic(0);
            }
            else
            if(gp.ui.commandNum == 1){
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }

    }

    private void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
        }

        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.retry();
                gp.playMusic(0);
            }
            else
                if(gp.ui.commandNum == 1){
                    gp.gameState = gp.titleState;
                    gp.restart();
                }
        }
    }

//    public void optionsState(int code){
//        if(code == KeyEvent.VK_ESCAPE){
//            gp.gameState = gp.playState;
//        }
//        if(code == KeyEvent.VK_ENTER)
//            enterPressed = true;
//    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }

    }

}


