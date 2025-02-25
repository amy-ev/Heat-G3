package managers;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AudioManager {

    private static AudioManager instance = null;

    AudioInputStream audioInputStream;
    Clip clip;

    // prevent instantiation
    protected AudioManager(){
    }

    // only create an audio manager if one doesnt already exist
    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();

        return instance;
    }
    // play selected audio
    public void play(String filePath) {
        try{
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.addLineListener(new LineListener() {
                public void update(LineEvent event){
                    if (event.getType() == LineEvent.Type.STOP){
                        clip.stop();
                    }
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        //Scanner scanner = new Scanner(System.in);
    }

    public void stop(String filePath){
        clip.stop();
        clip.close();
    }
}
