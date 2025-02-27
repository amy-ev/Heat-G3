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

    static {
        // Default Theme (Standard vision)
        DEFAULT_THEME.put("keyword1", new Color(0, 0, 255));  // Bright Blue
        DEFAULT_THEME.put("keyword2", new Color(75, 0, 130)); // Indigo
        DEFAULT_THEME.put("keyword3", new Color(139, 0, 139)); // Dark Magenta
        DEFAULT_THEME.put("literal1", new Color(153, 76, 0));  // Brown
        DEFAULT_THEME.put("literal2", new Color(255, 140, 0)); // Dark Orange
        DEFAULT_THEME.put("comment1", new Color(0, 102, 0));   // Green
        DEFAULT_THEME.put("comment2", new Color(34, 139, 34)); // Forest Green
        DEFAULT_THEME.put("label", new Color(128, 0, 128));    // Purple
        DEFAULT_THEME.put("operator", new Color(0, 0, 0));     // Black
        DEFAULT_THEME.put("invalid", new Color(255, 0, 0));    // Red

        // Protanopia Theme (Red color blindness)
        PROTANOPIA_THEME.put("keyword1", new Color(0, 0, 200));  // Dark Blue
        PROTANOPIA_THEME.put("keyword2", new Color(0, 100, 180)); // Deep Cyan-Blue
        PROTANOPIA_THEME.put("keyword3", new Color(50, 50, 150)); // Muted Indigo
        PROTANOPIA_THEME.put("literal1", new Color(120, 60, 0));  // Dark Brown
        PROTANOPIA_THEME.put("literal2", new Color(200, 100, 0)); // Deep Orange
        PROTANOPIA_THEME.put("comment1", new Color(0, 128, 128)); // Teal
        PROTANOPIA_THEME.put("comment2", new Color(0, 180, 180)); // Light Cyan
        PROTANOPIA_THEME.put("label", new Color(80, 0, 0));       // Dark Purple
        PROTANOPIA_THEME.put("operator", new Color(50, 50, 50));  // Dark Gray
        PROTANOPIA_THEME.put("invalid", new Color(200, 0, 0));    // Dark Red

        // Deuteranopia Theme (Green color blindness)
        DEUTERANOPIA_THEME.put("keyword1", new Color(0, 0, 100));  // Dark Blue
        DEUTERANOPIA_THEME.put("keyword2", new Color(0, 75, 130)); // Muted Cyan-Blue
        DEUTERANOPIA_THEME.put("keyword3", new Color(70, 70, 140)); // Slightly Brighter Indigo
        DEUTERANOPIA_THEME.put("literal1", new Color(100, 50, 0));  // Muted Brown
        DEUTERANOPIA_THEME.put("literal2", new Color(180, 90, 0)); // Warm Orange
        DEUTERANOPIA_THEME.put("comment1", new Color(0, 120, 120)); // Dark Teal
        DEUTERANOPIA_THEME.put("comment2", new Color(0, 160, 160)); // Brighter Cyan
        DEUTERANOPIA_THEME.put("label", new Color(90, 0, 90));     // Dark Purple
        DEUTERANOPIA_THEME.put("operator", new Color(40, 40, 40)); // Dark Grey
        DEUTERANOPIA_THEME.put("invalid", new Color(220, 0, 0));   // Deep Red

        // Tritanopia Theme (Blue color blindness)
        TRITANOPIA_THEME.put("keyword1", new Color(180, 0, 180)); // Purple
        TRITANOPIA_THEME.put("keyword2", new Color(140, 0, 140)); // Muted Violet
        TRITANOPIA_THEME.put("keyword3", new Color(110, 0, 110)); // Darker Violet
        TRITANOPIA_THEME.put("literal1", new Color(120, 80, 0));  // Dark Brown
        TRITANOPIA_THEME.put("literal2", new Color(210, 130, 0)); // Golden Orange
        TRITANOPIA_THEME.put("comment1", new Color(0, 180, 180)); // Cyan
        TRITANOPIA_THEME.put("comment2", new Color(0, 220, 220)); // Bright Cyan
        TRITANOPIA_THEME.put("label", new Color(100, 0, 100));    // Dark Purple
        TRITANOPIA_THEME.put("operator", new Color(60, 60, 60));  // Dark Grey
        TRITANOPIA_THEME.put("invalid", new Color(255, 100, 100)); // Light Red
    }

    public static Map<String, Color> getTheme(String type) {
        return switch (type) {
            case "Protanopia" -> PROTANOPIA_THEME;
            case "Deuteranopia" -> DEUTERANOPIA_THEME;
            case "Tritanopia" -> TRITANOPIA_THEME;
            default -> DEFAULT_THEME;
        };
    }
}







