package wememe.ca.Requetes;

/**
 * Created by info1 on 2017-04-21.
 */

public class DataLike {
    private int user, meme;

    public DataLike(int user, int meme) {
        this.user = user;
        this.meme = meme;
    }

    public int getUser() {
        return user;
    }

    public int getMeme() {
        return meme;
    }
}
