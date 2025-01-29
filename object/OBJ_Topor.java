package object;

import Main.GamePanel;
import Entity.Entity;

import javax.imageio.ImageIO;
import java.util.Objects;

//O clasă care reprezintă un obiect
public class OBJ_Topor extends Entity {

    public OBJ_Topor(GamePanel gp) {
        super(gp);
        name = "axe";
        down1 = setup("/objects/axe",gp.tileSize,gp.tileSize);
    }
}
