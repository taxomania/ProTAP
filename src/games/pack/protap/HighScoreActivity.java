package games.pack.protap;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HighScoreActivity extends Activity {
    private TableLayout highScoreTable;
    private static String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score_layout);
        highScoreTable = (TableLayout) findViewById(R.id.highScoreTable);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        chooseBoard(findViewById(R.id.rG));
    }

    private void addHeaders(){
        TableRow tr = new TableRow(this);
        highScoreTable.addView(tr);

        TextView rankText = new TextView(this);
        rankText.setText("Rank");
        rankText.setTextColor(Color.WHITE);
        tr.addView(rankText);

        TextView nameText = new TextView(this);
        nameText.setText("Name");
        nameText.setTextColor(Color.WHITE);
        tr.addView(nameText);

        TextView scoreText = new TextView(this);
        scoreText.setText("Score");
        scoreText.setTextColor(Color.WHITE);
        tr.addView(scoreText);
    }

    public void chooseBoard(View view) {
        switch (view.getId()) {
        case R.id.bG:
            url = "http://pro-tap.appspot.com/leaderboard?type=boxing";
            findViewById(R.id.rG).setEnabled(true);
            break;
        case R.id.rG:
            url = "http://pro-tap.appspot.com/leaderboard?type=reaction";
            findViewById(R.id.bG).setEnabled(true);
            break;
        default:
            url = "http://pro-tap.appspot.com/leaderboard?type=reaction";
            break;
        }
        new HighScoreDownload().execute();
        view.setEnabled(false);

    }

    public class HighScoreDownload extends AsyncTask<URL, Integer, Void> {
        private NodeList names;
        private NodeList scores;
        private NodeList ranks;
        private HttpResponse response;

        private final ProgressDialog dialog = new ProgressDialog(
                HighScoreActivity.this);

        protected void onPreExecute() {
            highScoreTable.removeAllViewsInLayout();
            addHeaders();
            this.dialog.setMessage("Downloading Leaderboard...");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(URL... params) {
            HttpClient client = new DefaultHttpClient();
            HttpGet request;
            try {
                request = new HttpGet(HighScoreActivity.url);
                try {
                    response = client.execute(request);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (IllegalArgumentException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(final Void unused) {
            if (response != null) {
                DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                        .newInstance();
                InputStream in;
                try {
                    DocumentBuilder docBuillder = docBuilderFactory
                            .newDocumentBuilder();
                    in = response.getEntity().getContent();
                    Document doc = docBuillder.parse(in);
                    ranks = doc.getElementsByTagName("rank");
                    names = doc.getElementsByTagName("name");
                    scores = doc.getElementsByTagName("score");
                } catch (ParserConfigurationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SAXException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                for (int i = 0; i < names.getLength(); i++) {

                    TableRow tr = new TableRow(HighScoreActivity.this);
                    highScoreTable.addView(tr);

                    TextView rankText = new TextView(HighScoreActivity.this);
                    rankText.setText(ranks.item(i).getTextContent());
                    rankText.setTextColor(Color.WHITE);
                    tr.addView(rankText);

                    TextView nameText = new TextView(HighScoreActivity.this);
                    nameText.setText(names.item(i).getTextContent());
                    nameText.setTextColor(Color.WHITE);
                    tr.addView(nameText);

                    TextView scoreText = new TextView(HighScoreActivity.this);
                    scoreText.setText(scores.item(i).getTextContent());
                    scoreText.setTextColor(Color.WHITE);
                    tr.addView(scoreText);

                    Log.i("TABLE ROW", "Table row added");
                } // for
                dialog.dismiss();
            } // if response not null
        } // onPostExecute
    } // class HighScoreDownload

} // class HighScoreActivity