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
 * @author Olaf Chitil
 * 
 * Handles the Option Dialog for users to change several settings,
 * in particular information about the Haskell interpreter and font sizes.
 *
 */

package view.windows;

import managers.ActionManager;
import managers.OverlayManager;
import managers.FontManager;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.*;

import static utils.jsyntax.SyntaxUtilities.applyTheme;


/**
 * Window for altering HEATs settings
 */
public class OptionsWindow {

  private JPanel panelOptions;
  private JTextField jTextFieldInterpreterPath;
  private JTextField jTextFieldOptions;
  private JTextField jTextFieldLibraryPath;
  private JTextField jTextFieldTestFunction;
  private JTextField jTextFieldTestPositive;

  private JComboBox jcbOutputFontSize;
  private JComboBox jcbCodeFontSize;
  private JComboBox jcbGlobalFontSize; // global font settings
  private JComboBox jcbDisplayOverlayToggle;
  private JComboBox jcbDisplayOverlayColour;


  // buttons turned into global variables to adjust font
  private JButton buttonApply = new JButton("Apply");
  private JButton buttonCancel = new JButton("Cancel");
  private JButton browse = new JButton("Browse");
  private JButton browseL = new JButton("Browse");

  // lebels turned into global variables to adjust font
  private JLabel interpreterPathLabel = new JLabel("Full path of the Haskell interpreter ghci (not ghc or winghci!): ");
  private JLabel optionsInfoLabel = new JLabel("Command line options for the Haskell interpreter:");
  private JLabel libraryPathLabel = new JLabel("Directory for additional Haskell libraries: ");
  private JLabel testLabel = new JLabel("Test function applied to a test property");
  private JLabel quickCheckLabel = new JLabel("(e.g. \"quickCheck\" for QuickCheck or \"\" (nothing) for Boolean properties)");
  private JLabel testPositiveLabel1 = new JLabel("String that appears in successful test output");
  private JLabel testPositiveLabel2 = new JLabel("(e.g. \"+++ OK, passed\" for QuickCheck or \"True\" for Boolean properties)");
  private JLabel editorFontSizeLabel = new JLabel("Editor font size: ");
  private JLabel interpreterFontSizeLabel = new JLabel("Interpreter font size:");
  private JLabel globalFontSizeLabel = new JLabel("Global font size:");

  JTabbedPane tabOptions = new JTabbedPane();

  private JDialog dialog;
  private JComboBox jcbSyntaxThemes;



  private SettingsManager sm = SettingsManager.getInstance();
  private WindowManager wm = WindowManager.getInstance();

  public boolean is_visible = false;
  private FontManager fm = FontManager.getInstance();

  private int fontSize = 12;
  private static Logger log = Logger.getLogger("heat");
  
  /**
   * Creates a new OptionsWindow object.
   */
  public OptionsWindow() {
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
    // panelInterpreter.add(new JLabel("")); // some vertical space

    JPanel panelOptionsInfo = new JPanel();
    panelOptionsInfo.add(optionsInfoLabel);
    panelInterpreter.add(panelOptionsInfo);
    jTextFieldOptions = new JTextField();
    panelInterpreter.add(jTextFieldOptions);
    panelInterpreter.add(new JSeparator(SwingConstants.HORIZONTAL));
    // panelInterpreter.add(new JLabel("")); // some vertical space

    browseL.setToolTipText("Browse for directory");
    browseL.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          libraryPath_actionPerformed();
        }
      });
    JPanel panelLibraryPath = new JPanel();
    panelLibraryPath.add(libraryPathLabel);
    panelLibraryPath.add(browseL);
    panelInterpreter.add(panelLibraryPath);
    jTextFieldLibraryPath = new JTextField();
    panelInterpreter.add(jTextFieldLibraryPath);
    
    // panel for test settings
    JPanel panelTest = new JPanel(new GridLayout(0,1));
    JPanel testFunction = new JPanel(new GridLayout(0,1));
    testFunction.add(testLabel);
    testFunction.add(quickCheckLabel);
    jTextFieldTestFunction = new JTextField();
    testFunction.add(jTextFieldTestFunction);
    panelTest.add(testFunction);
    // panelTest.add(new JLabel(""));  // some vertical space
    panelTest.add(new JSeparator(SwingConstants.HORIZONTAL));
    JPanel testPositive = new JPanel(new GridLayout(0,1));
    testPositive.add(testPositiveLabel1);
    testPositive.add(testPositiveLabel2);
    jTextFieldTestPositive = new JTextField();
    testPositive.add(jTextFieldTestPositive);
    panelTest.add(testPositive);
    
    // panel for setting font sizes
    JPanel panelFontSizes = new JPanel(new GridLayout(0,1));
    jcbOutputFontSize = new JComboBox();
    jcbCodeFontSize = new JComboBox();
    jcbGlobalFontSize = new JComboBox();

    jcbDisplayOverlayToggle = new JComboBox();
    jcbDisplayOverlayColour = new JComboBox();

    /* Populate the font size combo boxes */
    for (int i = 10; i < 25; i++) {
      jcbOutputFontSize.addItem(String.valueOf(i));
      jcbCodeFontSize.addItem(String.valueOf(i));
      jcbGlobalFontSize.addItem(String.valueOf(i));
    }

    // Adds the toggle option for the visual disturbance overlay
    jcbDisplayOverlayToggle.addItem("On");
    jcbDisplayOverlayToggle.addItem("Off");

    // Adds the colour options for the visual disturbance overlay
    jcbDisplayOverlayColour.addItem("Red");
    jcbDisplayOverlayColour.addItem("Green");
    jcbDisplayOverlayColour.addItem("Blue");
    jcbDisplayOverlayColour.addItem("Yellow");
    jcbDisplayOverlayColour.addItem("Purple");


    JPanel editorFontSize = new JPanel();
    editorFontSize.add(editorFontSizeLabel);
    editorFontSize.add(jcbCodeFontSize);
    JPanel interpreterFontSize = new JPanel();
    interpreterFontSize.add(interpreterFontSizeLabel);
    interpreterFontSize.add(jcbOutputFontSize);
    JPanel globalFontSize = new JPanel();
    globalFontSize.add(globalFontSizeLabel);
    globalFontSize.add(jcbGlobalFontSize);

    JPanel SyntaxThemeSelectionPanel = new JPanel();
    SyntaxThemeSelectionPanel.setLayout(new FlowLayout());

    jcbSyntaxThemes = new JComboBox();
    jcbSyntaxThemes.addItem("PROTANOPIA_THEME");
    jcbSyntaxThemes.addItem("DEUTERANOPIA_THEME");
    jcbSyntaxThemes.addItem("TRITANOPIA_THEME");
    jcbSyntaxThemes.addItem("DEFAULT_THEME");
    SyntaxThemeSelectionPanel.add(jcbSyntaxThemes);

    panelFontSizes.add(SyntaxThemeSelectionPanel);
    panelFontSizes.add(editorFontSize);
    panelFontSizes.add(interpreterFontSize);
    panelFontSizes.add(globalFontSize);
    panelFontSizes.add(jcbDisplayOverlayToggle);
    panelFontSizes.add(jcbDisplayOverlayColour);


    // combine panels on tabbed pane

    tabOptions.addTab("Haskell Interpreter", panelInterpreter);
    tabOptions.addTab("Property Tests", panelTest);
    tabOptions.addTab("Font Sizes", panelFontSizes);
    
    // buttons for applying options and cancellation

    buttonApply.setAction(ActionManager.getInstance().getSaveOptionsAction());

    buttonCancel.setToolTipText("Close options dialog without applying any changes");
    buttonCancel.addActionListener(new ActionListener() {

        public void actionPerformed(ActionEvent e) {
          close();
        }
      });
    JPanel panelButtons = new JPanel();
    panelButtons.add(buttonApply);
    panelButtons.add(buttonCancel);
    
    // put all together
    panelOptions = new JPanel(new BorderLayout());
    panelOptions.add(tabOptions,BorderLayout.CENTER);
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

  public void setFontSize(int ptSize) {
    Font font = new Font("Arial", Font.PLAIN, ptSize);
    fm.setButtonsFont(font, buttonApply, buttonCancel, browse);
    fm.setLabelsFont(font, interpreterPathLabel, optionsInfoLabel, libraryPathLabel, testLabel, quickCheckLabel, testPositiveLabel1, testPositiveLabel2, editorFontSizeLabel, interpreterFontSizeLabel, globalFontSizeLabel);
    fm.setOptionTabsFont(font, tabOptions);
    fm.setComboBoxFont(font, jcbCodeFontSize, jcbOutputFontSize, jcbGlobalFontSize);
  }

  /**
   * Displays the options window
   */
  public void show() {
    getProperties();

    dialog = new JDialog(wm.getMainScreenFrame(), "Options");
    dialog.setModal(true);
    dialog.getContentPane().add(panelOptions);      //(jTabbedPane1);
    dialog.setMinimumSize(new Dimension(500,350));
    dialog.setSize(600, 400);
    dialog.pack();
    dialog.setLocationRelativeTo(wm.getMainScreenFrame());

    String displayOverlay = sm.getSetting(Settings.OVERLAY_DISPLAY);
    updateDisplayOverlayToggle(displayOverlay);

    dialog.setVisible(true);
    is_visible = true;
  }
  // This method updates the visual disturbance overlay based upon new user settings
  public void updateDisplayOverlayToggle(String toggle) {
    OverlayManager om = OverlayManager.getInstance();
    om.addPanelOverlay(dialog, panelOptions, toggle);
  }

  /**
   * Closes the options window
   */
  public void close() {
    if (dialog != null)
      dialog.dispose();
      //is_visible = false;
  }

  /**
   * Gets the properties from the properties file, and sets them in the display
   */
  public void getProperties() {
    jTextFieldInterpreterPath.setText(sm.getSetting(Settings.INTERPRETER_PATH));
    jTextFieldOptions.setText(sm.getSetting(Settings.INTERPRETER_OPTS));
    jTextFieldLibraryPath.setText(sm.getSetting(Settings.LIBRARY_PATH));
    jTextFieldTestFunction.setText(sm.getSetting(Settings.TEST_FUNCTION));
    jTextFieldTestPositive.setText(sm.getSetting(Settings.TEST_POSITIVE));
    jcbOutputFontSize.setSelectedItem(sm.getSetting(Settings.OUTPUT_FONT_SIZE));
    jcbCodeFontSize.setSelectedItem(sm.getSetting(Settings.CODE_FONT_SIZE));
    jcbSyntaxThemes.setSelectedItem(sm.getSetting(Settings.SYNTAX_THEME));
    // GLOBAL SETTINGS
    jcbGlobalFontSize.setSelectedItem(sm.getSetting(Settings.GLOBAL_FONT_SIZE));
    jcbDisplayOverlayToggle.setSelectedItem(sm.getSetting(Settings.OVERLAY_DISPLAY));
    jcbDisplayOverlayColour.setSelectedItem(sm.getSetting(Settings.OVERLAY_COLOUR));

  }

 
  /**
   * Returns the current Interpreter path
   *
   * @return the Interpreter path
   */
  public String getInterpreterPath() {
    return jTextFieldInterpreterPath.getText();
  }
  
  /**
   * Returns the Interpreter options
   *
   * @return the Interpreter options
   */
  public String getInterpreterOpts() {
    return jTextFieldOptions.getText();
  }

  /**
   * Returns the location for temporary files
   *
   * @return the location for temporary files
   */
  public String getLibraryPath() {
    return jTextFieldLibraryPath.getText();
  }
  
  public String getTestFunction() {
      return jTextFieldTestFunction.getText();
  }

  public String getTestPositive() {
      return jTextFieldTestPositive.getText();
  }
  
  /**
   * Returns the desired font size for output window
   *
   * @return the output window font size
   */
  public String getOuputFontSize() {
    return (String) jcbOutputFontSize.getSelectedItem();
  }

  /**
   * Returns the desired font size for display window
   *
   * @return the display window font size
   */
  public String getCodeFontSize() {
    return (String) jcbCodeFontSize.getSelectedItem();
  }

  /**
   * Returns the desired font size for global
   *
   * @return the window font size
   */

  public String getGlobalFontSize() { return (String) jcbGlobalFontSize.getSelectedItem();}



  public String getSyntaxTheme() {
      return (String) jcbSyntaxThemes.getSelectedItem();
  }

  public String getDisplayOverlayToggle() {
    return (String) jcbDisplayOverlayToggle.getSelectedItem();
  }

  
  public String getDisplayOverlayColour() {
    // Grabs the string from the combo box to do switch statement check
    String colour = (String) jcbDisplayOverlayColour.getSelectedItem();
    // Empty string to add colour value string to which is set as the setting in OVERLAY_COLOUR
    String chosenColour = "";
    // Switch case to assign OVERLAY_COLOUR based upon user selection
    switch (colour) {
      case "Red":
        chosenColour = "255,0,0,50";
        break;
      case "Green":
        chosenColour = "0,255,0,50";
        break;
      case "Blue":
        chosenColour = "0,0,255,50";
        break;
      case "Yellow":
        chosenColour = "255,255,0,50";
        break;
      case "Purple":
        chosenColour = "255,0,255,50";
        break;
    }
    return chosenColour;
  }


  private void jButton2_actionPerformed(ActionEvent e) {
    close();
  }

//  private void jbUpdate_actionPerformed(ActionEvent e) {
//    close();
//  }

  /**
   * Browse for an interpreter file with full path
   */
  private void interpreterPath_actionPerformed() {
    File selectedFile = FileDialogs.getInstance().showDefaultFileChooser(
            new File(jTextFieldInterpreterPath.getText()));

    if (selectedFile != null) {
      jTextFieldInterpreterPath.setText(selectedFile.getAbsolutePath());
    }
  }

  /**
   * Browse for a library path
   */
  private void libraryPath_actionPerformed() {
    File selectedFile = FileDialogs.getInstance().showDefaultDirChooser(
            new File(jTextFieldLibraryPath.getText()));

    if (selectedFile != null)
      jTextFieldLibraryPath.setText(selectedFile.getAbsolutePath());
  }

  // boolean used to check when the options window has been opened
  public boolean isVisible(){
    return is_visible;
  }
}

