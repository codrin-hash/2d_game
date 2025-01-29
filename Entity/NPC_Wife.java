package Entity;

import Main.GamePanel;

//Reprezintă un NPC
public class NPC_Wife extends Entity {

    public NPC_Wife(GamePanel gp) {
        super(gp);

        direction = "right";

        setDialogue();
        getImage();
    }

    public void setDialogue() {
        dialogues[0] = "*crying*";
        dialogues[1] = "What happened?";
        dialogues[2] = "Someone stole my precious ring, please bring it back to me!";
        dialogues[3] = "I'll do so.";
    }

    public void getImage() {
        right1 = setup("/npc/wife",gp.tileSize,gp.tileSize);
    }

    public void speak() {

        if(dialogues[dialogueIndex] == null)
            dialogueIndex = 0;
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

    }

}
