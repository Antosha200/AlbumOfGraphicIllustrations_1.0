package view.frames.inputFileHelpFrame;

import javax.swing.*;

/**
 * {@link HelpFrame} is an extension of {@link JFrame}
 * that contains information about creating input file for the program.
 *
 * @author Naumov A.M.
 * @version 1.0
 */
public class HelpFrame extends JFrame {

    /**
     * Creates {@link JFrame} with information about creating input file for the program.
     */
    public HelpFrame() {
        setTitle("Help");
        add(createPanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JScrollPane createPanel() {
        JTextPane textPane = new JTextPane();
        textPane.setText("If you have any questions,\n" + "please contact the developer by e-mail:\n" +
                "anton2-2000@mail.ru");
        textPane.setEditable(false);
        return new JScrollPane(textPane);
    }
}
