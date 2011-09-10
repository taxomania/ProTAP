/**
 *
 */
package games.pack.protap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Tariq
 *
 */
public class PracticeBoxingActivity extends Activity {
    static final long MILLIS_IN_FUTURE = 10000;
    static final long COUNTDOWN_INTERVAL = 100;
    private int mScore = 0;
    TextView mCounterView;
    private TextView mTimerView;
    private CountDownTimer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boxing);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mTimerView = (TextView) findViewById(R.id.timer1);
        mCounterView = (TextView) findViewById(R.id.boxing_counter1);
        final ImageView iv = (ImageView) findViewById(R.id.imageView2);
        mTimer = new CountDownTimer(MILLIS_IN_FUTURE, COUNTDOWN_INTERVAL) {
            @Override
            public void onFinish() {
                mTimerView.setText("0.0");
                iv.setClickable(false);
                end();
            } // onFinish()

            @Override
            public void onTick(final long millisUntilFinished) {
                mTimerView.setText(Double.toString((double) millisUntilFinished / 1000));
            } // onTick(long)
        };
        showStartDialog();
    } // onCreate(Bundle)

    void showStartDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Tap the ball as many times as you can in 10s. Press GO to start")
                .setCancelable(false)
                .setPositiveButton("GO", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        mTimer.start();
                    } // onClick
                }).create().show();
    } // showStartDialog()

    void end() {
        final int finalScore = Integer.parseInt(mCounterView.getText().toString());
        new AlertDialog.Builder(this).setMessage("Your score: " + finalScore).setCancelable(true)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(PracticeBoxingActivity.this,
                                PracticeBoxingActivity.class));
                        finish();
                    }
                }).setNegativeButton("Return to Practice", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finish();
                    }
                }).create().show();
    } // end()

    public void addTap(final View view) {
        mScore++;
        mCounterView.setText(Integer.toString(mScore));
    } // addTap(View)

    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    } // onDestroy()
} // PracticeBoxingActivity
