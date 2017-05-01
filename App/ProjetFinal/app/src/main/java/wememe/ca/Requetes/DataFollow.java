package wememe.ca.Requetes;

/**
 * Created by info1 on 2017-04-27.
 */

public class DataFollow {

    private int followed, following;
    private String date;

    public DataFollow(int followed, int following, String date) {
        this.followed = followed;
        this.following = following;
        this.date = date;
    }

    public int getFollowed() { return this.followed; }

    public int getFollowing() {
        return this.following;
    }

    public String getDate() {
        return this.date;
    }
}
