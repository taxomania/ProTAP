/**
 *
 */
package games.pack.protap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * @author Tariq Patel
 *
 */
public class ReactionActivity extends PracticeReactionActivity {
    private static final String PREFERENCES_NAME = "ProTAP_Prefs";
    private static final String PREFERENCES_HIGHSCORE = "reaction_score";
    private static SharedPreferences.Editor prefsEditor;
    private static int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        prefsEditor = prefs.edit();
        highScore = prefs.getInt(PREFERENCES_HIGHSCORE, 0);
    }

    @Override
    protected void showCompleteAlert() {
        final int score = score(mTime);
        final Intent boxingIntent = new Intent(this, BoxingActivity.class);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your time: " + mTime + "ms\nYour score: " + score)
                .setCancelable(false)
                .setPositiveButton("Continue to Boxing", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        end(boxingIntent);
                    }
                }).setNegativeButton("Quit to Main Menu", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
        if (score > highScore) {
            highScore = score;
            prefsEditor.putInt(PREFERENCES_HIGHSCORE, highScore);
            prefsEditor.commit();
            PostHighScore newHighscore = new PostHighScore(ReactionActivity.this, "reaction", score);
            newHighscore.enterName();
        }
    }
}