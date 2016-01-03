package jimmycook.game;

import android.graphics.Rect;

/**
 * Game entity abstract class
 */
public abstract class Entity {
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected int height;
    protected int width;

    /** Getters and setters */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDx(int dx){
        this.dx = dx;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rect getRectangle(){
        return new Rect(x, y, x+width, y+height);
    }
}