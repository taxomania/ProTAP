package games.pack.protap;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class Boxing extends Activity {
    static final long MILLIS_IN_FUTURE = 10000;
    static final long COUNTDOWN_INTERVAL = 100;
    private int mScore = 0;
    TextView mCounterView, mTimerView;
    CountDownTimer mTimer;

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
    }

    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    } // onDestroy()

    abstract void showStartDialog();

    public void addTap(final View view) {
        mScore++;
        mCounterView.setText(Integer.toString(mScore));
    } // addTap(View)

    abstract void end();

} // Boxing
