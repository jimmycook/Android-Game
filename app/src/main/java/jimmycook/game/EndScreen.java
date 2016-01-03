package jimmycook.game;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * End of game activity
 */
public class EndScreen extends Activity {

    /**
     * When the activity is created
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_end_screen);
    }

    public void mainMenu(View view){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }
}
