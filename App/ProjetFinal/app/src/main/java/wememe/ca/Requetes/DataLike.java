package wememe.ca.Requetes;

/**
 * Created by info1 on 2017-04-21.
 */

public class DataLike {
    private int meme;
    private String user;

    public DataLike(String user, int meme) {
        this.user = user;
        this.meme = meme;
    }

    public String getUser() {
        return user;
    }

    public int getMeme() {
        return meme;
    }
}
