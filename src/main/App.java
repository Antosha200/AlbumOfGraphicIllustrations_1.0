import view.MainController;

import javax.swing.*;

/**
 * The class from which the application starts working
 *
 * @author Naumov A.M.
 * @version 1.0
 */
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
