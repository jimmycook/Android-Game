package jimmycook.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Jimmypq on 01/11/2015.
 */
public class Floor extends Entity {
    private Bitmap image;
    private int floor_y;

    Floor(Bitmap res, int w, int h){
        super.x = 0;
        super.y = 334;
        dy = 0;
        width = w; height = h;
        image = res;
    }

    public int getFloorY(int h) {
        return y-h;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

}