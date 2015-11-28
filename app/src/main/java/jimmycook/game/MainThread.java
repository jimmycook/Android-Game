package jimmycook.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * Created by Jimmypq on 19/10/2015.
 */
public class MainThread extends Thread
{
    private int fps = 30;

    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();

        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;

    }

    @Override
    public void run(){
        long startTime;
        long timeMills;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / this.fps;

        while(running){
            startTime = System.nanoTime();
            canvas = null;

            // try locking the canvas
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            }catch(Exception e){

            }
            finally{
                if(canvas!=null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e){

                    }
                }
            }

            timeMills = (System.nanoTime()-startTime) / 1000000;
            waitTime = targetTime - timeMills;

            // Try and wait for the wait time
            try{
                this.sleep(waitTime);
            }catch(Exception e){}

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == this.fps){
                averageFPS = 1000 / ((totalTime/frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
//                Log.d("FPS: ", String.valueOf(averageFPS));
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean getRunning(){
        return this.running;
    }

    public void touch(MotionEvent e){
    }
}
