package jimmycook.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Player entity
 */
public class Player extends Entity {
    private Bitmap image;
    private double dya;
    private boolean playing;
    private boolean jumping;
    private long startTime;
    private boolean queuedJump;

    /**
     * Player constructor
     * @param res Bitmap player sprite
     * @param w int player width
     * @param h int player height
     */
    Player(Bitmap res, int w, int h){
        // Initialise values
        x = 125;
        y = 284;
        dy = 0;
        width = w;
        jumping = false;
        height = h;
        image = res;
        startTime = System.nanoTime();
        playing = true;
    }

    /**
     * Draw method for the player
     * @param canvas Canvas
     */
    public void draw(Canvas canvas){
        // Draw the player sprite
        canvas.drawBitmap(image, x, y, null);
    }

    /**
     * Player update method
     * @param gravity double strength of gravity
     * @param floor int floor Y level
     * @param entities ArrayList<Wall> walls to check for colision with
     * @return
     */
    public boolean update(double gravity, int floor, ArrayList<Wall> entities) {
        // If the player is playing
        if(playing){
            long elapsed = (System.nanoTime() - startTime / 1000000);
            int ceiling = -500;
            if (elapsed > 100) {
                startTime = System.nanoTime();
            }
            // If there's a jump queued, jump
            if(!jumping && queuedJump){
                jump();
            }

            // Iterate through the game objects
            Iterator<Wall> it = entities.iterator();
            while(it.hasNext()){
                // Get the wall object
                Wall e = it.next();
                // Update the wall object
                e.update();

                // Create a rectangle with the X co-ordinates of the player
                Rect rect = new Rect(x, 0, x+height , 1000);
                int startY = e.getRectangle().top;
                int endY = e.getRectangle().bottom;

                // If the player lines up with this object on the X axis
                if(e.getRectangle().intersect(rect)){
                    int new_floor = startY - height;
                    // If the new floor is under the player
                    if( y <= new_floor){
                        // Check if the new floor is closer to the player than the old floor
                        if(new_floor < floor)
                            floor = new_floor;
                    }
                    else {
                        // If the player collides with an object then they are not longer playing
                        if (e.colides(this)) playing = false;
                    }

                    // If the current ceiling isn't the next object that should be the ceiling
                    if(y >= endY && ceiling < endY) {
                        ceiling = endY;
                    }
                }
                else {
                    jumping = true;
                }
            }

            // Player jumping related code
            if(jumping || y != floor) {
                if( y <= floor){

                    // Increment the y co-ordinates by the players velocity
                    int new_y = y + (int) dya * 2;
                    // Check for ceilings
                    if( new_y <= ceiling) {
                        new_y = ceiling;
                        dya = 0;
                    }
                    // Apply new co-ordinates
                    y = new_y;

                    dya += gravity;
                    // Check if the player has fallen
                    if(y >= floor) {
                        // Save them and say that they're not jumping anymore
                        y = floor;
                        jumping = false;
                        dya = 0;
                    }
                }
            }
        }
        // If the player isn't playing and isn't on the ground
        if(!playing && y < 480) {
            // Apply gravity
            dya += gravity;
            y += (int) dya * 2;
        }
        // return false if the player isn't playing
        else if (!playing) {
            return false;
        }
        return true;
    }

    /**
     * Make the player jump
     */
    public void jump(){
        // If they aren't already jumping
        if(!jumping){
            queuedJump = false;
            // Set jumping to true
            jumping = true;
            // Set the vector
            dya = -14;
        }
        // Otherwise queue a jump
        else {
            queuedJump = true;
        }
    }
}
