package games.pack.protap;

import android.content.Context;
import android.os.AsyncTask;

public abstract class RetrieveTopScore extends AsyncTask<Void, Void, Integer[]> {
    static final int BOXING = 0;
    static final int REACTION = 1;
    static final int BOTH = 2;

    private Context mCtx;
    private int mPref;
    public RetrieveTopScore(final Context c, final int pref) {
        mCtx = c;
        mPref = pref;
    }

    @Override
    protected final Integer[] doInBackground(final Void... noParams) {
        final TopScorePrefs prefs = new TopScorePrefs(mCtx);
        switch (mPref) {
            case BOXING:
                return new Integer[]{prefs.getBoxingScore()};
            case REACTION:
                return new Integer[]{prefs.getReactionScore()};
            case BOTH:
                return new Integer[]{prefs.getBoxingScore(), prefs.getReactionScore()};
            default:
                return null;
        } // switch
    } // doInBackground

    @Override
    protected abstract void onPostExecute(final Integer[] result);
}