package view;

import view.frames.mainFrame.MainFrameThread;
import view.frames.splashFrame.SplashFrameThread;

/**
 * Class which controls creating {@link MainFrameThread} and {@link SplashFrameThread}
 *
 * @author Naumov A.M
 * @version 1.0
 */
public class MainController {

    /**
     * Creates and starts {@link SplashFrameThread} and {@link MainFrameThread}.
     * Waits until start button on {@code SplashFrame} clicked and after that notifies {@link MainFrameThread} object.
     */
    public MainController() {
        SplashFrameThread splashFrameThread = new SplashFrameThread();
        MainFrameThread mainFrameThread = new MainFrameThread();
        splashFrameThread.start();
        mainFrameThread.start();
        while (!splashFrameThread.isStartButtonClicked())
            Thread.yield();
        splashFrameThread.interrupt();
        while (mainFrameThread.getState() != Thread.State.WAITING)
            Thread.yield();
        synchronized (mainFrameThread) {
            mainFrameThread.notify();
        }
    }
}
