package managers;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreenManager {

    private static SplashScreenManager instance = null;
    JFrame splashScreen = new JFrame("Accessibility Mode");

    public SplashScreenManager() {

        // Setup layout
        splashScreen.setSize(420, 300);
        splashScreen.setLocationRelativeTo(null);
        splashScreen.setLayout(new BorderLayout());

        // Panel to hold the background image and components
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Set background image
                ImageIcon backgroundImage = new ImageIcon("src/icons/splash.png");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Set the layout of the background panel
        backgroundPanel.setLayout(new BorderLayout());

        // Panel for the text and button content
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(true);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(10,0,0,0));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel("Accessibility Options");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add the title to the content panel
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Buttons
        JButton yesButton = new JButton("Options");
        JButton noButton = new JButton("No");
        yesButton.getPreferredSize();
        yesButton.setFont(new Font("Arial", Font.BOLD, 20));
        noButton.getPreferredSize();
        noButton.setFont(new Font("Arial", Font.BOLD, 20));

        // Add button group
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(new MatteBorder(0,0,1,1,Color.BLACK));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        // Panel to hold content
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(contentPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Styling panels on window
        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);
        splashScreen.setContentPane(backgroundPanel);

        // Final splash screen setup
        splashScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        splashScreen.setVisible(true);
    }



    public static SplashScreenManager getInstance() {
        if (instance == null)
            instance = new SplashScreenManager();
        return instance;
    }

    public boolean isActive(){
        return splashScreen.isActive();
    }
}
