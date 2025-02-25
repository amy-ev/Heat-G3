package managers;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

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
    public void play(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }
}
