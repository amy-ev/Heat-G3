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
 * @author Dean Ashton, John Travers
 *
 */

package view.toolbars;

import managers.ActionManager;
import managers.FontManager;
import managers.SettingsManager;
import utils.Settings;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.*;
import java.util.logging.Logger;

// import com.apple.eawt.Application;




/**
 * The menus used within HEAT
 */
public class MainMenu {
    // MAYBE FIX
  private JMenuBar jMenuBar = new JMenuBar() {
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (getParent() != null) {
        getParent().repaint(); // Forces GlassPane to repaint when hovering over menu items
      }
    }
  };
  //private JMenuBar jMenuBar = new JMenuBar();
  private FontManager fm = FontManager.getInstance();
  private SettingsManager sm = SettingsManager.getInstance();

  private int fontSize = 12;
  private static Logger log = Logger.getLogger("heat");
  /* Program menu items */
  private JMenu jMenuFile = new JMenu();
  private JMenuItem jMenuItemOpen = new JMenuItem();
  private JMenuItem jMenuItemCloseFile = new JMenuItem();
  private JMenuItem jMenuItemPrint = new JMenuItem();
  private JMenuItem jMenuItemOptions = new JMenuItem();
  private JMenuItem jMenuItemExit = new JMenuItem();

  /* Edit menu items */
  private JMenu jMenuEdit = new JMenu();

  // converted undo and redo abstract action objects into jMenuItems for font size adjustments.
  private JMenuItem jMenuItemUndo = new JMenuItem();
  private JMenuItem jMenuItemRedo = new JMenuItem();

  private JMenuItem jMenuItemCopy = new JMenuItem();
  private JMenuItem jMenuItemCut = new JMenuItem();
  private JMenuItem jMenuItemPaste = new JMenuItem();

  /* Run menu items */
  private JMenu jMenuRun = new JMenu();
  private JMenuItem jMenuItemCompile = new JMenuItem();
  private JMenuItem jMenuItemInterrupt = new JMenuItem();
  private JMenuItem jMenuItemTest = new JMenuItem();

  /* Help menu items */
  private JMenu jMenuHelp = new JMenu();
  private JMenuItem jMenuItemContents = new JMenuItem();
  private JMenuItem jMenuItemAbout = new JMenuItem();

  private ActionManager.UndoAction undoAction = ActionManager.getInstance()
                                                             .getUndoAction();
  private ActionManager.RedoAction redoAction = ActionManager.getInstance()
                                                             .getRedoAction();
  private JMenuItem jMenuItemSearch = new JMenuItem();

  /**
   * Creates a new MainMenu object.
   */
  public MainMenu() {
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Initialises the menus GUI compenetes
   *
   * @throws Exception if swing components fail to instantiate
   */
  public void jbInit() throws Exception {
    ActionManager am = ActionManager.getInstance();
    SettingsManager sm = SettingsManager.getInstance();


    /* File Menu */
    jMenuFile.setText("Program");
    jMenuFile.setMnemonic('p');
    jMenuItemOpen.setAction(am.getOpenFileAction());
    // jMenuItemPrint.setText("Print");
    // jMenuItemPrint.setMnemonic('p');
    jMenuItemCloseFile.setAction(am.getCloseFileAction());
    jMenuItemCloseFile.setEnabled(false);
    // jMenuItemPrint.setAction(am.getPrintFileAction());
    jMenuItemOptions.setAction(am.getShowOptionsAction());
    jMenuItemOptions.setMnemonic('o');
    jMenuItemExit.setText("Quit");
    jMenuItemExit.setMnemonic('Q');
    jMenuItemExit.setAction(am.getExitProgramAction());
    jMenuFile.add(jMenuItemOpen);
    jMenuFile.add(jMenuItemCloseFile);
    jMenuFile.addSeparator();
    // printing is too buggy; not essential, hence better exclude
    // jMenuFile.add(jMenuItemPrint);
    // jMenuFile.addSeparator();
    jMenuFile.add(jMenuItemOptions);
    jMenuFile.addSeparator();
    jMenuFile.add(jMenuItemExit);

    /* Edit Menu */
    jMenuEdit.setText("Edit");
    // changed the undoAction into a jMenuItem and attached its action to allow for font size adjustment
    jMenuItemUndo.setAction(undoAction);
    jMenuItemRedo.setAction(redoAction);

    jMenuEdit.addSeparator();
    jMenuEdit.setMnemonic('e');
    jMenuItemCut.setText("Cut");
    jMenuItemCut.setAction(am.getEditCutAction());
    jMenuItemCut.setMnemonic('t');
    jMenuItemCopy.setText("Copy");
    jMenuItemCopy.setAction(am.getEditCopyAction());
    jMenuItemCopy.setMnemonic('c');
    jMenuItemPaste.setText("Paste");
    jMenuItemPaste.setAction(am.getEditPasteAction());
    jMenuItemPaste.setMnemonic('p');
    jMenuItemSearch.setText("Find");
    jMenuItemSearch.setAction(am.getSearchAction());
    jMenuItemPaste.setMnemonic('f');
    jMenuEdit.add(jMenuItemUndo);
    jMenuEdit.add(jMenuItemRedo);
    jMenuEdit.add(jMenuItemCut);
    jMenuEdit.add(jMenuItemCopy);
    jMenuEdit.add(jMenuItemPaste);
    jMenuEdit.add(jMenuItemSearch);

    /* Run Menu */
    jMenuRun.setText("Run");
    jMenuRun.setMnemonic('r');
    jMenuItemCompile.setAction(am.getCompileAction());
    jMenuItemCompile.setText("Load & Compile");
    //jMenuItemCompile.setMnemonic('c');
    jMenuItemInterrupt.setAction(am.getInterruptAction());
    jMenuItemInterrupt.setText("Interrupt");
    //jMenuItemInterrupt.setMnemonic('i');
    jMenuItemTest.setAction(am.getTestAction());
    jMenuItemTest.setText("Test");
    //jMenuItemTest.setMnemonic('t');
    jMenuRun.add(jMenuItemCompile);
    jMenuRun.add(jMenuItemInterrupt);
    jMenuRun.add(jMenuItemTest);

    /* Help Menu */
    jMenuHelp.setText("Help");
    jMenuHelp.setMnemonic('H');
    jMenuItemContents.setAction(am.getShowHelpAction());
    jMenuItemAbout.setText("About");
    jMenuItemAbout.setAction(am.getShowAboutAction());
    jMenuItemAbout.setMnemonic('a');
    jMenuHelp.add(jMenuItemContents);
    jMenuHelp.addSeparator();
    jMenuHelp.add(jMenuItemAbout);

    /* Main Bar */
    jMenuBar.add(jMenuFile);
    jMenuBar.add(jMenuEdit);
    jMenuBar.add(jMenuRun);
    jMenuBar.add(jMenuHelp);

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

    // Mac specific stuff
    // Application app = Application.getApplication();
    // app.setAboutHandler(null);
    // app.setPreferencesHandler(null);
    // app.setQuitHandler(null);
  }

  // Set font size for MainMenu items
  public void setFontSize(int ptSize) {
    Font font = new Font("Arial", Font.PLAIN, ptSize);
    fm.setJMenuFont(font, jMenuFile, jMenuItemOpen, jMenuItemCloseFile, jMenuItemOptions, jMenuItemExit);
    fm.setJMenuFont(font, jMenuEdit, jMenuItemUndo, jMenuItemRedo, jMenuItemCut, jMenuItemCopy, jMenuItemPaste, jMenuItemSearch);
    fm.setJMenuFont(font, jMenuRun, jMenuItemCompile, jMenuItemInterrupt, jMenuItemTest);
    fm.setJMenuFont(font,jMenuHelp, jMenuHelp, jMenuItemContents, jMenuItemAbout);
    //jMenuFile.repaint();
  }

  /**
   * Returns the {@link JMenuBar} 
   *
   * @return the JMenuBar itself
   */
  public JMenuBar getToolBar() {
    return jMenuBar;
  }

  /**
   * Updates undo and redo command states
   */
  public void updateUndoRedo() {
    undoAction.updateUndoState();
    redoAction.updateRedoState();
    ActionManager am = ActionManager.getInstance();
    am.getToolbarUndoAction().updateUndoState();
    am.getToolbarRedoAction().updateRedoState();
  }

  /**
   * Enables or disables the close file command
   *
   * @param enabled the desired state of the close command
   */
  public void setCloseEnabled(boolean enabled) {
    jMenuItemCloseFile.setEnabled(enabled);
  }
  
    
  public void setInterruptEnabled(boolean enabled) {
    jMenuItemInterrupt.setEnabled(enabled);
  }
  
  public void setTestEnabled(boolean enabled) {
    jMenuItemTest.setEnabled(enabled);
  }
  
  public void setCompileEnabled(boolean enabled) {
    jMenuItemCompile.setEnabled(enabled);
  }
  

}
