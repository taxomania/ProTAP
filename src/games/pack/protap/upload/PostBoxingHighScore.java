package games.pack.protap.upload;

import android.content.Context;

public class PostBoxingHighScore extends PostHighScore {
    private static final String URL = "http://pro-tap.appspot.com/postboxing";

    public PostBoxingHighScore(final Context context, final int score) {
        super(context, score, URL);
    } // PostBoxingHighScore(Context, int)

} // PostBoxingHighScore
