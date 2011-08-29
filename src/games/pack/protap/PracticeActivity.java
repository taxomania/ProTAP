package games.pack.protap;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class PracticeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void onBoxingClick(View view) {
        startActivity(new Intent(this, PracticeBoxingActivity.class));
    }

    public void onReactionClick(View view) {
        startActivity(new Intent(this, PracticeReactionActivity.class));
    }
}