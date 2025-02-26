package utils.jsyntax;

/*
 * SyntaxUtilities.java - Utility functions used by syntax colorizing Copyright
 * (C) 1999 Slava Pestov You may use and modify this package for any purpose.
 * Redistribution is permitted, in both source and binary form, provided that
 * this notice remains intact in all source distributions of this package.
 */

import managers.WindowManager;
import utils.jsyntax.tokenmarker.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.Map;
/**
 * Class with several utility functions used by jEdit's syntax colorizing
 * subsystem.
 * 
 * @author Slava Pestov
 * @version $Id: SyntaxUtilities.java,v 1.9 1999/12/13 03:40:30 sp Exp $
 */
public class SyntaxUtilities {
  private static Map<String, Color> currentTheme = SyntaxColourBlindTheme.DEFAULT_THEME;
  /**
   * Checks if a subregion of a <code>Segment</code> is equal to a string.
   * 
   * @param ignoreCase
   * True if case should be ignored, false otherwise
   * @param text
   * The segment
   * @param offset
   * The offset into the segment
   * @param match
   * The string to match
   */
  public static boolean regionMatches(boolean ignoreCase, Segment text,
      int offset, String match) {
    int length = offset + match.length();
    char[] textArray = text.array;
    if (length > text.offset + text.count)
      return false;
    for (int i = offset, j = 0; i < length; i++, j++) {
      char c1 = textArray[i];
      char c2 = match.charAt(j);
      if (ignoreCase) {
        c1 = Character.toUpperCase(c1);
        c2 = Character.toUpperCase(c2);
      }
      if (c1 != c2)
        return false;
    }
    return true;
  }

  /**
   * Checks if a subregion of a <code>Segment</code> is equal to a character
   * array.
   * 
   * @param ignoreCase
   * True if case should be ignored, false otherwise
   * @param text
   * The segment
   * @param offset
   * The offset into the segment
   * @param match
   * The character array to match
   */
  public static boolean regionMatches(boolean ignoreCase, Segment text,
      int offset, char[] match) {
    int length = offset + match.length;
    char[] textArray = text.array;
    if (length > text.offset + text.count)
      return false;
    for (int i = offset, j = 0; i < length; i++, j++) {
      char c1 = textArray[i];
      char c2 = match[j];
      if (ignoreCase) {
        c1 = Character.toUpperCase(c1);
        c2 = Character.toUpperCase(c2);
      }
      if (c1 != c2)
        return false;
    }
    return true;
  }

  /**
   * Returns the default style table. This can be passed to the
   * <code>setStyles()</code> method of <code>SyntaxDocument</code> to use
   * the default syntax styles.
   */
  public static SyntaxStyle[] getDefaultSyntaxStyles() {
    SyntaxStyle[] styles = new SyntaxStyle[Token.ID_COUNT];
    /* Comments */
    styles[Token.COMMENT1] = new SyntaxStyle(getSyntaxColor("comment"), false, false);
    styles[Token.COMMENT2] = new SyntaxStyle(getSyntaxColor("comment"), false, false);
    /* Keywords */
    styles[Token.KEYWORD1] = new SyntaxStyle(getSyntaxColor("keyword"), false, true);
    styles[Token.KEYWORD2] = new SyntaxStyle(getSyntaxColor("keyword"), false, false);
    styles[Token.KEYWORD3] = new SyntaxStyle(getSyntaxColor("keyword"), false, true);
    /* Literal which include string and number highlight */
    styles[Token.LITERAL1] = new SyntaxStyle(getSyntaxColor("string"), false, false);
    styles[Token.LITERAL2] = new SyntaxStyle(getSyntaxColor("string"), false, true);
    /* Label */
    styles[Token.LABEL] = new SyntaxStyle(getSyntaxColor("label"), false, true);
    /* Operator which include the Operator like +,- */
    styles[Token.OPERATOR] = new SyntaxStyle(getSyntaxColor("operator"), false, false);
    /* Invalid include errors */
    styles[Token.INVALID] = new SyntaxStyle(getSyntaxColor("invalid"), false, false);

    return styles;
  }

  /**
   * Paints the specified line onto the graphics context. Note that this method
   * munges the offset and count values of the segment.
   * 
   * @param line
   * The line segment
   * @param tokens
   * The token list for the line
   * @param styles
   * The syntax style list
   * @param expander
   * The tab expander used to determine tab stops. May be null
   * @param gfx
   * The graphics context
   * @param x
   * The x co-ordinate
   * @param y
   * The y co-ordinate
   * @return The x co-ordinate, plus the width of the painted string
   */
  public static int paintSyntaxLine(Segment line, Token tokens,
      SyntaxStyle[] styles, TabExpander expander, Graphics gfx, int x, int y) {
    Font defaultFont = gfx.getFont();
    Color defaultColor = gfx.getColor();

    int offset = 0;
    for (;;) {
      byte id = tokens.id;
      if (id == Token.END)
        break;

      int length = tokens.length;
      if (id == Token.NULL) {
        if (!defaultColor.equals(gfx.getColor()))
          gfx.setColor(defaultColor);
        if (!defaultFont.equals(gfx.getFont()))
          gfx.setFont(defaultFont);
      } else {
        //
        styles[id] = new SyntaxStyle(getSyntaxColor(tokens.id + ""), false, false);
        styles[id].setGraphicsFlags(gfx, defaultFont);
      }
      line.count = length;
      x = Utilities.drawTabbedText(line, x, y, gfx, expander, 0);
      line.offset += length;
      offset += length;

      tokens = tokens.next;
    }

    return x;
  }

  public static void setSyntaxColor(String type, Color color) {
      currentTheme.put(type, color);
  }

  /*
    This method will retrieve the color for the given color types (keyword, string, etc)
   */
  public static Color getSyntaxColor(String type) {
    return currentTheme.getOrDefault(type, Color.black);
  }

  public static void applyTheme(String themeType){

    System.out.print("Applying theme " + themeType + "\n"); //this line use for debugging
    currentTheme = SyntaxColourBlindTheme.getTheme(themeType);

    //
    for (Map.Entry<String, Color> entry : currentTheme.entrySet()) {
      System.out.println("  - " + entry.getKey() + " -> " + entry.getValue());
      setSyntaxColor(entry.getKey(), entry.getValue());
    }

    refreshEditor();
  }


  public static void refreshEditor(){
    System.out.println("Refreshing Editor...");

    for (Window window : Window.getWindows()) {
      SwingUtilities.updateComponentTreeUI(window);
      window.repaint(); //Ensure UI is fully repainted
    }

  }



  // private members
  private SyntaxUtilities() {
  }


}