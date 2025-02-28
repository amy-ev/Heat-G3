package view.windows;

import managers.*;
import utils.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class SplashTest {
    private static SplashTest instance = null;

    SettingsManager sm = SettingsManager.getInstance();

    JFrame splashScreen;
    JPanel splashPanel;

    private JComboBox jcbOutputFontSize;


    private JButton buttonApply = new JButton("Apply");
    private JButton buttonCancel = new JButton("Cancel");


    protected SplashTest(){
    }

    public static SplashTest getInstance(){
        if (instance == null){
            instance = new SplashTest();
        }
        return instance;
    }

    public void createSplashScreen(){
        if (splashScreen != null){
            splashScreen.setVisible(false);
        }
        splashScreen = new JFrame();
        splashScreen.setTitle("Splash Screen");
        splashScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        splashScreen.setLocationRelativeTo(null);
        splashScreen.setSize(500,500);

        splashPanel = new JPanel();
        splashPanel.setLayout(new GridLayout(2,1));

        jcbOutputFontSize = new JComboBox();

        for (int i = 10; i < 25; i++) {
            jcbOutputFontSize.addItem(String.valueOf(i));
        }
        splashPanel.add(jcbOutputFontSize);

        splashPanel.add(buttonApply);
        splashPanel.add(buttonCancel);

        buttonApply.setAction(ActionManager.getInstance().getSaveAccessibilityOptionsAction());

        splashScreen.add(splashPanel);
    }

    public void setVisible() {
        getProperties();

        splashScreen.setVisible(true);
    }


    public void getProperties() {
        jcbOutputFontSize.setSelectedItem(sm.getSetting(Settings.OUTPUT_FONT_SIZE));
    }

    public String getOuputFontSize() {
        return (String) jcbOutputFontSize.getSelectedItem();
    }
}
