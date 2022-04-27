package gui;

import javax.swing.*;

public class App {
    /**
     * Runs the photo album App
     */
    public static void main(String[] args) {

        // Enable a pre-set native look & feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start the main window
        new MainFrame();
    }
}
