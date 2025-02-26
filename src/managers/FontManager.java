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
            Font f = new Font("sans-serif", Font.PLAIN, 30);
            arg.setFont(f);
        }
    }
}
