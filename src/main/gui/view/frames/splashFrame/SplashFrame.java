package gui.view.frames.splashFrame;

import javax.swing.*;

class SplashFrame extends JFrame {

    private final SplashFramePanel panel;

    protected SplashFrame() {
        setUndecorated(true);
        panel = new SplashFramePanel();
        add(panel);
        pack();
        setLocationRelativeTo(null);
        initializeMouseAdapter();
        setVisible(true);
    }

    private void initializeMouseAdapter() {
        SplashFrameMouseAdapter mouseListener = new SplashFrameMouseAdapter(this);
        addMouseMotionListener(mouseListener);
        addMouseListener(mouseListener);
    }

    protected boolean isStartButtonClicked() {
        return panel.isStartButtonClicked();
    }
}
