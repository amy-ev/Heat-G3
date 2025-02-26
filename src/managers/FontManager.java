package managers;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import utils.Settings;


public class FontManager {
    private SettingsManager sm = SettingsManager.getInstance();

    private static FontManager instance = null;
    //private String cFont = sm.getSetting(Settings.GLOBAL_FONT_SIZE);

    public Font customFont = new Font("Arial", Font.PLAIN, 30);
    private int fontsize = Integer.parseInt(sm.getSetting(Settings.GLOBAL_FONT_SIZE));

    private Font cfont = new Font("Arial", Font.PLAIN, fontsize);

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
            arg.setFont(cfont);
        }
    }

    public void setToolBarFont(JButton... args){
        for (JButton arg : args) {
            arg.setFont(customFont);

        }
    }

}
