package wememe.ca.Activities;

public class Utilisateur {
    private int id;
    private String email, username, date, profilpic;

    public Utilisateur(int id , String email, String username, String date, String profilpic) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.date = date;
        this.profilpic = profilpic;
    }

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
