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
 * @author Chris Olive, Dean Ashton
 *
 */

package view.windows;

import managers.FontManager;
import managers.SettingsManager;
import managers.OverlayManager;
import managers.SettingsManager;
import managers.WindowManager;

import utils.LinkListener;
import utils.Settings;

import java.awt.*;
import java.util.logging.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;


public class AboutWindow {
  FontManager fm = FontManager.getInstance();
  private SettingsManager sm = SettingsManager.getInstance();

  private int fontSize = 12;

  private JPanel jpMain = new JPanel();
  private JPanel jPanel1 = new JPanel();
  private JLabel jlHeat = new JLabel();
  private FlowLayout flowLayout1 = new FlowLayout();
  private JLabel jLabel1 = new JLabel();
  private JDialog dialog;
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel2 = new JPanel();
  private JButton jbClose = new JButton();
  private FlowLayout flowLayout2 = new FlowLayout();
  private JEditorPane jEditorPane1 = new JEditorPane();

  private static Logger log = Logger.getLogger("heat");
  private static File localFile = new File("html/about.html");
  private java.net.URL htmURL;
  private String indexFile = "html/about.html";

  public void setFontSize(int ptSize){
    Font font = new Font("Arial", Font.PLAIN, ptSize);
    fm.setLabelsFont(font,jlHeat, jLabel1 );
    fm.setButtonsFont(font, jbClose);
  }
  public AboutWindow() {
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jpMain.setLayout(borderLayout1);
    jPanel1.setLayout(flowLayout1);
    jlHeat.setText("HEAT");
    jlHeat.setFont(new Font("Dialog", 1, 16));
    flowLayout1.setAlignment(0);
    jLabel1.setText("Version 5.04");
    jPanel2.setLayout(flowLayout2);
    jbClose.setText("Close");
    jbClose.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          jbClose_actionPerformed(e);
        }
      });
    jEditorPane1.setText("jEditorPane1");
    jEditorPane1.setEditable(false);
    try {
      htmURL = this.getClass().getClassLoader().getResource(indexFile);

      if (htmURL == null)
        log.warning("[TutorialWindow] - Resource not found:" + localFile.toString());
      else
        jEditorPane1.setPage(htmURL);
    } catch (Exception e) {
      jEditorPane1.setText("Error: Could not find html page: " +
        localFile.getAbsolutePath());
    }
    jEditorPane1.addHyperlinkListener(new LinkListener(jEditorPane1));
    jPanel1.add(jlHeat, null);
    jPanel1.add(jLabel1, null);
    jpMain.add(jPanel1, BorderLayout.NORTH);
    jPanel2.add(jbClose, null);
    jpMain.add(jPanel2, BorderLayout.SOUTH);
    jpMain.add(jEditorPane1, BorderLayout.CENTER);

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

  public void show() {
    dialog = new JDialog(WindowManager.getInstance().getMainScreenFrame(),
        "About HEAT");
    dialog.setModal(true);
    dialog.getContentPane().add(jpMain);
    dialog.setSize(400, 400);
    dialog.setLocationRelativeTo(WindowManager.getInstance().getMainScreenFrame());

    SettingsManager sm = SettingsManager.getInstance();
//    String displayOverlay = sm.getSetting(Settings.OVERLAY_DISPLAY);
//    if (displayOverlay != null && displayOverlay != "Off") {
//      updateDisplayOverlayToggle(displayOverlay);
//    }

    String displayOverlay = sm.getSetting(Settings.OVERLAY_DISPLAY);
    updateDisplayOverlayToggle(displayOverlay);

    dialog.setVisible(true);
  }

  public void updateDisplayOverlayToggle(String toggle) {
    // Call the OverlayManager and apply an overlay if setting is true
    OverlayManager om = OverlayManager.getInstance();
    om.addPanelOverlay(dialog, jpMain, toggle);
    dialog.pack();
    dialog.repaint();
  }

  public boolean isOpen() {
    return dialog != null && dialog.isShowing();
  }


  private void jbClose_actionPerformed(ActionEvent e) {
    dialog.dispose();
  }
}
