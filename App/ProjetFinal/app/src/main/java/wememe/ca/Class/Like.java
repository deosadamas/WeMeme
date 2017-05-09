package wememe.ca.Class;

/**
 * Created by dnksj on 2017-04-27.
 */

public class Like {
    //Variable de la classe Like
    private int meme, like;

    //Constructeur de la classe Like
    public Like(int meme, int like) {
        this.meme = meme;
        this.like = like;
    }

    //Methode pour get les paramettre de la classe like
    public int getMeme() {
        return meme;
    }

    public int getLike() {
        return like;
    }
}