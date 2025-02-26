package managers;

import view.windows.OptionsWindow;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SplashScreenManager {

    private static SplashScreenManager instance = null;
    private final JFrame splashScreen = new JFrame("Accessibility Mode");
    private final OptionsWindow ow = new OptionsWindow();

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
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Stretch to fill width
                }
            }
        };
        backgroundPanel.setPreferredSize(new Dimension(800, 300)); // Adjust height if needed

        // Panel for Title, Settings, and Buttons
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title Panel
        JLabel titleLabel = new JLabel("Accessibility Options");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(new EmptyBorder(0, 0, 25, 0));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);
        contentPanel.add(titlePanel, BorderLayout.NORTH);

        // Settings Panel
        JPanel settingsContainer = new JPanel(new GridBagLayout());
        settingsContainer.setOpaque(false);
        settingsContainer.setBorder(new EmptyBorder(10, 0, 20, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.weightx = 1;  // Column spacing setting

        // Left Column
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Left Column Title with Description
        settingsContainer.add(createTitlePanel("Font Settings", "Customise font size and readability."), gbc);
        gbc.gridy++;

        // Visual disturbance filter toggle
        JToggleButton filterToggle = new JToggleButton("Enable Filter");
        filterToggle.addActionListener(e -> handleFilterToggle(filterToggle.isSelected()));
        settingsContainer.add(createSettingPanel("Visual Disturbance Filter", filterToggle), gbc);
        gbc.gridy++;

        // UI scaling dropdown
        settingsContainer.add(
                createSettingPanel(
                        "UI Scaling", createDropdown(
                                new String[]{"Default", "Large", "Extra Large"}, this::applyUIScaling)), gbc);
        gbc.gridy++;

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
        settingsContainer.add(createSettingPanel("Editor Font Size", jcbCodeFontSize), gbc);
        gbc.gridy++;

        // Interpreter Font Size
        settingsContainer.add(createSettingPanel("Interpreter Font Size", jcbOutputFontSize), gbc);
        gbc.gridy++;

        // Global font size
        settingsContainer.add(createSettingPanel("Global Font Size", jcbGlobalFontSize), gbc);

        // Right Column
        gbc.gridx = 1;
        gbc.gridy = 0;

        // Right Column Title with Description
        settingsContainer.add(createTitlePanel("Visual Settings", "Adjust screen contrast and effects."), gbc);
        gbc.gridy++;

        // Audio response toggle
        JToggleButton audioResponse = new JToggleButton("Enable Audio Response");
        audioResponse.addActionListener(e -> handleAudioToggle(audioResponse.isSelected()));
        settingsContainer.add(createSettingPanel("Audio Response", audioResponse), gbc);
        gbc.gridy++;

        // Syntax highlighting dropdown
        settingsContainer.add(
                createSettingPanel(
                        "Syntax Highlighting", createDropdown(
                                new String[]{"Default", "High Contrast", "Colorblind Mode"},
                                this::applySyntaxHighlighting)), gbc);

        contentPanel.add(settingsContainer, BorderLayout.CENTER);
        gbc.gridy++;

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Border with extra for padding
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 0, 0, 0)
        ));
        // Create buttons
        JButton yesButton = new JButton("Apply");
        JButton noButton = new JButton("Continue");

        // Set fonts
        yesButton.setFont(new Font("Arial", Font.BOLD, 16));
        noButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Set preferred size to make buttons wider
        Dimension buttonSize = new Dimension(200, 40);
        yesButton.setPreferredSize(buttonSize);
        noButton.setPreferredSize(buttonSize);

        // Apply action listener
        yesButton.addActionListener(e -> {
            splashScreen.dispose();
            ow.show();
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
     * @param title
     * @param component
     * @return
     */
    private JPanel createSettingPanel(String title, JComponent component) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(10, 10, 10, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Title Label with Increased Font Size
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(titleLabel, gbc);

        // Component - Toggle Button | Dropdown
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);

        // Apply global 14-point size for components
        component.setFont(new Font("Arial", Font.PLAIN, 14));

        return panel;
    }



    /**
     * Creates a title component with description
     * @param titleText
     * @param descriptionText
     * @return
     */
    private JPanel createTitlePanel(String titleText, String descriptionText) {
        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel descriptionLabel = new JLabel(descriptionText);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setForeground(Color.DARK_GRAY);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(new EmptyBorder(0, 0, 5, 0));

        titlePanel.add(titleLabel);
        titlePanel.add(descriptionLabel);

        return titlePanel;
    }

    /**
     * Creates a dropdown with an action listener.
     */
    private JComboBox<String> createDropdown(String[] options, ActionListener actionListener) {
        JComboBox<String> dropdown = new JComboBox<>(options);
        dropdown.setPreferredSize(new Dimension(150, 25));
        dropdown.setFont(new Font("Arial", Font.BOLD, 14));
        dropdown.addActionListener(actionListener);
        return dropdown;
    }

    /**
     * Handle filter toggle
     * @param isEnabled
     */
    private void handleFilterToggle(boolean isEnabled) {
        if (isEnabled) {
            System.out.println("Visual Disturbance Filter ENABLED.");
            // Add logic to apply the filter
        } else {
            System.out.println("Visual Disturbance Filter DISABLED.");
            // Add logic to remove the filter
        }
    }

    private void handleAudioToggle(boolean isEnabled) {
        if (isEnabled) {
            System.out.println("Audi Response ENABLED.");
            // Add logic for response
        } else {
            System.out.println("Audi Response DISABLED.");
        }
    }

    /**
     * Adjusts UI scaling
     */
    private void applyUIScaling(java.awt.event.ActionEvent e) {
        JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
        String selectedOption = (String) comboBox.getSelectedItem();

        int fontSize = switch (selectedOption) {
            case "Large" -> 18;
            case "Extra Large" -> 22;
            default -> 14;
        };

        // TODO add the settings
    }

    /**
     * Adjusts Syntax Highlighting
     */
    private void applySyntaxHighlighting(java.awt.event.ActionEvent e) {
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
