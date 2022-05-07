package gui.view.frames.inputFileHelpFrame;

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
        setSize(getToolkit().getScreenSize().width / 3, getToolkit().getScreenSize().height / 3);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JScrollPane createPanel() {
        JTextArea textArea = new JTextArea("");
        textArea.setEditable(false);
        return new JScrollPane(textArea);
    }
}
