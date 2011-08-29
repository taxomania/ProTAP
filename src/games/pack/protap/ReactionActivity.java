/**
 * 
 */
package games.pack.protap;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.os.CountDownTimer;

/**
 * @author Tariq Patel
 * 
 */
public class ReactionActivity extends Activity {
	ImageView buttonImage;
	private static final String PREFERENCES_NAME = "ProTAP_Prefs";
	private static final String PREFERENCES_HIGHSCORE = "reaction_score";
	private static SharedPreferences.Editor prefsEditor;
	static int highScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reaction);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		buttonImage = (ImageView) findViewById(R.id.red_button);
		SharedPreferences prefs = getSharedPreferences(PREFERENCES_NAME,
				MODE_PRIVATE);
		prefsEditor = prefs.edit();
		highScore = prefs.getInt(PREFERENCES_HIGHSCORE, 0);
	}

	@Override
	protected void onStart() {
		super.onStart();
		long countdownTime1 = (long) (3000 * Math.random());
		if (countdownTime1 < 2000)
			countdownTime1 = 2000;
		final long countdownTime = countdownTime1;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Press the button when it goes green. Press GO to start")
				.setCancelable(false)
				.setPositiveButton("GO", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						new CountDownTimer(countdownTime, 100) {
							@Override
							public void onFinish() {
								changeBackground();
							}

							@Override
							public void onTick(long millisUntilFinished) {
								// TODO Auto-generated method stub

							}
						}.start();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private static long start;

	protected void changeBackground() {
		setContentView(R.layout.reaction_green);
		start = Calendar.getInstance().getTimeInMillis();
	}

	private long time = 0;

	public void fail(View view) {
		time += 2000;
	}

	static int score;

	public void complete(View view) {
		long finish = Calendar.getInstance().getTimeInMillis();
		time += (finish - start);
		score = score(time);
		final Intent boxingIntent = new Intent(this, BoxingActivity.class);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Your time: " + getTime() + "ms\nYour score: " + score)
				.setCancelable(false)
				.setPositiveButton("Continue to Boxing",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								end(boxingIntent);
							}
						})
				.setNegativeButton("Quit to Main Menu",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
							}
						});

		AlertDialog alert = builder.create();
		alert.show();
		if (score > highScore) {
			highScore = score;
			prefsEditor.putInt(PREFERENCES_HIGHSCORE, highScore);
			prefsEditor.commit();
			PostHighScore newHighscore = new PostHighScore(
					ReactionActivity.this, "reaction", score);
			newHighscore.enterName();
		}
	}

	protected void end(Intent intent) {
		finish();
		startActivity(intent);
	}

	private static int score(long time) {
		// TODO Auto-generated method stub
		if (time > 500)
			return 0;
		return (int) (500 - time);
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

}