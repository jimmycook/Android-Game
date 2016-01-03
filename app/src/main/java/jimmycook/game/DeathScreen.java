package jimmycook.game;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Death screen android activity extension
 */
public class DeathScreen extends Activity {

    /**
     * Set the content view to the appropriate layout
     * when the activity is created
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_death_screen);
    }

    /**
     * Switch to the game activity
     * @param view View  Element that issues this action
     */
    public void startGame(View view){
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
        finish();
    }
}
