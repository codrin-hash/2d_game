package object;

import Main.GamePanel;
import Entity.Entity;

//O clasă care reprezintă un obiect
public class OBJ_Hammer extends Entity{

    public OBJ_Hammer(GamePanel gp) {
        super(gp);
        name = "axe";
        down1 = setup("/objects/Hammer",gp.tileSize,gp.tileSize);
    }

}
