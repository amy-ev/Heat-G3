package managers;

import javax.swing.*;

public class SplashScreenManager {

    private static SplashScreenManager instance = null;

    public SplashScreenManager(){
        JFrame splashScreen = new JFrame();
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");


        splashScreen.setSize(500,500);
        splashScreen.setLocationRelativeTo(null);
        //splashScreen.setLayout(new GridLayout());
        splashScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // TODO: set button dimensions
        splashScreen.add(yesButton);
        splashScreen.add(noButton);

//    yesButton.addActionListener(new ActionListener() {
//
//    });
        splashScreen.setVisible(true);
    }
    public static SplashScreenManager getInstance() {
        if (instance == null)
            instance = new SplashScreenManager();
        return instance;
    }



    // splash screen startup
    //TODO: correct this
    public boolean isActive(){
        return false;
    }


}
