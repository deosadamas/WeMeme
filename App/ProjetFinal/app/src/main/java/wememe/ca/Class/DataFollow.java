package wememe.ca.Class;

/**
 * Created by info1 on 2017-04-27.
 */

public class DataFollow {

    //Variable de la classe DataFollow
    private int followed, following;
    private String date;

   //Constructeur de la classe DataFollow
    public DataFollow(int followed, int following, String date) {
        this.followed = followed;
        this.following = following;
        this.date = date;
    }

    //Methode pour get les paramettre de la classe DataFollow
    public int getFollowed() { return this.followed; }

    public int getFollowing() {
        return this.following;
    }

    public String getDate() {
        return this.date;
    }
}
