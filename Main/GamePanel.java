package Main;

import AI.Pathfinder;
import Entity.Player;
import Tile.TileManager;
import Entity.Entity;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//Această clasă reprezintă un panou de joc. Are variabile pentru gestionarea panoului de joc, cum ar fi dimensiunile ecranului, imagini, referințe către alte obiecte din joc, etc.
public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 27;
    public final int maxScreenRow = 15;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public int currentArea;

    public boolean canTouchEvent = false;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public final int maxMap = 10;
    public int currentMap = 0;

    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);

    Sound music = new Sound();
    Sound se = new Sound();

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    public Pathfinder pFinder = new Pathfinder(this);

    Thread gameThread;

    public Player player = new Player(this, keyH);
    public Entity[][] obj = new Entity[maxMap][10];
    public Entity[][] npc = new Entity[maxMap][10];
    ArrayList<Entity> entityList = new ArrayList<>();
    public Entity[][] monster = new Entity[maxMap][20];


    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int endingState = 7;
    public Connection c;

    //Constructorul clasei, inițializează dimensiunile panoului de joc, culoarea de fundal, se ocupă de dublarea buffer-ului, adaugă un KeyListener și stabilește focalizarea.
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        c=null;
        try{
            Class.forName("org.sqlite.JDBC");
            c= DriverManager.getConnection("jdbc:sqlite:databaseDB.db");
            c.setAutoCommit(false);
            System.out.println("Connected with success");
        }catch(Exception e)
        {
            System.err.println("Failed to connect to DB");
            e.printStackTrace();
        }
    }

    //Se ocupă de încărcarea datelor din baza de date. Inserează nivelul curent și starea de sănătate a jucătorului în baza de date.
    public void loadDB(){

        int level=currentMap;
        int health=player.getplayerhealth();
        try{
            Statement st=c.createStatement();
            String s="INSERT INTO tableTHERING(Level,Health) VALUES ("+level+","+health+");";
            st.executeUpdate(s);
            c.commit();
            st.close();
            System.out.println("Load success");
        }catch(SQLException e)
        {
            System.err.println("failed to load");
            e.printStackTrace();
        }
    }

    //Se ocupă de descărcarea datelor din baza de date. Extrage nivelurile și starea de sănătate a jucătorului din baza de date.
    public void unloadDB(){

        int level,health;
        Statement st;
        try{
            st=c.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM tableTHERING");
            while(rs.next())
            {
                level=rs.getInt("Level");
                health=rs.getInt("Health");
                System.out.println("Level: "+level+" Health: "+health);
            }
        }catch(SQLException e)
        {
            System.err.println("failed to unload from DB");
            e.printStackTrace();
        }
    }

    //Inițializează jocul, setând obiectele, NPC-urile, monștrii și muzica.
    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        playMusic(0);
        gameState = titleState;
    }

    //Restabilește jocul la starea inițială după o încercare eșuată.
    public void retry() {

        currentMap = 0;
        player.setDefaultValues();
        player.setDefaultPositions();
        player.restoreLife();
        aSetter.setNPC();
        aSetter.setMonster();

    }

    //Restabilește complet jocul la starea inițială.
    public void restart() {
        currentMap = 0;
        player.setDefaultValues();
        player.setDefaultPositions();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
    }

    //Porneste thread-ul jocului.
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Este metoda principală care rulează în buclă. Se ocupă de actualizarea și redesenarea jocului la un FPS specificat.
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;


        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                updateNPC();
                updateMonster();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void updateNPC() {

        if (gameState == playState) {
            player.update();
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].updateNPC();
                }
            }

        }

        if (gameState == pauseState) {
        }
    }
    public void updateMonster() {

        for (int i = 0; i < monster[1].length; i++) {
            if (monster[currentMap][i] != null) {
                if (monster[currentMap][i].alive && !monster[currentMap][i].dying)
                    monster[currentMap][i].updateMonster();
                if (!monster[currentMap][i].alive) {
                    monster[currentMap][i].checkDrop();
                    monster[currentMap][i] = null;
                }
            }
        }

        if (gameState == pauseState) {
        }
    }

    //Desenează componenta principală a jocului pe ecran, inclusiv placa de joc, jucătorul, NPC-urile și monștrii.
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        drawStart = System.nanoTime();

        if (gameState == titleState) {
            ui.draw(g2);
        } else {

            tileM.draw(g2);

            entityList.add(player);

            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null)
                    entityList.add(npc[currentMap][i]);
            }

            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null)
                    entityList.add(obj[currentMap][i]);
            }

            for (int i = 0; i < monster[1].length; i++)
                if (monster[currentMap][i] != null)
                    entityList.add(monster[currentMap][i]);

            entityList.sort(new Comparator<>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return 0;
                }
            });

            for (Entity entity : entityList)
                entity.draw(g2);

            entityList.clear();

            ui.draw(g2);

            if(keyH.showDebugtext) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;

                g2.setFont(new Font("Arial", Font.PLAIN,20));
                g2.setColor(Color.white);

                int x = 10;
                int y = 400;
                int lineHeight = 20;

                g2.drawString("WorldX: " + player.worldX, x, y); y += lineHeight;
                g2.drawString("WorldY: " + player.worldY, x, y); y += lineHeight;
                g2.drawString("Col: " + (player.worldX + player.solidArea.x)/tileSize, x, y); y += lineHeight;
                g2.drawString("Row: " + (player.worldY + player.solidArea.y)/tileSize, x, y); y += lineHeight;

                g2.drawString("Draw Time: " + passed, x, y);}


        }

        g2.dispose();

    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
