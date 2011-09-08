package games.pack.protap;

import android.content.Context;

public class PostReactionHighScore extends PostHighScore {
    private static final String URL = "http://pro-tap.appspot.com/postreaction";

    public PostReactionHighScore(final Context context, final int score) {
        super(context, score, URL);
    } // PostReactionHighScore(Context, int)
} // PostReactionHighScore
