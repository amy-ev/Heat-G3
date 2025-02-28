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

import managers.*;

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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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
  private JComboBox jcbAudio;
  private JComboBox jcbSyntaxThemes;


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
  private JLabel audioLabel = new JLabel("Audio Response: ");
  private JLabel syntaxLabel = new JLabel("Syntax Settings: ");
  private JLabel overlayLabel = new JLabel("Overlay Settings: ");
  private JLabel overlayColourLabel = new JLabel("Overlay Colour Settings: ");

  JTabbedPane tabOptions = new JTabbedPane();

  private JDialog dialog;

  private SettingsManager sm = SettingsManager.getInstance();
  private WindowManager wm = WindowManager.getInstance();
  private AudioManager am = AudioManager.getInstance();
  private FontManager fm = FontManager.getInstance();
  //private SplashScreenManager ss = SplashScreenManager.getInstance();

  public boolean is_visible = false;


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

    JPanel panelAccessibility = new JPanel(new GridLayout(2,3));

    jcbGlobalFontSize = new JComboBox();
    jcbDisplayOverlayToggle = new JComboBox();
    jcbDisplayOverlayColour = new JComboBox();
    jcbAudio = new JComboBox();
    jcbSyntaxThemes = new JComboBox();

    panelAccessibility.setOpaque(false);
    panelAccessibility.setBorder(new EmptyBorder(20, 0, 20, 0));

    // Left Column Title with Description
    JLabel fontLabel = new JLabel("Font Settings");
    fontLabel.setFont(new Font("Arial", Font.BOLD, 28));

    JPanel fontPanel = new JPanel();
    fontPanel.setLayout(new BoxLayout(fontPanel, BoxLayout.Y_AXIS));
    fontPanel.setOpaque(false);
    fontPanel.setBorder(new EmptyBorder(0, 0, 5, 0));

    fontPanel.add(fontLabel);

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

    // AUDIO SETTINGS
    jcbAudio.addItem("Off");
    jcbAudio.addItem("On");

    //SYNTAX SETTINGS
    jcbSyntaxThemes.addItem("PROTANOPIA_THEME");
    jcbSyntaxThemes.addItem("DEUTERANOPIA_THEME");
    jcbSyntaxThemes.addItem("TRITANOPIA_THEME");
    jcbSyntaxThemes.addItem("DEFAULT_THEME");

    JPanel editorFontSize = new JPanel(new GridLayout(0,1)){
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
      }
    };
    editorFontSize.setOpaque(false);
    editorFontSize.setBackground(Color.WHITE);
    editorFontSize.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1, true),
            new EmptyBorder(16, 20, 16, 20)
    ));



    // ICON
    JLabel codeIcon = new JLabel(new ImageIcon("src/icons/accessibility_icons/editor-fs.png"));
    editorFontSize.add(codeIcon);

    editorFontSize.add(editorFontSizeLabel);

    editorFontSize.add(jcbCodeFontSize);

    JPanel interpreterFontSize = new JPanel(new GridLayout(0,1)){
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
      }
    };
    interpreterFontSize.setOpaque(false);
    interpreterFontSize.setBackground(Color.WHITE);
    interpreterFontSize.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1, true),
            new EmptyBorder(16, 20, 16, 20)
    ));


    // ICON
    JLabel outputIcon = new JLabel(new ImageIcon("src/icons/accessibility_icons/inter-fs.png"));

    interpreterFontSize.add(outputIcon);

    interpreterFontSize.add(interpreterFontSizeLabel);

    // Component - Toggle Button | Dropdown
    interpreterFontSize.add(jcbOutputFontSize);

    // Apply global size for components
    //component.setFont(new Font("Arial", Font.BOLD, 24));

    // GLOBAL FONT SETTINGS

    JPanel globalFontSize = new JPanel(new GridLayout(0,1)){
        @Override
        protected void paintComponent(Graphics g) {
          super.paintComponent(g);
          Graphics2D g2d = (Graphics2D) g;
          g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
          g2d.setColor(getBackground());
          g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        }
      };
    globalFontSize.setOpaque(false);
    globalFontSize.setBackground(Color.WHITE);
    globalFontSize.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1, true),
            new EmptyBorder(16, 20, 16, 20)
    ));


    // ICON
    JLabel globalFontIcon = new JLabel(new ImageIcon("src/icons/accessibility_icons/global-fs.png"));

    globalFontSize.add(globalFontIcon);

    globalFontSize.add(globalFontSizeLabel);

    // Component - Toggle Button | Dropdown
    globalFontSize.add(jcbGlobalFontSize);

    // Apply global size for components

    JLabel visualLabel = new JLabel("Visual Settings");
    visualLabel.setFont(new Font("Arial", Font.BOLD, 28));

    JPanel visualPanel = new JPanel();
    visualPanel.setLayout(new BoxLayout(visualPanel, BoxLayout.Y_AXIS));
    visualPanel.setOpaque(false);
    visualPanel.setBorder(new EmptyBorder(0, 0, 5, 0));

    visualPanel.add(visualLabel);

    // SYNTAX SETTINGS
    JPanel SyntaxThemeSelectionPanel = new JPanel(new GridLayout(0,1)){
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
      }
    };
    SyntaxThemeSelectionPanel.setOpaque(false);
    SyntaxThemeSelectionPanel.setBackground(Color.WHITE);
    SyntaxThemeSelectionPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1, true),
            new EmptyBorder(16, 20, 16, 20)
    ));


    // ICON
    JLabel syntaxIcon = new JLabel(new ImageIcon("src/icons/accessibility_icons/syntax-hl.png"));
    SyntaxThemeSelectionPanel.add(syntaxIcon);

    SyntaxThemeSelectionPanel.add(syntaxLabel);
    globalFontSize.add(jcbGlobalFontSize);

    //SyntaxThemeSelectionPanel.setLayout(new FlowLayout());
    SyntaxThemeSelectionPanel.add(jcbSyntaxThemes);

    // AUDIO SETTINGS
    JPanel audioPanel = new JPanel(new GridLayout(0,1)){
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
      }
    };
    audioPanel.setOpaque(false);
    audioPanel.setBackground(Color.WHITE);
    audioPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1, true),
            new EmptyBorder(16, 20, 16, 20)
    ));

    JLabel audioIcon = new JLabel(new ImageIcon("src/icons/accessibility_icons/audio-resp.png"));
    audioPanel.add(audioIcon);
    audioPanel.add(audioLabel);
    audioPanel.add(jcbAudio);
    JPanel displayPanel = new JPanel(new GridLayout(0,1)){
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
      }
    };
    displayPanel.setOpaque(false);
    displayPanel.setBackground(Color.WHITE);
    displayPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1, true),
            new EmptyBorder(16, 20, 16, 20)
    ));

    // ICON
    JLabel displayIcon = new JLabel(new ImageIcon("src/icons/accessibility_icons/vs-filter.png"));

    displayPanel.add(displayIcon);

    // Label
    displayPanel.add(overlayLabel);

    // Component - Toggle Button | Dropdown

    displayPanel.add(jcbDisplayOverlayToggle);

    // DISPLAY
    JPanel displayPanelColour = new JPanel(new GridLayout(0,1)){
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
      }
    };
    displayPanelColour.setOpaque(false);
    displayPanelColour.setBackground(Color.WHITE);
    displayPanelColour.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1, true),
            new EmptyBorder(16, 20, 16, 20)
    ));

    // ICON
    JLabel displayColourIcon = new JLabel(new ImageIcon("src/icons/accessibility_icons/vs-filter.png"));
    displayPanelColour.add(displayColourIcon);

    // Label
    displayPanelColour.add(overlayColourLabel);

    // Component - Toggle Button | Dropdown

    displayPanelColour.add(jcbDisplayOverlayColour);

    panelAccessibility.add(editorFontSize);
    panelAccessibility.add(interpreterFontSize);
    panelAccessibility.add(globalFontSize);
    panelAccessibility.add(audioPanel);
    panelAccessibility.add(displayPanel);
    panelAccessibility.add(displayPanelColour);
    panelAccessibility.add(SyntaxThemeSelectionPanel);

    // combine panels on tabbed pane
    tabOptions.addTab("Accessibility Options", panelAccessibility);
    tabOptions.addTab("Haskell Interpreter", panelInterpreter);
    tabOptions.addTab("Property Tests", panelTest);
    //tabOptions.addTab("Font Sizes", panelFontSizes);
    
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
    fm.setLabelsFont(font, interpreterPathLabel, optionsInfoLabel, libraryPathLabel, testLabel, quickCheckLabel, testPositiveLabel1, testPositiveLabel2, editorFontSizeLabel, interpreterFontSizeLabel, globalFontSizeLabel, audioLabel, syntaxLabel, overlayLabel, overlayColourLabel);
    fm.setOptionTabsFont(font, tabOptions);
    fm.setComboBoxFont(font, jcbCodeFontSize, jcbOutputFontSize, jcbGlobalFontSize, jcbAudio, jcbSyntaxThemes, jcbDisplayOverlayColour,jcbDisplayOverlayToggle);
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
    // SYNTAX SETTINGS
    jcbSyntaxThemes.setSelectedItem(sm.getSetting(Settings.SYNTAX_THEME));
    // GLOBAL SETTINGS
    jcbGlobalFontSize.setSelectedItem(sm.getSetting(Settings.GLOBAL_FONT_SIZE));
    // OVERLAY SETTINGS
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


  public String getAudioResponse(){
    return (String) jcbAudio.getSelectedItem();
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
