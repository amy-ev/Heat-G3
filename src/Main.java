/**
 *
 * Copyright (c) 2005 University of Kent
 * Computing Laboratory, Canterbury, Kent, CT2 7NP, U.K
 *
 * This software is the confidential and proprietary information of the
 * Computing Laboratory of the University of Kent ("Confidential Information").
 * You shall not disclose such confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with
 * the University.
 *
 */

//import com.incors.plaf.alloy.AlloyLookAndFeel;

import managers.*;
import view.windows.OptionsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;
import java.io.File;

/**
 * Main HEAT class
 */
public class Main {

  /**
   * Used to run HEAT
   * @param args
   */
public static void main(String[] args) {
    Logger log = Logger.getLogger("heat");
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set look and feel
        log.setUseParentHandlers(false);  // turn off logging on stdout console
        FileHandler handler = new FileHandler(System.getProperty("user.home") + File.separator + "heat.log");
        handler.setFormatter(new SimpleFormatter());
        log.addHandler(handler);
    } catch (Exception e) {
        System.out.println("Could not install file handler for logging.");
    }

    System.setProperty("com.apple.mrj.application.apple.menu.about.name", ""); // set name of main menu on Mac
    System.setProperty("apple.laf.useScreenMenuBar", "true");  // on Mac separate menu from window

    // Show splash image
    showSplash();

    // Create managers
    SettingsManager sm = SettingsManager.getInstance();
    WindowManager wm = WindowManager.getInstance();
    SplashScreenManager splashScreen = SplashScreenManager.getInstance();

    // while splash screen is still open do NOT start the program
    //
    while(!splashScreen.owIsActive()){
        while (splashScreen.isActive()){
            ;
        }
    }

    sm.loadSettings();
    WindowManager.setLookAndFeel();
    wm.createGUI();


    if (sm.isNewSettingsFile())
      wm.showWizardWindow();
      // will also start interpreter process
    else {
      // FileManager fm = FileManager.getInstance();
      // fm.saveTemporary();

      InterpreterManager im = InterpreterManager.getInstance();
      im.startProcess(false);
    }

    if (args.length > 0) {
    	wm.openFile(new java.io.File(args[0]));
        wm.showAll();
        /* Make edit area active */
        wm.getEditorWindow().grabFocus();
    } else {
        wm.getEditorWindow().setEditorContent("Use menu to open an existing or create a new program in the editor.");
        wm.setCloseEnabled(false);
        UndoManager.getInstance().reset();
        wm.onlyConsole();
        wm.getConsoleWindow().getFocus();
    }
    wm.setVisible();
   }

    /**
     * Shows the splash image on load
     */
    public static void showSplash() {
        JWindow splashWindow = new JWindow();
        splashWindow.getContentPane().add(
                new JLabel(new ImageIcon("src/icons/splash.png")),
                BorderLayout.CENTER
        );
        splashWindow.setSize(420, 300);
        splashWindow.setLocationRelativeTo(null);
        splashWindow.setVisible(true);

        // Keep the splash screen visible for 1.2 secs
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        splashWindow.setVisible(false);
        splashWindow.dispose();
    }
}

