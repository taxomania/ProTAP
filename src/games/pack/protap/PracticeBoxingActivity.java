/**
 * 
 */
package games.pack.protap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author Tariq
 * 
 */
public class PracticeBoxingActivity extends BoxingActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	private int score = 0;

	public void addTap(View view) {
		score++;
		super.counterView.setText(""+score);
	}

	@Override
	public void end() {
		final Intent retryIntent = new Intent(this,
				PracticeBoxingActivity.class);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Your score: " + score)
				.setCancelable(false)
				.setPositiveButton("Retry",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								endGame(retryIntent);
							}
						})
				.setNegativeButton("Return to Practice",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								finish();
							}
						});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public void endGame(Intent intent) {
		super.endGame(intent);
	}
}
