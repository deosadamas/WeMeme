package wememe.ca.Class;

public class Utilisateur {
    //Variable de la classe Utilisateur
    private int id;
    private String email, username, date, profilpic;

    //Constructeur de la classe Utilisateur
    public Utilisateur(int id , String email, String username, String date, String profilpic) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.date = date;
        this.profilpic = profilpic;
    }

    //Methode pour get les paramettre de la classe Utilisateur
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public String getProfilpic() {
        return profilpic;
    }
}
