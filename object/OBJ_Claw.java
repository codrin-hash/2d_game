package object;

import Entity.Entity;
import Main.GamePanel;

//O clasă care reprezintă un obiect
public class OBJ_Claw extends Entity {

    GamePanel gp;

    public OBJ_Claw(GamePanel gp) {
        super(gp);
        this.gp = gp;
        value = 1;

        type = type_pickupOnly;
        name = "Wolf Claw";
        down1 = setup("/objects/wolf_claw", gp.tileSize, gp.tileSize);
    }

    public void use(Entity entity) {

        gp.playSE(1);
        gp.player.claw += value;
    }

}
