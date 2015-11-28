package jimmycook.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Jimmypq on 22/10/2015.
 */
public class Player extends Entity {
    private Bitmap image;
    private int score;
    private double dya;
    private boolean playing;
    private boolean jumping;
    private long startTime;

    Player(Bitmap res, int w, int h){
        x = 125;
        y = 284;
        dy = 0;
        score = 0;
        width = w;
        jumping = false;
        height = h;
        image = res;
        startTime = System.nanoTime();
        playing = true;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }

//    public void update(ArrayList<Entity> entities) {
    public boolean update(double gravity, int floor, ArrayList<Wall> entities) {

        int base_floor = floor;

        if(playing){
            long elapsed = (System.nanoTime() - startTime / 1000000);
            int ceiling = -500;
            if (elapsed > 100) {
                score++;
                startTime = System.nanoTime();
            }

            Iterator<Wall> it = entities.iterator();

            while(it.hasNext()){
                Wall e = it.next();
                e.update();
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
                        if (e.colides(this)) playing = false;
                    }

                    if(y >= endY && ceiling < endY) {
                        ceiling = endY;
                    }

                    // Do a collision check
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
        if(playing == false && y < 480) {
            dya += gravity;
            y += (int) dya * 2;
        }
        else if (playing == false) {
            return false;
        }
        return true;
    }

    public void jump(){
        if(!jumping){
            jumping = true;
            dya = -14;
        }
    }
}
