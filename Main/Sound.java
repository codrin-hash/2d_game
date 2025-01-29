package Main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

//Gestionarea sunetului în joc, incluzând redarea, oprirea, bucla și setarea fișierelor audio.
public class Sound {

    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound(){
        soundURL[0] = getClass().getResource("/sound/sound_joc.wav");
        soundURL[1] = getClass().getResource("/sound/sound_death.wav");
        soundURL[2] = getClass().getResource("/sound/sound_mob_death.wav");
        soundURL[3] = getClass().getResource("/sound/sound_player_hit.wav");
        soundURL[4] = getClass().getResource("/sound/sound_pickup.wav");
        soundURL[5] = getClass().getResource("/sound/sound_swing.wav");
    }

    public void setFile(int i){

        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}

