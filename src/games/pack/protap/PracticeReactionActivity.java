/**
 *
 */
package games.pack.protap;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

/**
 * @author Tariq
 *
 */
public class PracticeReactionActivity extends ReactionActivity {
    private static long start;

    protected void changeBackground() {
        super.changeBackground();
        start = Calendar.getInstance().getTimeInMillis();
    }

    private long time;

    @Override
    public void complete(View view) {
        long finish = Calendar.getInstance().getTimeInMillis();
        time += (finish - start);
        final Intent retryIntent = new Intent(this, PracticeReactionActivity.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your time: " + getTime() + "ms\nYour score: " + score(time))
                .setCancelable(false)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        end(retryIntent);
                    }
                }).setNegativeButton("Return to Practice", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void fail(View view) {
        time += 2000;
    }

    private static int score(long time) {
        if (time > 500) return 0;
        return (int) (500 - time);
    }

    public long getTime() {
        return time;
    }

}
