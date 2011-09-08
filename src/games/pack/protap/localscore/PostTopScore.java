package games.pack.protap.localscore;

import android.content.Context;
import android.os.AsyncTask;

public abstract class PostTopScore extends AsyncTask<Integer, Void, Boolean> {
    private final Context mCtx;

    public PostTopScore(final Context context) {
        mCtx = context;
    } // PostTopScore(Context)

    @Override
    protected final Boolean doInBackground(final Integer... params) {
        return postTask(new TopScorePrefs(mCtx).edit(), params[0]);
    } // doInBackground(Integer...)

    abstract Boolean postTask(TopScorePrefs.Editor edit, Integer score);
} // PostTopScore