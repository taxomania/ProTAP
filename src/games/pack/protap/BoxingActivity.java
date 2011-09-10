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
import android.os.Bundle;

/**
 * @author Tariq
 *
 */
public class BoxingActivity extends PracticeBoxingActivity {
    private static int sHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new RetrieveBoxingTopScore(this).execute();
    } // onCreate(Bundle)

    @Override
    public final void end() {
        final int finalScore = Integer.parseInt(mCounterView.getText().toString());
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (finalScore > 0) {
            builder.setNeutralButton("Upload Score", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    new PostBoxingHighScore(BoxingActivity.this, finalScore).enterName();
                } // onClick(DialogInterface, int)
            });
            if (finalScore > sHighScore) {
                sHighScore = finalScore;
                new PostBoxingTopScore(this).execute(sHighScore);
                builder.setTitle("NEW HIGH SCORE!");
            } // if
        } // if

        builder.setMessage("Your score: " + finalScore).setCancelable(true)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finish();
                    }
                }).create().show();
    } // end

    private final class RetrieveBoxingTopScore extends RetrieveTopScore {
        public RetrieveBoxingTopScore(final Context context) {
            super(context);
        } // RetrieveReactionTopScore(Context)

        @Override
        protected Integer[] doTask(final TopScorePrefs prefs) {
            return new Integer[] { prefs.getBoxingScore() };
        } // doTask(TopScorePrefs)

        @Override
        protected void setResult(Integer[] result) {
            if (result[0] != null) {
                sHighScore = result[0];
            } // if
        } // setResult(Integer[])
    } // RetrieveReactionTopScore

    private final class PostBoxingTopScore extends PostTopScore {
        public PostBoxingTopScore(final Context context) {
            super(context);
        } // PostBoxingTopScore(Context)

        @Override
        protected Boolean postTask(final TopScorePrefs.Editor edit, final Integer score) {
            return edit.putBoxingScore(score).commit();
        } // postTask(TopScorePrefs.Editor, Integer)
    } // PostBoxingTopScore

    private final class PostBoxingHighScore extends PostHighScore {
        private static final String URL = "http://pro-tap.appspot.com/postboxing";

        public PostBoxingHighScore(final Context context, final int score) {
            super(context, score, URL);
        } // PostBoxingHighScore(Context, int)
    } // PostBoxingHighScore
} // BoxingActivity
