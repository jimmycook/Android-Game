package jimmycook.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * Main game logic thread
 */
public class MainThread extends Thread
{
    private int fps = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    /**
     * Set the SurfaceHolder and GamePanel attributes and call super
     * @param surfaceHolder SurfaceHolder
     * @param gamePanel GamePanel
     */
    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    /**
     * The game logic
     */
    @Override
    public void run(){
        // Time and frame variables
        long startTime;
        long timeMills;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / this.fps;

        // If the game should be running perform the game logic
        while(running){
            // Empty the canvas
            canvas = null;
            // Issue the game panel to update
            this.gamePanel.update();

            // Calculate the wait time
            startTime = System.nanoTime();
            timeMills = (System.nanoTime()-startTime) / 1000000;
            waitTime = targetTime - timeMills;
            // Try and wait for the wait time
            try{
                this.sleep(waitTime);
            }
            catch(Exception e){
                e.printStackTrace();
            }

            // Increase the total time
            totalTime += System.nanoTime()-startTime;

            // Calculate the FPS and logging it for debugging
            frameCount++;
            if(frameCount == this.fps){
                averageFPS = 1000 / ((totalTime/frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                Log.d("FPS: ", String.valueOf(averageFPS));
            }
        }
    }

    /**
     * Set the running attribute
     *
     * @param running boolean
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Get the running attribute
     * @return boolean
     */
    public boolean getRunning(){
        return this.running;
    }
}
