package games.pack.protap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class PostHighScore {
	private String username, entity;
	private int score;
	private Context con;
	
	public PostHighScore(Context c, String e, int s){
		entity = e;
		score = s;
		con = c;
	}
	public void enterName()
    {
		AlertDialog.Builder builder = new AlertDialog.Builder(con);
		builder.setTitle("NEW HIGHSCORE!");
        final EditText userName = new EditText(con);
        builder.setView(userName).setMessage("Enter Your Name")
                .setPositiveButton("Done",
                        new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                    	username = userName.getText().toString();
                    	InputMethodManager imm = (InputMethodManager)con.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(userName.getWindowToken(), 0);
                        postHighscore();                
                    }
                }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                    	
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
	
	private void postHighscore() {
		Log.i("USERNAME", "User name is" + username);
		// start upload
		new HighScorePoster().execute();
	}

	public class HighScorePoster extends AsyncTask<URL, Integer, Void> {
		private HttpClient client;
		private HttpPost post;
		int highScoreToPost;
		private HttpResponse response;

		private final ProgressDialog dialog = new ProgressDialog(con);

		protected void onPreExecute() {
			this.dialog.setMessage("Uploading new high score...");
			this.dialog.show();
		}

		@Override
		protected Void doInBackground(URL... params) {
			client = new DefaultHttpClient();
			post = new HttpPost("http://pro-tap.appspot.com/leaderboard");
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("type", entity));
			pairs.add(new BasicNameValuePair("content", username));
			pairs.add(new BasicNameValuePair("score", "" + score));
			try {
				post.setEntity(new UrlEncodedFormEntity(pairs));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			try {
				response = client.execute(post);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		protected void onPostExecute(final Void unused) {
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				Toast error = Toast.makeText(con,
						"Error Whilst Posting: "
								+ response.getStatusLine().getReasonPhrase(),
						Toast.LENGTH_LONG);
				error.show();
			}

		}
	}

	
}
