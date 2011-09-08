package games.pack.protap.localscore;

import android.content.Context;

public final class PostReactionTopScore extends PostTopScore {
    public PostReactionTopScore(final Context context) {
        super(context);
    } // PostReactionTopScore

    @Override
    Boolean postTask(final TopScorePrefs.Editor edit, final Integer score) {
        return edit.putReactionScore(score).commit();
    } // postTask(TopScorePrefs.Editor, Integer)
} // PostReactionTopScore
