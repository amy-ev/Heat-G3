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
 * @author Dean Ashton
 *
 */

package view.windows;

import managers.ActionManager;
import managers.FontManager;
import managers.SettingsManager;
import managers.OverlayManager;
import managers.SettingsManager;
import managers.WindowManager;

import utils.Settings;
import view.dialogs.FileDialogs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;


public class WizardWindow {
  private JPanel panelOptions;
  private JTextField jTextFieldInterpreterPath;
  private SettingsManager sm = SettingsManager.getInstance();

  private int fontSize = 12;
  private static Logger log = Logger.getLogger("heat");
 
  private JDialog dialog;
  
  private WindowManager wm = WindowManager.getInstance();
  private FontManager fm = FontManager.getInstance();

  // global variables to adjust font
  private JButton browse = new JButton("Browse");
  private JButton buttonApply = new JButton("Apply");
  private JLabel interpreterPathLabel= new JLabel("Full path of the Haskell interpreter ghci (not ghc or winghci!): ");

  public WizardWindow() {
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    // panel for interpreter options:
    JPanel panelInterpreter = new JPanel(new GridLayout(0,1));

    browse.setToolTipText("Browse for file");
    browse.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          interpreterPath_actionPerformed();
        }
      });
    JPanel panelInterpreterPath = new JPanel();
    panelInterpreterPath.add(interpreterPathLabel);
    panelInterpreterPath.add(browse);
    panelInterpreter.add(panelInterpreterPath);
    jTextFieldInterpreterPath = new JTextField();
    panelInterpreter.add(jTextFieldInterpreterPath);
    panelInterpreter.add(new JSeparator(SwingConstants.HORIZONTAL));
  
    // buttons for applying options and cancellation

    buttonApply.setAction(ActionManager.getInstance().getSaveWizardAction());
      JPanel panelButtons = new JPanel();
    panelButtons.add(buttonApply);
    
    // put all together
    panelOptions = new JPanel(new BorderLayout());
    panelOptions.add(panelInterpreter,BorderLayout.CENTER);
    panelOptions.add(panelButtons,BorderLayout.PAGE_END);

    String fontSizeStr = sm.getSetting(Settings.GLOBAL_FONT_SIZE);


    if ((fontSizeStr != null) && (fontSizeStr != "")) {
      try {
        int size = Integer.parseInt(fontSizeStr);
        fontSize = size;
        setFontSize(fontSize);
      } catch (NumberFormatException nfe) {
        log.warning("[DisplayWindow] - Failed to parse " +
                Settings.GLOBAL_FONT_SIZE + " setting, check value in settings file");
      }
    }

  }

  public void setFontSize(int ptSize){
    Font font = new Font("Arial", Font.PLAIN, ptSize);
    fm.setButtonsFont(font, browse, buttonApply);
    fm.setLabelsFont(font, interpreterPathLabel);
  }

  private void interpreterPath_actionPerformed() {
    File selectedFile = FileDialogs.getInstance().showDefaultFileChooser(
            new File(jTextFieldInterpreterPath.getText()));

    if (selectedFile != null) {
      jTextFieldInterpreterPath.setText(selectedFile.getAbsolutePath());
    }
  }

  public String getInterpreterPath() {
    return jTextFieldInterpreterPath.getText();
  }

  public void show() {
    dialog = new JDialog(wm.getMainScreenFrame(), "Initial Setup");
    dialog.setModal(true);
    dialog.getContentPane().add(panelOptions);
    dialog.setMinimumSize(new Dimension(500,200));
    dialog.setSize(400,200);
    dialog.pack();
    dialog.setLocationRelativeTo(wm.getMainScreenFrame());

    // OVERLAY SETTINGS
    SettingsManager sm = SettingsManager.getInstance();
    String displayOverlay = sm.getSetting(Settings.OVERLAY_DISPLAY);
    updateDisplayOverlayToggle(displayOverlay);

    dialog.setVisible(true);
  }

  public void updateDisplayOverlayToggle(String toggle) {
    // Call the OverlayManager and apply an overlay if setting is true
    OverlayManager om = OverlayManager.getInstance();
    om.addPanelOverlay(dialog, panelOptions, toggle);
    dialog.pack();
    dialog.repaint();
  }

  public boolean isOpen() {
    return dialog != null && dialog.isShowing();
  }

  public void close() {
    if (dialog != null)
      dialog.dispose();
  }
}
