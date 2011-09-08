/**
 *
 */
package games.pack.protap;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
        RatePrompt.appLaunched(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetScores().execute();
    }

    private static final int MENU_NEW_GAME = Menu.FIRST;
    private static final int MENU_PRACTICE = Menu.FIRST + 1;
    private static final int MENU_HIGHSCORES = Menu.FIRST + 2;
    private static final int MENU_RESET = Menu.FIRST + 3;

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        menu.add(Menu.NONE, MENU_NEW_GAME, MENU_NEW_GAME, "New Game");
        menu.add(Menu.NONE, MENU_PRACTICE, MENU_PRACTICE, "Practice");
        menu.add(Menu.NONE, MENU_HIGHSCORES, MENU_HIGHSCORES, "High Scores");
        menu.add(Menu.NONE, MENU_RESET, MENU_RESET, "Reset High Scores");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case MENU_NEW_GAME:
                newGame();
                return true;
            case MENU_PRACTICE:
                practice();
                return true;
            case MENU_HIGHSCORES:
                highscores();
                return true;
            case MENU_RESET:
                new ResetScores().execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void newGame() {
        startActivity(new Intent(this, ReactionActivity.class));
    }

    private void highscores(){
        startActivity(new Intent(this, HighScoreActivity.class));
    }

    private void practice(){
        startActivity(new Intent(this, PracticeActivity.class));
    }

    public void onNewGameClick(View view) {
        newGame();
    }

    public void onHighScoreClick(View view) {
        highscores();
    }

    public void onPracticeClick(View view) {
        practice();
    }

    private final class GetScores extends RetrieveTopScore {
        public GetScores() {
            super(MainMenuActivity.this, BOTH);
        }

        @Override
        protected void onPostExecute(Integer[] result) {
            if (result != null) {
                ((TextView) findViewById(R.id.topBoxing)).setText("Boxing: " + result[BOXING]);
                ((TextView) findViewById(R.id.topReaction))
                        .setText("Reaction: " + result[REACTION]);
            }

        } // onPostExecute
    } // GetScores

    private final class ResetScores extends PostTopScore {
        public ResetScores() {
            super(MainMenuActivity.this, RESET);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            new GetScores().execute();
        }
    } // ResetScores
}
