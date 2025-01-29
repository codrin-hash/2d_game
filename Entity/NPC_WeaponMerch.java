package Entity;

import Main.GamePanel;

//Reprezintă un NPC
public class NPC_WeaponMerch extends Entity{

    public NPC_WeaponMerch(GamePanel gp){

        super(gp);
        direction = "up";

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/npc/WeaponMerch",gp.tileSize,gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "Hello!";
        dialogues[1] = "I need your help, I'm desperate.";
        dialogues[2] = "What can I do for you?";
        dialogues[3] = "I need some equipment to bring back the ring\nthat was stolen from me!";
        dialogues[4] = "There is a legend about a very scary forest...\nIf you manage to pass through it safely, you will get to a waraxe.";
        dialogues[5] = "I'll do it!";
        dialogues[6] = "Be safe kid..";
    }

    public void speak() {

        if(dialogues[dialogueIndex] == null)
            dialogueIndex = 0;
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

    }
}
