package Main;

import javax.swing.JFrame;
import java.io.IOException;

//Această clasă conține metoda principală (main) care este punctul de intrare în aplicație.
public class Main {
    public static void main(String[] args){
        JFrame window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("The Ring");

         GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);



        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}




