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

    // Grab the colour settings for the overlay and assign to String objects
    private String overlayRed = sm.getSetting(Settings.OVERLAY_RED);
    private String overlayGreen = sm.getSetting(Settings.OVERLAY_GREEN);
    private String overlayBlue = sm.getSetting(Settings.OVERLAY_BLUE);
    private String overlayAlpha = sm.getSetting(Settings.OVERLAY_ALPHA);

    // Parse String objects for int value of overlay colours
    private int red = Integer.parseInt(overlayRed);
    private int green = Integer.parseInt(overlayGreen);
    private int blue = Integer.parseInt(overlayBlue);
    private int alpha = Integer.parseInt(overlayAlpha);

    protected OverlayManager() {
        /* Exists to prevent instantiation */
    }

    public static OverlayManager getInstance() {
        if (instance == null)
            instance = new OverlayManager();

        return instance;
    }

    public void addFrameOverlay(JFrame frame, String toggle) {
        // Checks if the overlay display setting is true && !null and displays the overlay if so
        if (displayOverlay != null && displayOverlay != "false") {
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
        }
    }

    public void addPanelOverlay(JDialog dialog, JPanel panel) {

        dialog.pack();

        int dialogWidth = dialog.getContentPane().getWidth();
        int dialogHeight = dialog.getContentPane().getHeight();

        // Checks if the overlay display setting is true && !null and displays the overlay if so
        if (displayOverlay != null && displayOverlay != "false") {
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

            dialog.add(layeredPane, BorderLayout.CENTER);
            dialog.setVisible(true);
        } else {
            dialog.setVisible(true);
        }
    }
}
