package View.frames.mainFrame;

/**
 * {@link MainFrameThread} is an extension of {@link Thread}
 * that overrides {@code run} method. Creates {@link MainFrame} object in different thread.
 *
 * @author Naumov A.M.
 * @version 1.0
 */
public class MainFrameThread extends Thread {

    /**
     * Creates {@link Thread} with the overridden method {@code run}.
     * When method {@code start} called creates {@link MainFrame} in different thread.
     */
    public MainFrameThread() {
        super();
        setName("Main Frame Thread");
    }

    @Override
    public void run() {
        MainFrame mainFrame = new MainFrame();
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mainFrame.setVisible(true);
    }
}
