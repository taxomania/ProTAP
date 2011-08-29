
/**
 * 
 */
package games.pack.protap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

/**
 * @author Tariq
 * 
 */
public class BoxingActivity extends Activity {
	private static final String PREFERENCES_NAME = "ProTAP_Prefs";
	private static final String PREFERENCES_HIGHSCORE = "boxing_score";
	private static SharedPreferences.Editor prefsEditor;
	static int highScore;

	TextView timerView,counterView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boxing);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		timerView = (TextView) findViewById(R.id.timer1);
		counterView = (TextView) findViewById(R.id.boxing_counter1);
		SharedPreferences prefs = getSharedPreferences(PREFERENCES_NAME,
				MODE_PRIVATE);
		prefsEditor = prefs.edit();
		highScore = prefs.getInt(PREFERENCES_HIGHSCORE, 0);
	}

	private int score = 0;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Tap the ball as many times as you can in 10s. Press GO to start")
				.setCancelable(false)
				.setPositiveButton("GO", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						new CountDownTimer((long) (10000), 100) {
							@Override
							public void onFinish() {
								// TODO Auto-generated method stub
								timerView.setText("0.0");
								end();
							}

							@Override
							public void onTick(long millisUntilFinished) {
								// TODO Auto-generated method stub
								timerView.setText(""+(double)millisUntilFinished / 1000);

							}
						}.start();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	protected void end() {
		final Intent menuIntent = new Intent(this, MainMenuActivity.class);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Your score: " + score)
				.setCancelable(false)
				.setPositiveButton("Continue",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								endGame(menuIntent);
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
		if (score > highScore) {
			highScore = score;
			prefsEditor.putInt(PREFERENCES_HIGHSCORE, highScore);
			prefsEditor.commit();
			PostHighScore newHighscore = new PostHighScore(
					BoxingActivity.this, "boxing", score);
			newHighscore.enterName();
		}
	}

	public void addTap(View view) {
		score++;
		counterView.setText(""+score);
	}

	public void endGame(Intent intent) {
		// TODO Auto-generated method stub
		finish();
	}
}
