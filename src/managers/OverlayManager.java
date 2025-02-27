package managers;

import utils.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class OverlayManager {
    private static OverlayManager instance = null;

    // Get a settings manager instance and assign the OVERLAY_DISPLAY setting to a variable
    private SettingsManager sm = SettingsManager.getInstance();
    private String displayOverlay = sm.getSetting(Settings.OVERLAY_DISPLAY);

    protected OverlayManager() {
        /* Exists to prevent instantiation */
    }

    public static OverlayManager getInstance() {
        if (instance == null)
            instance = new OverlayManager();

        return instance;
    }

    public void addFrameOverlay(JFrame frame, String toggle) {
        // Grab the colour settings for the overlay and assign to String objects
        String overlayColour = sm.getSetting(Settings.OVERLAY_COLOUR);

        // Split the String object holding the RGB values for the colour
        String[] rgbValues = overlayColour.split(",");

        // Parse the split string values into integers
        int red = Integer.parseInt(rgbValues[0]);
        int green = Integer.parseInt(rgbValues[1]);
        int blue = Integer.parseInt(rgbValues[2]);
        int alpha = Integer.parseInt(rgbValues[3]);

//        String displayOverlay = sm.getSetting(Settings.OVERLAY_DISPLAY);
        // Checks if the overlay display setting is true && !null and displays the overlay if so
        if (displayOverlay != null && !toggle.equals("Off")) {
            JPanel glassPane = new JPanel() {

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setColor(new Color(red, green, blue, alpha)); // Controls overlay colour
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    g2d.dispose();
                }
            };
            // Creates transparency, ensures interactivity of underlying elements, assigns to main window frame
            glassPane.setOpaque(false);
            glassPane.setLayout(null);
            frame.setGlassPane(glassPane);
            glassPane.setVisible(true);
        } else {
            // Sets the glass pane to false to hide the overlay
            frame.getGlassPane().setVisible(false);
        }
    }

    public void addPanelOverlay(JDialog dialog, JPanel panel, String toggle) {

        // Grab the colour settings for the overlay and assign to String objects
        String overlayColour = sm.getSetting(Settings.OVERLAY_COLOUR);

        // Split the String object holding the RGB values for the colour
        String[] rgbValues = overlayColour.split(",");

        // Parse the split string values into integers
        int red = Integer.parseInt(rgbValues[0]);
        int green = Integer.parseInt(rgbValues[1]);
        int blue = Integer.parseInt(rgbValues[2]);
        int alpha = Integer.parseInt(rgbValues[3]);

//        String displayOverlay = sm.getSetting(Settings.OVERLAY_DISPLAY);
        dialog.pack();

        // Get the width and height of the dialog, ensures compatability with different size dialogs
        int dialogWidth = dialog.getContentPane().getWidth();
        int dialogHeight = dialog.getContentPane().getHeight();

        // Checks if the overlay display setting is true && !null and displays the overlay if so
        if (displayOverlay != null && !toggle.equals("Off")) {
            JPanel overlayPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setColor(new Color(red, green, blue, alpha)); // Controls overlay colour
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    g2d.dispose();
                }
            };

            // Creates transparency, ensures interactivity of underlying elements, bounds match dialog
            overlayPanel.setOpaque(false);
            overlayPanel.setLayout(null);
            overlayPanel.setBounds(0, 0, dialogWidth, dialogHeight);

            // JLayeredPane goes above the main panel on the PALLETTE_LAYER, bounds/dimensions match dialog
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(dialogWidth, dialogHeight));
            panel.setBounds(0, 0, dialogWidth, dialogHeight);
            layeredPane.add(panel, JLayeredPane.DEFAULT_LAYER);
            layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER);

            // This adds a component listener which resizes the new JLayeredPane as the user resizes the panel
            dialog.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    int width = dialog.getContentPane().getWidth();
                    int height = dialog.getContentPane().getHeight();
                    panel.setBounds(0, 0, width, height);
                    overlayPanel.setBounds(0, 0, width, height);
                }
            });
            // Adds the layered pane to the dialog
            dialog.setLayeredPane(layeredPane);
        } else {
          // This grabs the layered pane and removes it
          JLayeredPane layeredPane = dialog.getLayeredPane();
          dialog.remove(layeredPane);
        }
    }
}
