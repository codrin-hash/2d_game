package object;

import Main.GamePanel;
import Entity.Entity;

//O clasă care reprezintă un obiect
public class OBJ_Ring extends Entity{

    GamePanel gp;
    public OBJ_Ring(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "axe";
        down1 = setup("/objects/ring",gp.tileSize,gp.tileSize);
    }

}
