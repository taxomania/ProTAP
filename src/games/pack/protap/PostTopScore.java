package games.pack.protap;

import android.content.Context;
import android.os.AsyncTask;

public class PostTopScore extends AsyncTask<Integer, Void, Boolean> {
    static final int BOXING = 0;
    static final int REACTION = 1;
    static final int RESET = 2;

    private Context mCtx;
    private int mPref;
    public PostTopScore(final Context c, final int pref) {
        mCtx = c;
        mPref = pref;
    }

    @Override
    protected final Boolean doInBackground(final Integer... params) {
        final TopScorePrefs.Editor edit = new TopScorePrefs(mCtx).edit();
        switch (mPref) {
            case BOXING:
                return edit.putBoxingScore((Integer) params[0]).commit();
            case REACTION:
                return edit.putReactionScore((Integer) params[0]).commit();
            case RESET:
                return edit.reset();
            default:
                return null;
        } // switch
    } // doInBackground
}