package View.frames.mainFrame;

import View.frames.mainFrame.panels.MainFramePanel;
import gui.AlbumPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * {@link MainFrame} is an extension of {@link JFrame} that adds panels and menu bar on the frame.
 * Used as the main window of the application.
 * Provides with UI components for solving systems of linear equations.
 *
 * @author Naumov A.M.
 * @version 1.0
 */
public class MainFrame extends JFrame {

    private int solutionMethod = 0;

    private final MainFramePanel mainFramePanel;

    private AlbumPanel albumFrame;

    private AlbumPanel.PhotoPanel photoPanel;

    /**
     * Creates frame with menu bar and {@link MainFramePanel}.
     * Provides user interface for solving systems of linear equations.
     */
    public MainFrame() {
        setTitle("Album of graphic illustrations");
        setSize(1200,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new BorderLayout());
        mainFramePanel = new MainFramePanel(this);
        albumFrame = new AlbumPanel(this);




        panel.add(albumFrame, BorderLayout.CENTER);
        panel.add(mainFramePanel, BorderLayout.NORTH);
        add(panel, BorderLayout.WEST);
        setJMenuBar(new MainFrameMenuBar(this));
        mainFramePanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
        pack();
        setLocationRelativeTo(null);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                mainFramePanel.updateScrollPane();
            }
        });
    }
}
