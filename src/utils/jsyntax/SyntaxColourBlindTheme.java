package utils.jsyntax;

import java.awt.Color;
import java.util.Map;
import java.util.HashMap;
/*
    This class defines colour-blind-friendly themes.
    It supports 3 type of colour blindness:
    1. Protanopia people who have red colour blindness
    2. Deuteranopia people who have green colour blindness
    3. Tritanopia people who have blue colour blindness
    We will use shade of blue, orange, and purple as they will be easier to distinguish
    for colour-blind user
 */
public class SyntaxColourBlindTheme {
    public static final Map<String, Color> PROTANOPIA_THEME = new HashMap<>();
    public static final Map<String, Color> DEUTERANOPIA_THEME = new HashMap<>();
    public static final Map<String, Color> TRITANOPIA_THEME = new HashMap<>();
    public static final Map<String, Color> DEFAULT_THEME = new HashMap<>();

    static{
        //Default theme
        DEFAULT_THEME.put("keyword", new Color(0,0,255)); //Blue
        DEFAULT_THEME.put("string", new Color(153,76,0)); //Brown
        DEFAULT_THEME.put("comment", new Color(0, 102, 0)); //Green
        DEFAULT_THEME.put("label", new Color(128, 0, 128)); //Purple
        DEFAULT_THEME.put("operator", new Color(0,0,0)); //Black
        DEFAULT_THEME.put("invalid", new Color(255, 0,0)); //red

        // Protanopia theme
        PROTANOPIA_THEME.put("keyword", new Color(0,0,200)); //Dark blue
        PROTANOPIA_THEME.put("string", new Color(153,76,0)); //Dark brown
        PROTANOPIA_THEME.put("comment", new Color(0,128,128)); //Teal
        PROTANOPIA_THEME.put("label", new Color(80,0,0)); //Dark Purple
        PROTANOPIA_THEME.put("operator", new Color(50,50,50)); //Dark Gray
        PROTANOPIA_THEME.put("invalid", new Color(200,0,0)); //Dark red

        //Deuteranopia theme
        DEUTERANOPIA_THEME.put("keyword", new Color(0,0,100)); //Dark blue
        DEUTERANOPIA_THEME.put("string", new Color(120,60,0)); //Dark brown
        DEUTERANOPIA_THEME.put("comment", new Color(0,120,120)); //Teal
        DEUTERANOPIA_THEME.put("label", new Color(90,0,90)); //Dark purple
        DEUTERANOPIA_THEME.put("operator", new Color(40,40,40)); //Dark grey
        DEUTERANOPIA_THEME.put("invalid", new Color(220,0,0)); //Dark red

        //Tritanopia Theme
        TRITANOPIA_THEME.put("keyword", new Color(180,0,180)); //Purple
        TRITANOPIA_THEME.put("string", new Color(120,80,0)); //Dark Brown
        TRITANOPIA_THEME.put("comment", new Color(0,180,180)); //Cyan
        TRITANOPIA_THEME.put("label", new Color(100,0,100)); //Dark purple
        TRITANOPIA_THEME.put("operator", new Color(60,60,60)); //Dark grey
        TRITANOPIA_THEME.put("invalid", new Color(255,100,100)); //Light red
    }

    public static Map<String, Color> getTheme(String type){
        return switch (type) {
            case "Protanopia" -> PROTANOPIA_THEME;
            case "Deuteranopia" -> DEUTERANOPIA_THEME;
            case "Tritanopia" -> TRITANOPIA_THEME;
            default -> DEFAULT_THEME;
        };
    }

}




