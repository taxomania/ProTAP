/**
 *
 */
package games.pack.protap;

import games.pack.protap.localscore.PostTopScore;
import games.pack.protap.localscore.RetrieveTopScore;
import games.pack.protap.localscore.TopScorePrefs;
import games.pack.protap.upload.PostHighScore;
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (score > sHighScore) {
            builder.setTitle("NEW HIGH SCORE");
            sHighScore = score;
            new PostReactionTopScore(this).execute(sHighScore);
        } // if
        final Intent boxingIntent = new Intent(this, BoxingActivity.class);
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
        if (sHighScore > 0) {
            builder.setNeutralButton("Upload Score", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    new PostReactionHighScore(ReactionActivity.this, score).enterName();
                } // onClick(DialogInterface, int)
            });
        } // if
        builder.create().show();
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

    private final class PostReactionHighScore extends PostHighScore {
        private static final String URL = "http://pro-tap.appspot.com/postreaction";

        public PostReactionHighScore(final Context context, final int score) {
            super(context, score, URL);
        } // PostReactionHighScore(Context, int)
    } // PostReactionHighScore

} // ReactionActivity