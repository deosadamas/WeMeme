package wememe.ca.Requetes;

/**
 * Created by info1 on 2017-04-21.
 */

public class DataLike {
    private int user, meme;
    private String image;

    public DataLike(int user, int meme, String image) {
        this.user = user;
        this.meme = meme;
        this.image = image;
    }

    public int getUser() {
        return user;
    }

    public int getMeme() {
        return meme;
    }

    public String getImage() {
        return image;
    }
}
