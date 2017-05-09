package wememe.ca.Class;

/**
 * Created by info1 on 2017-04-21.
 */

public class DataLike {
    //Variable de la classe DataLike
    private int user, meme;

    //Constructeur de la classe DataLike
    public DataLike(int user, int meme) {
        this.user = user;
        this.meme = meme;
    }

    //Methode pour get les paramettre de la classe DataLike
    public int getUser() {
        return user;
    }

    public int getMeme() {
        return meme;
    }
}
