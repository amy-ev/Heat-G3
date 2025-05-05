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


package utils;

/**
 * Contains the static identifiers for the SettingsManager
 */
public class Settings {

  /**
   * The location of the desired Haskell interpreter 
   */
  public static final String INTERPRETER_PATH = "GHCI_PATH";
  
  /**
   * Command line options for the Haskell interpreter
   */
  
  public static final String INTERPRETER_OPTS = "GHCI_OPTS";

  /**
   * The location used to store temporary files
   */
  public static final String LIBRARY_PATH = "LIBRARY_PATH";

  /**
   * The font size used in the output window
   */
  public static final String OUTPUT_FONT_SIZE = "OUTPUT_FONT_SIZE";

  /**
   * The font size used in the display window
   */
  public static final String CODE_FONT_SIZE = "CODE_FONT_SIZE";

  /**
   * The font size used across UI
   */
  public static final String GLOBAL_FONT_SIZE = "GLOBAL_FONT_SIZE";

  /**
   * To determine if the visual disturbance overlay is on (Boolean)
   */
  public static final String OVERLAY_DISPLAY = "OVERLAY_DISPLAY";

  /**
   * The colours used for the visual disturbance overlay
   */
  public static final String OVERLAY_COLOUR = "OVERLAY_COLOUR";

  /*
   * For Boolean unit tests or QuickCheck
   */
  public static final String TEST_FUNCTION = "TEST_FUNCTION";
  
  public static final String TEST_POSITIVE = "TEST_POSITIVE";


  // SYNTAX SETTINGS
  public static final String SYNTAX_THEME = "SYNTAX_THEME";

  // AUDIO SETTINGS
  public static final String AUDIO_RESPONSE = "AUDIO_RESPONSE";

}
