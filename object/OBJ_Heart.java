package object;

import Main.GamePanel;
import Entity.Entity;

import javax.imageio.ImageIO;
import java.util.Objects;
//O clasă care reprezintă un obiect de tip inimă în joc, utilizat pentru a oferi viață sau sănătate jucătorului.
public class OBJ_Heart extends Entity{

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        name = "Heart";
        image = setup("/objects/heart_full",gp.tileSize,gp.tileSize);
        image2 = setup("/objects/heart_half",gp.tileSize,gp.tileSize);
        image3 = setup("/objects/heart_empty",gp.tileSize,gp.tileSize);

    }
}
