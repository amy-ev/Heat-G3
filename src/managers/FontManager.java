package managers;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class FontManager {

    private static FontManager instance = null;

    // prevent instantiation
    protected FontManager() {
    }


    public static FontManager getInstance() {
        if (instance == null)
            instance = new FontManager();
        return instance;
    }

    public void setFonts(JMenuItem... args){
        for (JMenuItem arg : args) {
            arg.setFont((new Font("", Font.PLAIN, 30)));
        }
    }
}
