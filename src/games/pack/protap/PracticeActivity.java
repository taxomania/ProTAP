package games.pack.protap;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.ads.*;

public class PracticeActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.practice);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    AdView adView = new AdView(this, AdSize.BANNER, "a14e0d0fe7d6087");
	    LinearLayout layout = (LinearLayout)findViewById(R.id.practiceLayout);
	    layout.addView(adView, layout.getBottom());
	    adView.loadAd(new AdRequest());
	
	}
	
/*	public void onSprintClick(View view){
		Intent sprintIntent = new Intent(this, PracticeSprintActivity.class);
		startActivity(sprintIntent);
	}*/
/*	public void onArcheryClick(View view){
		Intent archeryIntent = new Intent(this, PracticeArcheryActivity.class);
		startActivity(archeryIntent);
	}*/
	public void onBoxingClick(View view){
		Intent boxingIntent = new Intent(this, PracticeBoxingActivity.class);
		startActivity(boxingIntent);
	}
	public void onReactionClick(View view){
		Intent reactionIntent = new Intent(this, PracticeReactionActivity.class);
		startActivity(reactionIntent);
	}
/*	public void onPenaltyClick(View view){
		Intent penaltyIntent = new Intent(this, PracticePenaltyActivity.class);
		startActivity(penaltyIntent);
	}*/
}