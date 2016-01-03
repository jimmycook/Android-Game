package jimmycook.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

/**
 * Wall game object
 */
public class Wall extends Entity {

    protected Bitmap image;
    protected long startTime;

    /**
     * Constructor
     * @param res Bitmap
     * @param w int
     * @param h int
     */
    Wall(Bitmap res, int w, int h){
        super.x = 856;
        super.y = 234;
        dx = -6;
        width = w;
        height = h;
        image = res;
        startTime = System.nanoTime();
    }

    /**
     * Constructor
     * @param end_x int
     * @param x int
     * @param y int
     */
    Wall(int end_x, int x, int y){
        width = end_x - x;
        height = 500;
        image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        image.eraseColor(Color.argb(255, 234, 162, 0));
        super.x = x;
        super.y = y;
        dx = -6;
        startTime = System.nanoTime();
    }

    /**
     * Update the position of the game object
     */
    public void update(){
        y += dy*2;
        x += dx*2;
    }

    /**
     * Check of the player object collides with this object
     * @param player Player
     * @return boolean
     */
    public boolean colides(Player player) {
        return Rect.intersects(this.getRectangle(), player.getRectangle());
    }

    /**
     * Draw the bitmap to the canvas
     * @param canvas Canvas
     */
    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }
}