package View.frames.splashFrame;

/**
 * {@link SplashFrameThread} is an extension of {@link Thread}
 * that overrides {@code run} method. Creates {@link SplashFrame} object in different thread.
 *
 * @author Naumov A.M.
 * @version 1.0
 */
public class SplashFrameThread extends Thread {

    private SplashFrame splashFrame;
    private volatile boolean isStartButtonClicked = false;

    /**
     * Creates {@link Thread} with the overridden method {@code run}.
     * When method {@code start} called creates {@link SplashFrame} in different thread.
     */
    public SplashFrameThread() {
        super();
        setName("Splash Frame Thread");
    }

    @Override
    public void run() {
        splashFrame = new SplashFrame();
        long time = System.currentTimeMillis();
        while (!splashFrame.isStartButtonClicked()) {
            if (System.currentTimeMillis() - time >= 60000)
                System.exit(0);
            try {
                sleep(200);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
        isStartButtonClicked = true;
    }

    @Override
    public void interrupt() {
        super.interrupt();
        splashFrame.dispose();
    }

    /**
     * Gets the value of the {@code isStartButtonClicked} property.
     *
     * @return the value of the {@code isStartButtonClicked} property.
     */
    public boolean isStartButtonClicked() {
        return isStartButtonClicked;
    }
}
