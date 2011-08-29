/**
 *
 */
package games.pack.protap;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

/**
 * @author Tariq Patel
 *
 */
public class MainMenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void onNewGameClick(View view) {
        startActivity(new Intent(this, ReactionActivity.class));
    }

    public void onHighScoreClick(View view) {
        startActivity(new Intent(this, HighScoreActivity.class));
    }

    public void onPracticeClick(View view) {
        startActivity(new Intent(this, PracticeActivity.class));
    }

    // TODO : Add options menu, show high scores? reset high scores
}
