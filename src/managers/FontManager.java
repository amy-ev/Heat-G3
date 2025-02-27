package managers;

import javax.swing.*;
import java.awt.*;

import utils.Settings;


public class FontManager {
    private SettingsManager sm = SettingsManager.getInstance();

    private static FontManager instance = null;

    private int fontsize = Integer.parseInt(sm.getSetting(Settings.GLOBAL_FONT_SIZE));
    private Font cFont = new Font("Arial", Font.PLAIN, fontsize);
    // prevent instantiation
    protected FontManager() {
    }

    public static FontManager getInstance() {
        if (instance == null)
            instance = new FontManager();
        return instance;
    }

    public void setJMenuFont(Font cFont, JMenuItem... args){
        for (JMenuItem arg : args) {
            arg.setFont(cFont);
        }
    }

    public void setToolBarFont(Font cFont, JButton... args){
        for (JButton arg : args) {
            arg.setFont(cFont);

        }
    }
    public void setButtonsFont(Font cFont, JButton... args){
        for (JButton arg : args) {
            arg.setFont(cFont);
        }
    }

    public void setLabelsFont(Font cFont, JLabel... args) {
        for (JLabel arg : args) {
            arg.setFont(cFont);
        }
    }
    public void setOptionTabsFont(Font cFont, JTabbedPane... args){
        for (JTabbedPane arg : args) {
            arg.setFont(cFont);
        }
    }
    public void setComboBoxFont(Font cFont, JComboBox... args){
        for (JComboBox arg : args) {
            arg.setFont(cFont);

        }
    }

    public void setComponentFont(Font cFont, JComponent... args){
        for (JComponent arg : args) {
            arg.setFont(cFont);
        }
    }

}
