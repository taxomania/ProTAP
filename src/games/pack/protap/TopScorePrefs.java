package games.pack.protap;

import android.content.Context;
import android.content.SharedPreferences;

public class TopScorePrefs {
    private static final String PREFERENCES = "ProTAP_Prefs";
    private static final String KEY_BOXING = "boxing_score";
    private static final String KEY_REACTION = "reaction_score";
    private static final int DEFAULT = 0;

    private SharedPreferences mPrefs;
    private Context mCtx;

    public TopScorePrefs(final Context c) {
        mCtx = c;
        mPrefs = mCtx.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    } // TopScorePrefs

    public int getBoxingScore() {
        return getInt(KEY_BOXING);
    } // getBoxingScore

    public int getReactionScore() {
        return getInt(KEY_REACTION);
    } // getReactionScore

    private int getInt(final String key) {
        return mPrefs.getInt(key, DEFAULT);
    } // getInt

    public Editor edit() {
        return new Editor();
    } // edit

    public class Editor {
        private SharedPreferences.Editor mEdit;

        public Editor() {
            mEdit = mPrefs.edit();
        } // Editor

        public Editor putBoxingScore(final int score) {
            return putInt(KEY_BOXING, score);
        } // putBoxingScore

        public Editor putReactionScore(final int score) {
            return putInt(KEY_REACTION, score);
        } // putReactionScore

        private Editor putInt(final String key, final int value) {
            mEdit.putInt(key, value);
            return this;
        } // putInt

        public boolean commit() {
            return mEdit.commit();
        } // commit

        public boolean reset() {
            return putBoxingScore(DEFAULT).putReactionScore(DEFAULT).commit();
        } // reset

    } // TopScorePrefs.Editor

} // TopScorePrefs
