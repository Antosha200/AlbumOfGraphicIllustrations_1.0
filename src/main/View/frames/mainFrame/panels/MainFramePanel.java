package View.frames.mainFrame.panels;

import View.frames.mainFrame.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * {@link MainFramePanel} is an extension of {@link JPanel} that adds panels and menu bar on the frame.
 * Used as the main panel on the {@link MainFrame}.
 * Provides with UI components for solving systems of linear equations.
 *
 * @author Naumov A.M.
 * @version 1.0
 */
public class MainFramePanel extends JPanel {

    //private EquationPanel equationPanel;
    private JScrollPane scrollPane;
    private final GridBagConstraints scrollPaneConstraints;

    private final MainFrame frame;

    /**
     * Creates {@link MainFramePanel} with components for solving systems of linear equations.
     *
     * @param frame parent of the panel. Used to get its size.
     */
    public MainFramePanel(MainFrame frame) {
        this.frame = frame;
        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        GridBagConstraints controlsPanelConstraints = new GridBagConstraints();
        controlsPanelConstraints.insets = new Insets(1, 3, 3, 3);
        scrollPaneConstraints = new GridBagConstraints();
    }


    /**
     * Updates {@code JScrollPane} with the equation panel.
     */
    public void updateScrollPane() {
        int verticalInsets = 10;
        int horizontalInsets = 6;
        scrollPane.setPreferredSize(
                new Dimension(
                        frame.getWidth()
                                - scrollPane.getVerticalScrollBar().getWidth()
                                - horizontalInsets,
                        frame.getLocationOnScreen().y + frame.getSize().height
                                - scrollPane.getLocationOnScreen().y
                                - verticalInsets));
        updateUI();
    }

}
