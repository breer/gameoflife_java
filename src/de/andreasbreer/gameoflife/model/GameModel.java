package de.andreasbreer.gameoflife.model;

/**
 * This class represents the game itself.
 * @author Andreas Breer
 */
public class GameModel implements Runnable {

    /**
     * The default value for the frame width.
     */
    private final static int DEFAULT_FRAME_WIDTH = 35;

    /**
     * The default value for the frame height.
     */
    private final static int DEFAULT_FRAME_HEIGHT = 35;

    /**
     * The default value for the frames per seconds.
     */
    private final static int DEFAULT_FRAMES_PER_SECOND = 15;

    /**
     * The state of this game.
     */
    private GameState state;

    /**
     * The frames that should calculate per seconds.
     */
    private int framesPerSecond;

    /**
     * The world to play on.
     */
    private World world;

    /**
     * The thread that calculates the next generation.
     */
    private Thread gameThread;

    /**
     * The constructor of this class.
     */
    public GameModel() {
        this(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
    }

    /**
     * The constructor of this class using the fields width and height.
     * @param width
     *            the width of the world
     * @param height
     *            the height of this world
     */
    public GameModel(int width, int height) {
        this.state = GameState.INITIALIZE;
        this.setFramesPerSecond(DEFAULT_FRAMES_PER_SECOND);
        this.world = new World(width, height);
        this.gameThread = new Thread(this);
        this.gameThread.start();

    }

    /**
     * Get the state of this game.
     * @return the state of this game.
     */
    public synchronized GameState getState() {
        return state;
    }

    /**
     * Determine whether the game is running.
     * @return <code>true</code> if the game is running
     */
    public synchronized boolean isRunnung() {
        return (GameState.RUNNING.equals(state));
    }

    /**
     * This method starts the game.
     */
    public synchronized void startGame() {
        this.state = GameState.RUNNING;
        synchronized (world) {
            world.notify();
        }
    }

    /**
     * This method stops the game.
     */
    public synchronized void stopGame() {
        this.state = GameState.STOPPED;
    }

    /**
     * Get the frames per second.
     * @return the frames per seconds
     */
    public synchronized int getFramesPerSecond() {
        return framesPerSecond;
    }

    /**
     * Set the frames per seconds.
     * @param framesPerSecond
     *            the frames per second
     */
    public synchronized void setFramesPerSecond(int framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
    }

    /**
     * Get the world of this game.
     * @return the world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Run the thread to calculate the next generation.
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // Wait when the game stopped.
            synchronized (world) {
                if (!isRunnung()) {
                    try {
                        world.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            // Calculate the next generation.
            long time = System.currentTimeMillis();
            world.nextGeneration();
            time = System.currentTimeMillis() - time;
            long sleeptime = 1000 / framesPerSecond - time;

            // Go to sleep for a while.
            if (sleeptime > 0) {
                try {
                    Thread.sleep(sleeptime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

    }

}
