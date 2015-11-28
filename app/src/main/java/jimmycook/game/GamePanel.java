package jimmycook.game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Jimmypq on 19/10/2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final float WIDTH = 856;
    public static final float HEIGHT = 480;
    private Game context;
    private MainThread gameThread;
    private Level level;
    private Player player;

    public GamePanel(Game context) {
        super(context);

        this.context = context;

        // add the callback to the surfaceholder to intecept events
        getHolder().addCallback(this);

        gameThread = new MainThread(getHolder(), this);

        // make gamepanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter = 0;
        while(retry && counter < 1000)
        {
            try{gameThread.setRunning(false);
                gameThread.join();
                retry = false;

            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        level = new Level(getResources());
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.ball), 50, 50);
        level.setPlayer(player);
        // Fill level
        int[][] blocks = new int[][]{
                { 300, 300 },
                { 350, 300 },
                { 400, 300 },
                { 450, 300 },
                { 500, 300 },
        };
        int platforms[];
        int obsticles[];

//        for (int i = 0; i < blocks.length; i ++){
//            level.addBlock(blocks[i][0], blocks[i][1]);
//        }

        level.addPlatform(1000, 280);
//        level.addPlatform(1250, 220);
        // Idea - level.addObsticle(startX, endX, y);
        level.addObsticle(1250, 234);
        level.addObsticle(1300, 234);
        level.addObsticle(1350, 234);
        level.addObsticle(1400, 234);
        level.addObsticle(1450, 234);
        level.addObsticle(1500, 234);
        level.addObsticle(1550, 234);
        level.addObsticle(1600, 234);
        level.addObsticle(1650, 234);
        level.addObsticle(1700, 234);

        // Start the thread
        gameThread.setRunning(true);
        gameThread.start();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        final float scaleFactorX = getWidth()/WIDTH;
        final float scaleFactorY = getHeight()/HEIGHT;


        Log.d("Touch Event", "X: " + scaleFactorX + " Y: " + scaleFactorY);
        if(gameThread.getRunning()){
            player.jump();
        }

        return super.onTouchEvent(event);

    }

    public void update() {
        level.update();
    }

    @Override
    public void draw(Canvas canvas){
        final float scaleFactorX = getWidth()/WIDTH;
        final float scaleFactorY = getHeight()/HEIGHT;
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            level.draw(canvas);

            canvas.restoreToCount(savedState);
        }
    }
}