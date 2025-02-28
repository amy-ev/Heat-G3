package test;

import managers.SplashScreenManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

class SplashScreenManagerTest {

    private SplashScreenManager manager;

//    @BeforeEach
//    void setUp() {
//        manager = SplashScreenManager.getInstance();
//    }
//
//    @Test
//    void testSingletonInstance() {
//        SplashScreenManager anotherInstance = SplashScreenManager.getInstance();
//        assertSame(manager, anotherInstance, "getInstance should return the same instance.");
//    }

    @Test
    void testHandleFilterToggle() {
        // Enable the filter
        manager.handleFilterToggle(true);

        // Disable the filter
        manager.handleFilterToggle(false);

    }

    @Test
    void testHandleAudioToggle() {
        // Simulate enabling the audio response
        manager.handleAudioToggle(true);

        // Simulate disabling the audio response
        manager.handleAudioToggle(false);
    }

    @Test
    void testApplyUIScaling() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Default", "Large", "Extra Large"});
        comboBox.setSelectedItem("Large");
        ActionEvent event = new ActionEvent(comboBox, ActionEvent.ACTION_PERFORMED, "Large");
        manager.applyUIScaling(event);
    }

    @Test
    void testApplySyntaxHighlighting() {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Default", "High Contrast", "Colorblind Mode"});
        comboBox.setSelectedItem("High Contrast");
        ActionEvent event = new ActionEvent(comboBox, ActionEvent.ACTION_PERFORMED, "High Contrast");
        manager.applySyntaxHighlighting(event);
    }
}