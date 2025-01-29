package Entity;

import Main.GamePanel;
import Entity.Player;
//Reprezintă un NPC
public class NPC_Armorer extends Entity {

    public NPC_Armorer(GamePanel gp) {

        super(gp);
        direction = "left";

        getImage();
        setDialogue();
    }

    public void getImage() {
        left1 = setup("/npc/armorer", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
            dialogues[0] = "Hello Akaad!";
            dialogues[1] = "With respect, i need your help!";
            dialogues[2] = "Tell me son.. \nHow can i help you?";
            dialogues[3] = "I need some equipment to bring back the ring\nthat was stolen from me!";
            dialogues[4] = "I have some rusty equipment, but you will have to give me your wolf claws.\nYou will never make it like this though...";
            dialogues[5] = "What can I do?";
            dialogues[6] = "Alright, my very precious hammer was lost near the river.\nCan you get it back?\nYour work will be kindly appreciated.";
            dialogues[7] = "Yes sir.";
            dialogues[8] = "Thank you for the hammer. Your armour is ready now.";
            dialogues[9] = "Thanks old man!";

    }

    public void speak() {

            if (dialogues[dialogueIndex] == null)
                dialogueIndex = 0;
            gp.ui.currentDialogue = dialogues[dialogueIndex];
            dialogueIndex++;

    }
}
