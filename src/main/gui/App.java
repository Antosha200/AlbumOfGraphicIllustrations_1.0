package gui;

import View.MainController;
import View.frames.mainFrame.MainFrame;

import javax.swing.*;

public class App {
    /**
     * Runs the photo album App
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new MainController();
    }
}
