package jimmycook.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Jimmypq on 02/11/2015.
 */
public class Wall extends Entity {

    protected Bitmap image;
    protected long startTime;

    Wall(Bitmap res, int w, int h){
        super.x = 856;
        super.y = 234;
        dx = -6;
        width = w;
        height = h;
        image = res;
        startTime = System.nanoTime();
    }

    public void update(){
        y += dy*2;
        x += dx*2;
        // if ( x < (0 - width) ) x = 856;
    }

    public boolean colides(Player player) {
        return Rect.intersects(this.getRectangle(), player.getRectangle());
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }

    public boolean remove(){
        if (this.x < 0 - this.width) {
            return true;
        }
        else {
            return false;
        }
    }
}