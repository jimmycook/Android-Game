package jimmycook.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Level class
 */
public class Level {
    private Background background;
    private Player player;
    private Floor floor;
    private ArrayList<Wall> gameObjects;
    private Resources resources;
    private End end;

    /**
     * Constructor
     * @param resources Resources
     */
    public Level(Resources resources){
        this.resources = resources;
        background = new Background(BitmapFactory.decodeResource(resources, R.drawable.background));
        background.setVector(-4);
        floor = new Floor(BitmapFactory.decodeResource(this.resources, R.drawable.floor), 66, 40);
        gameObjects = new ArrayList<>();
        end = new End(6100, -6);
    }

    /**
     * Update the level
     */
    public void update(){
        background.update();
        player.update(1.1, floor.getFloorY(player.getHeight()), gameObjects);
        end.update();
    }

    /**
     * Set the player for the level
     * @param p Player
     */
    public void setPlayer(Player p ){
        this.player = p;
    }

    /**
     * Draw the bitmaps to the level
     * @param canvas Canvas
     */
    public void draw(Canvas canvas){
        // Draw the background
        background.draw(canvas);

        // Iterate through the game objects
        Iterator<Wall> it = gameObjects.iterator();
        while(it.hasNext()){
            Wall e = it.next();

            // If the object is on screen draw it
            if(e.getX() < 856 && e.getX() > 0 - e.getWidth()) {
                e.draw(canvas);
            }
        }
        // Draw the floor
        floor.draw(canvas);
        // Draw the player
        player.draw(canvas);
    }

    /**
     * Checks if the game has ended
     * @return boolean
     */
    public boolean isOver(){
        if(end.getX() < 0){
            return true;
        }
        return false;
    }

    /**
     * Add an obstacle
     * @param x int
     * @param width int
     * @param y int
     */
    public void addObstacle(int x, int width, int y){
        Wall w = new Wall(x + width , x, y);
        gameObjects.add(w);
    }
}
