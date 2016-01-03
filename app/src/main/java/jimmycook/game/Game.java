package jimmycook.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Game Activity Class
 *
 * Doesn't use standard android view, uses a custom game surface view
 */
public class Game extends Activity {

    /**
     * Sets the application to full screen and sets the content
     * view to the GamePanel class
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new GamePanel(this));
    }

    /**
     * Change activity to the end screen activity
     */
    public void endScreen(){
        Intent intent = new Intent().setClass(this, EndScreen.class);
        startActivity(intent);
        finish();
    }

    /**
     * Change the activity to the death screen activity
     */
    public void deathScreen() {
        Intent intent = new Intent().setClass(this, DeathScreen.class);
        this.startActivity(intent);
        finish();
    }
}