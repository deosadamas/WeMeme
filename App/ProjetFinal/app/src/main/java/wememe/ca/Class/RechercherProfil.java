package wememe.ca.Class;

/**
 * Created by dnksj on 2017-05-14.
 */

public class RechercherProfil {
    //Variable de la classe RechercherProfil
    int id;
    String username;

    //Constructeur de la classe RechercherProfil
    public RechercherProfil(int id, String username) {
        this.id = id;
        this.username = username;
    }
    //Methode pour get les paramettre de la classe RechercherProfil
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
