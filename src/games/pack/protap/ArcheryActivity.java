package games.pack.protap;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class ArcheryActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.archery);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
}
