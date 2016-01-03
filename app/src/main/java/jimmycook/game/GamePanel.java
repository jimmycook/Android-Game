package jimmycook.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * This class is the container for the game
 * Created  on 19/10/2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final float WIDTH = 856;
    public static final float HEIGHT = 480;
    private Game context;
    private MainThread gameThread;
    private RenderThread renderThread;
    private Level level;
    private Player player;

    /**
     * Initialises both the game and render threads being used
     * and prepare the surfaceholder for events
     *
     * @param context Game Activity
     */
    public GamePanel(Game context) {
        super(context);

        this.context = context;

        // add the callback to the surfaceholder to intecept events
        getHolder().addCallback(this);

        gameThread = new MainThread(getHolder(), this);
        renderThread = new RenderThread(getHolder(), this);

        // make gamepanel focusable so it can handle events
        setFocusable(true);
    }

    /**
     * Complying to the SurfaceHolder.Callback interface
     *
     * @param holder SurfaceHolder
     * @param format int
     * @param width int
     * @param height int
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    /**
     * Complying to the SurfaceHolder.Callback interface
     *
     * @param holder SurfaceHolder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        stopThreads();
    }

    /**
     * Stops the game and render threads from running
     */
    public void stopThreads(){
        boolean retry = true;
        int counter = 0;
        while(retry && counter < 1000)
        {
            if(retry){
                try{
                    gameThread.setRunning(false);
                    gameThread.join();
                    renderThread.setRunning(false);
                    renderThread.join();
                    retry = false;

                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }

            counter++;
        }
    }

    /**
     * Create the level
     * @param holder SurfaceHolder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder){

        level = new Level(getResources());
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.ball), 50, 50);
        level.setPlayer(player);

        // Fill the level with obstacles
        // Patch 1
        level.addObstacle(1000, 400, 234);
        level.addObstacle(1400, 400, 134);
        level.addObstacle(2000, 100, 134);
        level.addObstacle(2500, 100, 234);
        // Patch 2
        level.addObstacle(2950, 100, 234);
        level.addObstacle(3200, 50, 184);
        level.addObstacle(3400, 50, 234);
        level.addObstacle(3600, 50, 100);
        // Patch 3
        level.addObstacle(4500, 50, 184);
        level.addObstacle(5500, 50, 184);
        level.addObstacle(5900, 50, 159);

        // Start the threads
        gameThread.setRunning(true);
        gameThread.start();
        renderThread.setRunning(true);
        renderThread.start();
    }

    /**
     * Handle touch events
     * @param event MotionEvent
     * @return super class
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // Make the player jump
        if(gameThread.getRunning()){
            player.jump();
        }

        return super.onTouchEvent(event);
    }

    /**
     * Update the game logic
     */
    public void update() {
        // Update the level
        level.update();

        // If the level, switch to the MainMenu activity
        if(level.isOver()){
            context.endScreen();
        }

        // If the player falls of the screen they are dead, change the activity to the death screen
        if(player.getY() > 480){
            context.deathScreen();
        }
    }

    /**
     * Scales the game widths and tells the level to draw
     *
     * @param canvas the game canvas
     */
    @Override
    public void draw(Canvas canvas){
        final float scaleFactorX = getWidth()/WIDTH;
        final float scaleFactorY = getHeight()/HEIGHT;
        // If the canvas exists
        if(canvas!=null) {
            // draw to and scale the canvas
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            level.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }
}