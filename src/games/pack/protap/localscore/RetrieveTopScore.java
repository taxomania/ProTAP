package games.pack.protap.localscore;

import android.content.Context;
import android.os.AsyncTask;

public abstract class RetrieveTopScore extends AsyncTask<Void, Void, Integer[]> {
    private final Context mCtx;

    public RetrieveTopScore(final Context context) {
        mCtx = context;
    } // RetrieveTopScore(Context)

    @Override
    protected final Integer[] doInBackground(final Void... noParams) {
        return doTask(new TopScorePrefs(mCtx));
    } // doInBackground

    @Override
    protected final void onPostExecute(final Integer[] result) {
        if (result != null) {
            setResult(result);
        } // if
    } // onPostExecute

    protected abstract Integer[] doTask(TopScorePrefs prefs);
    protected abstract void setResult(Integer[] result);
} // RetrieveTopScore