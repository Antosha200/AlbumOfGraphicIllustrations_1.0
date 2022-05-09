package view.frames.splashFrame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class SplashFrameMouseAdapter extends MouseAdapter {
    private final JFrame frame;
    private int x, y;
    private boolean isMousePressed, isMouse1Pressed, isMouse3Pressed;

    protected SplashFrameMouseAdapter(JFrame frame) {
        super();
        this.frame = frame;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        if (isMousePressed) {
            isMousePressed = false;
            x = e.getXOnScreen();
            y = e.getYOnScreen();
        }
        if (isMouse1Pressed)
            frame.setLocation(frame.getLocationOnScreen().x + e.getXOnScreen() - x, frame.getLocationOnScreen().y + e.getYOnScreen() - y);
        x = e.getXOnScreen();
        y = e.getYOnScreen();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (e.getButton() == MouseEvent.BUTTON1)
            isMouse1Pressed = true;
        else if (e.getButton() == MouseEvent.BUTTON3)
            isMouse3Pressed = true;
        isMousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        int button = e.getButton();
        if (button == MouseEvent.BUTTON1)
            isMouse1Pressed = false;
        else if (button == MouseEvent.BUTTON2)
            isMouse3Pressed = false;
    }
}
