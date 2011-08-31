package games.pack.protap;

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

public class PostHighScore {
    private static final String REACTION_URL = "http://pro-tap.appspot.com/postreaction";
    private static final String BOXING_URL = "http://pro-tap.appspot.com/postboxing";
    public static final int REACTION = 0;
    public static final int BOXING = 1;
    private String mScore;
    private Context mContext;
    private int mType;

    public PostHighScore(final Context c, final int s, final int t) {
        mScore = Integer.toString(s);
        mContext = c;
        mType = t;
    }

    public void enterName() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("NEW HIGHSCORE!");
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
    }

    private void postHighScore(final String name) {
        final String url;
        switch (mType) {
            case REACTION:
                url = REACTION_URL;
                break;
            case BOXING:
                url = BOXING_URL;
                break;
            default:
                return;
        }
        new HighScorePoster().execute(url, name, mScore);
    }

    public class HighScorePoster extends AsyncTask<String, Void, HttpResponse> {
        private final ProgressDialog dialog = new ProgressDialog(mContext);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Uploading new high score...");
            this.dialog.show();
        }

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
            }
            final List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("content", params[1]));
            pairs.add(new BasicNameValuePair("score", params[2]));
            try {
                post.setEntity(new UrlEncodedFormEntity(pairs));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                return client.execute(post);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final HttpResponse response) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (response == null) {
                Toast.makeText(mContext, "Error posting", Toast.LENGTH_SHORT).show();
                return;
            }
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                Toast.makeText(mContext,
                        "Error Whilst Posting: " + response.getStatusLine().getReasonPhrase(),
                        Toast.LENGTH_LONG).show();
            }

        }
    }

}
