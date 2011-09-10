/**
 *
 */
package games.pack.protap;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

/**
 * @author Tariq
 *
 */
public class PracticeReactionActivity extends Activity {
    private long mStart;
    private CountDownTimer mTimer;
    protected long mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reaction);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final double countdownTime1 = 3000 * Math.random();
        final long countdownTime = (countdownTime1 < 2000) ? 2000 : (long) countdownTime1;
        mTimer = new CountDownTimer(countdownTime, 100) {
            @Override
            public void onFinish() {
                changeBackground();
            } // onFinish()

            @Override
            public void onTick(final long millisUntilFinished) {
            } // onTick(long)
        };
        showStartDialog();
    } // onCreate

    void showStartDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Press the button when it goes green. Press GO to start")
                .setCancelable(false)
                .setPositiveButton("GO", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        mTimer.start();
                    }
                }).create().show();
    } // showStartDialog()

    protected void changeBackground() {
        setContentView(R.layout.reaction_green);
        mStart = Calendar.getInstance().getTimeInMillis();
    }

    public void complete(final View view) {
        final long finish = Calendar.getInstance().getTimeInMillis();
        mTime += (finish - mStart);
        showCompleteAlert();
    }

    protected void showCompleteAlert() {
        final Intent retryIntent = new Intent(this, PracticeReactionActivity.class);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your time: " + mTime + "ms\nYour score: " + score(mTime))
                .setCancelable(true)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        end(retryIntent);
                    }
                }).setNegativeButton("Return to Practice", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finish();
                    }
                });
        builder.create().show();
    }

    protected final void end(final Intent intent) {
        finish();
        startActivity(intent);
    }

    public final void fail(final View view) {
        mTime += 2000;
    }

    protected final int score(long time) {
        return (time > 500) ? 0 : (int) (500 - time);
    }

    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    } // onDestroy()
} // PracticeReactionActivity