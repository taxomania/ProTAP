/**
 *
 */
package games.pack.protap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Tariq
 *
 */
public class PracticeBoxingActivity extends Boxing {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStartDialog();
    }

    @Override
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

    @Override
    void end() {
        final int finalScore = Integer.parseInt(mCounterView.getText().toString());
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your score: " + finalScore).setCancelable(true)
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
                });
        builder.create().show();
    } // end()
} // PracticeBoxingActivity
