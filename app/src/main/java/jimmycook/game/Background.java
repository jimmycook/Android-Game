package jimmycook.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Game Background
 */
public class Background {

    private Bitmap image;
    private int x, y, dx;

    /**
     * Contructor
     * @param res Bitmap background image
     */
    public Background(Bitmap res){
        image = res;
    }

    /**
     * Updates the position of the background image by the vector (dx)
     */
    public void update(){
        x+=dx;
        if(x< -GamePanel.WIDTH){
            x = 0;
        }
    }

    /**
     * Draws the image to the canvas, will also draw the looping half of the background
     * @param canvas Canvas
     */
    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
        if(x<0){
            canvas.drawBitmap(image, x+GamePanel.WIDTH, y, null);
        }
    }

    /**
     * Sets the vector
     * @param dx int  the new vector
     */
    public void setVector(int dx){
        this.dx = dx;
    }
}