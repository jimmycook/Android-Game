package jimmycook.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * Render thread
 */
public class RenderThread extends Thread
{

    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    /**
     * Constructor
     * @param surfaceHolder SurfaceHolder
     * @param gamePanel GamePanel
     */
    public RenderThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();

        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;

    }


    /**
     * Rendering loop logic
     */
    @Override
    public void run(){
        int fps = 30;
        long startTime;
        long timeMills;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / fps;

        while(running){
            // Get the current time
            startTime = System.nanoTime();
            // Set the canvas to null
            canvas = null;

            // try locking the canvas
            try{
                // get the canvas from the surface holder
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    // Pass the canvas to the game panel to draw the bitmaps to it
                    this.gamePanel.draw(canvas);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            finally{
                // If the canvas isn't null
                if(canvas!=null){
                    try{
                        // unlock the canvas and post it to the surface holder
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }

            // Calculate the wait time
            timeMills = (System.nanoTime()-startTime) / 1000000;
            waitTime = targetTime - timeMills;

            // Try and wait for the wait time
            try{
                this.sleep(waitTime);
            }catch(Exception e){}

            // Increment the frame count
            totalTime += System.nanoTime()-startTime;
            frameCount++;
            // Log when the frame count is the same as the target FPS (default 30)
            if(frameCount == fps){
                averageFPS = 1000 / ((totalTime/frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                Log.d("FPS: ", String.valueOf(averageFPS));
            }
        }
    }

    /**
     * Setter for running
     * @param running boolean
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

}
