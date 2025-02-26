package managers;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class FontManager {

    private static FontManager instance = null;
    public Font customFont = new Font("Arial", Font.PLAIN, 30);

    // prevent instantiation
    protected FontManager() {
    }

    public static FontManager getInstance() {
        if (instance == null)
            instance = new FontManager();
        return instance;
    }

    public void setJMenuFont(JMenuItem... args){
        for (JMenuItem arg : args) {
            arg.setFont(customFont);
        }
    }

    public void setToolBarFont(JButton... args){
        for (JButton arg : args) {
            arg.setFont(customFont);

        }
    }

}
