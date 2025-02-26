package utils.jsyntax;
import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import static utils.jsyntax.SyntaxUtilities.applyTheme;

public class SyntaxThemeSelectionPanel extends JPanel {

    public SyntaxThemeSelectionPanel() {
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createLineBorder(Color.RED, 3));


        JButton defaultThemeButton = new JButton("Default mode");
        JButton protanopiaThemeButton = new JButton("Protanopia mode");
        JButton deuteranopiaThemeButton = new JButton("Deuteranopia mode");
        JButton tritanopiaThemeButton = new JButton("Tritanopia mode");

        defaultThemeButton.setPreferredSize(new Dimension(150, 30));
        protanopiaThemeButton.setPreferredSize(new Dimension(150, 30));
        deuteranopiaThemeButton.setPreferredSize(new Dimension(150, 30));
        tritanopiaThemeButton.setPreferredSize(new Dimension(150, 30));


        defaultThemeButton.addActionListener(e -> {
            applyTheme("default");
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getWindowAncestor(this));
            SwingUtilities.getWindowAncestor(this).repaint();
        });

        protanopiaThemeButton.addActionListener(e -> {
            applyTheme("Protanopia");
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getWindowAncestor(this));
            SwingUtilities.getWindowAncestor(this).repaint();
        });

        deuteranopiaThemeButton.addActionListener(e -> {
            applyTheme("Deuteranopia");
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getWindowAncestor(this));
            SwingUtilities.getWindowAncestor(this).repaint();
        });

        tritanopiaThemeButton.addActionListener(e -> {
            applyTheme("Tritanopia");
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getWindowAncestor(this));
            SwingUtilities.getWindowAncestor(this).repaint();
        });



        add(defaultThemeButton);
        add(protanopiaThemeButton);
        add(deuteranopiaThemeButton);
        add(tritanopiaThemeButton);

        setVisible(true);
    }
}
