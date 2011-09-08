package games.pack.protap.upload;

import android.content.Context;

public final class PostReactionHighScore extends PostHighScore {
    private static final String URL = "http://pro-tap.appspot.com/postreaction";

    public PostReactionHighScore(final Context context, final int score) {
        super(context, score, URL);
    } // PostReactionHighScore(Context, int)
} // PostReactionHighScore
