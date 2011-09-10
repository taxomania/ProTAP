package games.pack.protap.upload;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import android.text.InputType;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public abstract class PostHighScore {
    private final Context mContext;
    private final String mScore;
    private final String mUrl;

    public PostHighScore(final Context context, final int score, final String url) {
        mScore = Integer.toString(score);
        mContext = context;
        mUrl = url;
    } // PostHighScore(Context, int, String)

    public void enterName() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final EditText userName = new EditText(mContext);
        userName.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(userName).setMessage("Enter Your Name")
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int whichButton) {
                        final InputMethodManager imm = (InputMethodManager) mContext
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(userName.getWindowToken(), 0);
                        postHighScore(userName.getText().toString());
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int whichButton) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    } // enterName()

    private void postHighScore(final String name) {
        new HighScorePoster().execute(mUrl, name, mScore);
    } // postHighScore(String)

    private final class HighScorePoster extends AsyncTask<String, Void, HttpResponse> {
        private final ProgressDialog dialog = new ProgressDialog(mContext);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Uploading new high score...");
            this.dialog.show();
        } // onPreExecute()

        @Override
        protected HttpResponse doInBackground(final String... params) {
            if (params.length < 3) return null;
            final HttpClient client = new DefaultHttpClient();
            final HttpPost post;
            try {
                post = new HttpPost(params[0]);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return null;
            } // try
            final List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("content", params[1]));
            pairs.add(new BasicNameValuePair("score", params[2]));
            try {
                post.setEntity(new UrlEncodedFormEntity(pairs));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } // try
            try {
                return client.execute(post);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } // try
            return null;
        } // doInBackground(String...)

        @Override
        protected void onPostExecute(final HttpResponse response) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            } // if
            if (response == null) {
                Toast.makeText(mContext, "Error posting", Toast.LENGTH_SHORT).show();
                return;
            } // if
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                Toast.makeText(mContext,
                        "Error Whilst Posting: " + response.getStatusLine().getReasonPhrase(),
                        Toast.LENGTH_LONG).show();
            } // if
        } // onPostExecute(HttpResponse)
    } // HighScorePoster

} // PostHighScore
