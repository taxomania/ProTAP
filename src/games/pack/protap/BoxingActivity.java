/**
 *
 */
package games.pack.protap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * @author Tariq
 *
 */
public class BoxingActivity extends PracticeBoxingActivity {
    private static final String PREFERENCES_NAME = "ProTAP_Prefs";
    private static final String PREFERENCES_HIGHSCORE = "boxing_score";
    private static SharedPreferences.Editor prefsEditor;
    static int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        prefsEditor = prefs.edit();
        highScore = prefs.getInt(PREFERENCES_HIGHSCORE, 0);
    }

    @Override
    protected void end() {
        final int finalScore = Integer.parseInt(mCounterView.getText().toString());
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your score: " + finalScore).setCancelable(true)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
        if (finalScore > highScore) {
            highScore = finalScore;
            prefsEditor.putInt(PREFERENCES_HIGHSCORE, highScore);
            prefsEditor.commit();
            PostHighScore newHighscore = new PostHighScore(BoxingActivity.this, "boxing",
                    finalScore);
            newHighscore.enterName();
        }
    }

}
