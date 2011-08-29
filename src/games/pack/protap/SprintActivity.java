/**
 * 
 */
package games.pack.protap;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * @author Tariq
 * 
 */
public class SprintActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sprint);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;
    private Path mPath;
	private void touch_start(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}

	private void touch_up() {
		
		mPath.lineTo(mX, mY);
		// kill this so we don't double draw
		mPath.reset();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touch_start(x, y);
			break;
		case MotionEvent.ACTION_MOVE:
			touch_move(x, y);
			break;
		case MotionEvent.ACTION_UP:
			touch_up();
			break;
		}

		return true;
	}

}
