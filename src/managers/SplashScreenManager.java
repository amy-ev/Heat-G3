package managers;

import view.windows.OptionsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreenManager {

    private static SplashScreenManager instance = null;
    OptionsWindow ow = new OptionsWindow();

    JFrame splashScreen = new JFrame("Splash Screen");
    public SplashScreenManager(){

        // yes button = close splash screen and show the options pane
        JButton yesButton = new JButton(new AbstractAction("Yes") {
            public void actionPerformed(ActionEvent e) {
                splashScreen.dispose();
                ow.show();
            }
        });

        JButton noButton = new JButton(new AbstractAction("No") {
            public void actionPerformed(ActionEvent e) {
                splashScreen.dispose();
                ow.is_visible = true;
            }
        });

        // splash screen layout
        splashScreen.setSize(420,300);
        splashScreen.setLocationRelativeTo(null);
        splashScreen.setLayout(new GridLayout());
        splashScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        splashScreen.add(yesButton);
        splashScreen.add(noButton);

        splashScreen.setVisible(true);
    }

    // create the splash screen if there is not already an instance
    public static SplashScreenManager getInstance() {
        if (instance == null)
            instance = new SplashScreenManager();

        return instance;
    }

    // identify where the jframe is active
    public boolean isActive(){
        return splashScreen.isActive();
    }

    // identify whether the options pane is active
    public boolean owIsActive(){
        return ow.is_visible;

    }
}

