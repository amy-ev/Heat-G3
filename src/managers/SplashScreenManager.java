package managers;

import view.windows.OptionsWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SplashScreenManager {

    private static SplashScreenManager instance = null;
    private final JFrame splashScreen = new JFrame("Accessibility Options");
    public final OptionsWindow ow = new OptionsWindow();

    public SplashScreenManager() {
        // Setup main frame
        splashScreen.setLayout(new BorderLayout());

        // Background Image Panel
        JPanel backgroundPanel = new JPanel() {
            private final Image backgroundImage = new ImageIcon("src/icons/spashscreen.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setPreferredSize(new Dimension(800, 125));

        // Panel for Title, Settings, and Buttons
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(240,240,240));
        //contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title Label
        JLabel titleLabel = new JLabel("Accessibility Options");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(new EmptyBorder(10, 10, 20, 0));

        // Title Panel with Bottom Border
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(true);
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
        titlePanel.add(titleLabel);

        // Add to Content Panel
        contentPanel.add(titlePanel, BorderLayout.NORTH);

        // Settings Panel
        JPanel settingsContainer = new JPanel(new GridBagLayout());
        settingsContainer.setOpaque(false);
        settingsContainer.setBorder(new EmptyBorder(20, 0, 20, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.weightx = 1;

        // Left Column
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Left Column Title with Description
        settingsContainer.add(createTitlePanel("Font Settings"), gbc);
        gbc.gridy++;

//        // UI scaling dropdown
//        settingsContainer.add(
//                createSettingPanel(
//                        "UI Scaling", createDropdown(
//                                new String[]{"Default", "Large", "Extra Large"},  this::applyUIScaling),
//                        "src/icons/accessibility_icons/ui-scaling.png"), gbc);
//        gbc.gridy++;

        // Font Size Settings
        JComboBox<String> jcbOutputFontSize = new JComboBox<>();
        JComboBox<String> jcbCodeFontSize = new JComboBox<>();
        JComboBox<String> jcbGlobalFontSize = new JComboBox<>();

        // Populate font sizes (10 to 24)
        for (int i = 10; i <= 24; i++) {
            jcbOutputFontSize.addItem(String.valueOf(i));
            jcbCodeFontSize.addItem(String.valueOf(i));
            jcbGlobalFontSize.addItem(String.valueOf(i));
        }

        // Editor Font Size
        settingsContainer.add(createSettingPanel("Editor Font Size", jcbCodeFontSize,
                "src/icons/accessibility_icons/editor-fs.png"), gbc);
        gbc.gridy++;

        // Interpreter Font Size
        settingsContainer.add(createSettingPanel("Interpreter Font Size", jcbOutputFontSize,
                "src/icons/accessibility_icons/inter-fs.png"), gbc);
        gbc.gridy++;

        // Global font size
        settingsContainer.add(createSettingPanel("Global Font Size", jcbGlobalFontSize,
                "src/icons/accessibility_icons/global-fs.png"), gbc);

        // Right Column
        gbc.gridx = 1;
        gbc.gridy = 0;

        // Right Column Title with Description
        settingsContainer.add(createTitlePanel("Visual Settings"), gbc);
        gbc.gridy++;

        // Visual disturbance filter toggle
        JToggleButton filterToggle = new JToggleButton("Enable Filter");
        filterToggle.addActionListener(e -> handleFilterToggle(filterToggle.isSelected()));
        settingsContainer.add(createSettingPanel("Visual Disturbance Filter", filterToggle,
                "src/icons/accessibility_icons/vs-filter.png"), gbc);
        gbc.gridy++;

        // Audio response toggle
        JToggleButton audioResponse = new JToggleButton("Enable Audio Response");
        audioResponse.addActionListener(e -> handleAudioToggle(audioResponse.isSelected()));
        settingsContainer.add(createSettingPanel("Audio Response", audioResponse,
                "src/icons/accessibility_icons/audio-resp.png"), gbc);
        gbc.gridy++;

        // Syntax highlighting dropdown
        settingsContainer.add(
                createSettingPanel(
                        "Syntax Highlighting", createDropdown(
                                new String[]{"Default", "High Contrast", "Colorblind Mode"},
                                this::applySyntaxHighlighting),
                        "src/icons/accessibility_icons/syntax-hl.png"), gbc);

        contentPanel.add(settingsContainer, BorderLayout.CENTER);
        gbc.gridy++;

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Border with extra for padding
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 0, 0, 0)
        ));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        // Create buttons
        JButton yesButton = new JButton("Apply");
        JButton noButton = new JButton("Continue");

        // Set fonts
        yesButton.setFont(new Font("Arial", Font.BOLD, 24));
        noButton.setFont(new Font("Arial", Font.BOLD, 24));

        // Set preferred size to make buttons wider
        Dimension buttonSize = new Dimension(200, 40);
        yesButton.setPreferredSize(buttonSize);
        noButton.setPreferredSize(buttonSize);

        // Apply action listener
        yesButton.addActionListener(e -> {
            splashScreen.dispose();
            ow.is_visible = true;
            // TODO add the options window logic here
        });

        // Continue action listener
        noButton.addActionListener(e -> {
            splashScreen.dispose();
            ow.is_visible = true;
        });

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Main Layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(backgroundPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        splashScreen.setContentPane(mainPanel);
        splashScreen.pack();
        splashScreen.setLocationRelativeTo(null);
        splashScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        splashScreen.setVisible(true);

    }

    /**
     * Creates a Settings panel item with title and component.
     * @param title title i.e "Settings item"
     * @param component component i.e "Dropdown"
     * @return panel
     */
    private JPanel createSettingPanel(String title, JComponent component, String iconPath) {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        panel.setOpaque(false);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1, true),
                new EmptyBorder(16, 20, 16, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 20);

        // Icon
        JLabel iconLabel = new JLabel(new ImageIcon(iconPath));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(iconLabel, gbc);

        // Title Label with Increased Font Size
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Label
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(titleLabel, gbc);

        // Component - Toggle Button | Dropdown
        gbc.gridx = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);

        // Apply global size for components
        component.setFont(new Font("Arial", Font.BOLD, 24));

        return panel;
    }

    /**
     * Creates a title component with description
     * @param titleText title text for panel
     * @return titlePanel
     */
    private JPanel createTitlePanel(String titleText) {
        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(new EmptyBorder(0, 0, 5, 0));

        titlePanel.add(titleLabel);

        return titlePanel;
    }

    /**
     * Creates a dropdown with an action listener.
     */
    private JComboBox<String> createDropdown(String[] options, ActionListener actionListener) {
        JComboBox<String> dropdown = new JComboBox<>(options);
        dropdown.setPreferredSize(new Dimension(150, 25));
        dropdown.setFont(new Font("Arial", Font.BOLD, 24));
        dropdown.addActionListener(actionListener);
        return dropdown;
    }

    /**
     * Handle filter toggle
     * @param isEnabled
     */
    public void handleFilterToggle(boolean isEnabled) {
        if (isEnabled) {
            System.out.println("Visual Disturbance Filter ENABLED.");
            // Add logic to apply the filter
        } else {
            System.out.println("Visual Disturbance Filter DISABLED.");
            // Add logic to remove the filter
        }
    }

    public void handleAudioToggle(boolean isEnabled) {
        if (isEnabled) {
            System.out.println("Audio Response ENABLED.");
            // Add logic for response
        } else {
            System.out.println("Audio Response DISABLED.");
        }
    }

    /**
     * Adjusts UI scaling
     */
    public void applyUIScaling(java.awt.event.ActionEvent e) {
        JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
        String selectedOption = (String) comboBox.getSelectedItem();

        int fontSize = switch (selectedOption) {
            case "Large" -> 18;
            case "Extra Large" -> 24;
            default -> 14;
        };

        // TODO add the settings
    }

    /**
     * Adjusts Syntax Highlighting
     */
    public void applySyntaxHighlighting(java.awt.event.ActionEvent e) {
        JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
        String selectedOption = (String) comboBox.getSelectedItem();
        System.out.println("Syntax Highlighting changed to: " + selectedOption);
        // TODO: Implement syntax color change logic
    }

    /**
     * Singleton Instance
     */
    public static SplashScreenManager getInstance() {
        if (instance == null)
            instance = new SplashScreenManager();
        return instance;
    }

    /**
     * Checks if the splash screen is active.
     */
    public boolean isActive() {
        return splashScreen.isActive();
    }

    /**
     * Checks if OptionsWindow is active.
     */
    public boolean owIsActive() {
        return ow.is_visible;
    }
}
