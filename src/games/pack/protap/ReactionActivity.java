/**
 *
 */
package games.pack.protap;

import games.pack.protap.upload.PostReactionHighScore;
import android.app.AlertDialog;
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

        new GetReaction().execute();
    }

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
            new PostTopScore(this, PostTopScore.REACTION).execute(sHighScore);
            new PostReactionHighScore(ReactionActivity.this, score).enterName();
        }
    } // showCompleteAlert

    private final class GetReaction extends RetrieveTopScore {
        public GetReaction() {
            super(ReactionActivity.this, REACTION);
        }

        @Override
        protected void onPostExecute(final Integer[] result) {
            if (result[0] != null) sHighScore = result[0];
        }

    }
} // ReactionActivity