package wememe.ca.Class;

/**
 * Created by dnksj on 2017-05-14.
 */

public class RechercherProfil {
    int id;
    String username, profilpic;

    public RechercherProfil(int id, String username, String profilpic) {
        this.id = id;
        this.username = username;
        this.profilpic = profilpic;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilpic() {
        return profilpic;
    }
}
