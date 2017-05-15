package wememe.ca.Class;

/**
 * Created by dnksj on 2017-05-14.
 */

public class RechercherProfil {
    int id;
    String username;

    public RechercherProfil(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
