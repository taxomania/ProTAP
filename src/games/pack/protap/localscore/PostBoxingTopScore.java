package games.pack.protap.localscore;

import android.content.Context;

public final class PostBoxingTopScore extends PostTopScore {
    public PostBoxingTopScore(final Context context) {
        super(context);
    } // PostBoxingTopScore(Context)

    @Override
    Boolean postTask(final TopScorePrefs.Editor edit, final Integer score) {
        return edit.putBoxingScore(score).commit();
    } // postTask(TopScorePrefs.Editor, Integer)
} // PostBoxingTopScore
