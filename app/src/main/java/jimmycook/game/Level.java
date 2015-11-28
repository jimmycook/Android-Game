package jimmycook.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jimmycook on 23/11/2015.
 */
public class Level {
    private Background background;
    private Player player;
    private Floor floor;
    private ArrayList<Wall> gameObjects;
    private Resources resources;

    public Level(Player p, Background b, Floor f, ArrayList<Wall> gameObjects){
        this.player = p;
        this.background = b;
        this.floor = f;
        this.gameObjects = gameObjects;
    }

    public Level(Resources resources){
        this.resources = resources;
        background = new Background(BitmapFactory.decodeResource(resources, R.drawable.background));
        background.setVector(-4);
        floor = new Floor(BitmapFactory.decodeResource(this.resources, R.drawable.floor), 66, 40);
        gameObjects = new ArrayList<>();
    }

    public void update(){
        background.update();
        int current_floor = floor.getFloorY(50);
//        Iterator<Wall> it = gameObjects.iterator();
//        while(it.hasNext()){
//            Wall e = it.next();
////            e.update();
//        }
        player.update(1.1, floor.getFloorY(player.getHeight()), gameObjects);
    }

    public void setPlayer(Player p ){
        this.player = p;
    }

    public void draw(Canvas canvas){

        background.draw(canvas);
        floor.draw(canvas);
        Iterator<Wall> it = gameObjects.iterator();
        while(it.hasNext()){
            Wall e = it.next();
            e.draw(canvas);
        }
        player.draw(canvas);
    }

    public void addBlock(int x, int y){
        Bitmap platform = BitmapFactory.decodeResource(resources, R.drawable.platformblock);
        Wall w = new Wall(platform, platform.getWidth(), platform.getHeight());
        w.setY(y);
        w.setX(x);
        gameObjects.add(w);
    }

    public void addPlatform(int x, int y){
        Bitmap platform = BitmapFactory.decodeResource(resources, R.drawable.platform);
        Wall w = new Wall(platform, platform.getWidth(), platform.getHeight());
        w.setY(y);
        w.setX(x);
        gameObjects.add(w);
    }

    public void addObsticle(int x, int y){
        Bitmap block = BitmapFactory.decodeResource(resources, R.drawable.obsticle);
        Wall w = new Wall(block, block.getWidth(), block.getHeight());
        w.setY(y);
        w.setX(x);
        gameObjects.add(w);
    }

    public void addSmallObsticle(int x, int y){
        Bitmap block = BitmapFactory.decodeResource(resources, R.drawable.smallobsticle);
        Wall w = new Wall(block, block.getWidth(), block.getHeight());
        w.setY(y);
        w.setX(x);
        gameObjects.add(w);
    }

    public void init(){

    }
}
