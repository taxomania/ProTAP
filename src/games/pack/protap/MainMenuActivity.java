/**
 *
 */
package games.pack.protap;

import games.pack.protap.localscore.RetrieveTopScore;
import games.pack.protap.localscore.TopScorePrefs;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
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
    } // onCreate(Bundle)

    @Override
    protected void onResume() {
        super.onResume();
        new GetScores(this).execute();
    } // onResume()

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
    } // onCreateOptionsMenu(Menu)

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
                new ResetTopScore(this).execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // switch
    } // onOptionsItemSelected(MenuItem)

    private void startActivity(final Class<?> className) {
        startActivity(new Intent(this, className));
    } // startActivity(Class)

    private void newGame() {
        startActivity(ReactionActivity.class);
    } // newGame()

    private void highscores() {
        startActivity(HighScoreActivity.class);
    } // highscores()

    private void practice() {
        startActivity(PracticeActivity.class);
    } // practice()

    public void onNewGameClick(final View view) {
        newGame();
    } // onNewGameClick(View)

    public void onHighScoreClick(final View view) {
        highscores();
    } // onHighScoreClick(View)

    public void onPracticeClick(final View view) {
        practice();
    } // onPracticeClick(View)

    private final class GetScores extends RetrieveTopScore {
        public GetScores(final Context context) {
            super(context);
        } // GetScores(Context)

        @Override
        protected Integer[] doTask(final TopScorePrefs prefs) {
            return new Integer[] { prefs.getBoxingScore(), prefs.getReactionScore() };
        } // doTask(TopScorePrefs)

        @Override
        protected void setResult(Integer[] result) {
            ((TextView) findViewById(R.id.topBoxing)).setText("Boxing: " + result[0]);
            ((TextView) findViewById(R.id.topReaction)).setText("Reaction: " + result[1]);
        } // setResult(Integer[])
    } // GetScores

    private final class ResetTopScore extends AsyncTask<Void, Void, Boolean> {
        private final Context mCtx;

        public ResetTopScore(final Context context) {
            mCtx = context;
        } // ResetTopScore(Context)

        @Override
        protected Boolean doInBackground(final Void... noParams) {
            return new TopScorePrefs(mCtx).edit().reset();
        } // doInBackground(Void...)

        @Override
        protected void onPostExecute(final Boolean result) {
            new GetScores(mCtx).execute();
        } // onPostExecute(Boolean)
    } // ResetTopScore
} // MainMenuActivity
