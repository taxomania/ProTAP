/**
 * 
 */
package games.pack.protap;

import com.google.ads.*;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author Tariq Patel
 *
 */
public class MainMenuActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

	    AdView adView = new AdView(this, AdSize.BANNER, "a14e0d0fe7d6087");
	    RelativeLayout layout = (RelativeLayout)findViewById(R.id.mainLayout);
	    layout.addView(adView);
	    adView.loadAd(new AdRequest());
	}

	public void onNewGameClick(View view){startActivity(new Intent(this, ReactionActivity.class));}
	public void onHighScoreClick(View view){startActivity(new Intent(this, HighScoreActivity.class));}
	public void onPracticeClick(View view) {startActivity(new Intent(this, PracticeActivity.class));}
	public void onQuitClick(View view){finish();}
}
