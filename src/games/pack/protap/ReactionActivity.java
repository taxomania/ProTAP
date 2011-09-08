/**
 *
 */
package games.pack.protap;

import games.pack.protap.localscore.PostTopScore;
import games.pack.protap.localscore.RetrieveTopScore;
import games.pack.protap.localscore.TopScorePrefs;
import games.pack.protap.upload.PostReactionHighScore;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Tariq Patel
 *
 */
public class ReactionActivity extends PracticeReactionActivity {
    private static int sHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new RetrieveReactionTopScore(this).execute();
    } // onCreate(Bundle)

    @Override
    protected void showCompleteAlert() {
        final int score = score(mTime);
        final Intent boxingIntent = new Intent(this, BoxingActivity.class);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your time: " + mTime + "ms\nYour score: " + score).setCancelable(false)
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
        if (score > sHighScore) {
            sHighScore = score;
            new PostReactionTopScore(ReactionActivity.this).execute(sHighScore);
            new PostReactionHighScore(ReactionActivity.this, score).enterName();
        }
    } // showCompleteAlert

    private final class RetrieveReactionTopScore extends RetrieveTopScore {
        public RetrieveReactionTopScore(final Context context) {
            super(context);
        } // RetrieveReactionTopScore(Context)

        @Override
        protected Integer[] doTask(final TopScorePrefs prefs) {
            return new Integer[] { prefs.getReactionScore() };
        } // doTask(TopScorePrefs)

        @Override
        protected void setResult(Integer[] result) {
            if (result[0] != null) {
                sHighScore = result[0];
            } // if
        } // setResult(Integer[])
    } // RetrieveReactionTopScore

    private final class PostReactionTopScore extends PostTopScore {
        public PostReactionTopScore(final Context context) {
            super(context);
        } // PostReactionTopScore

        @Override
        protected Boolean postTask(final TopScorePrefs.Editor edit, final Integer score) {
            return edit.putReactionScore(score).commit();
        } // postTask(TopScorePrefs.Editor, Integer)
    } // PostReactionTopScore

} // ReactionActivity