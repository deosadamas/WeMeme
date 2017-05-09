package wememe.ca.Class;


public class Data_Feed {

    //Variable de la classe Data_Feed
    private int id, like, id_user_post;
    private String sujet, nom, description,image_link;

    //Constructeur de la classe Data_Feed
    public Data_Feed(int id, String sujet, String nom, String description, String image_link, int like, int id_user_post) {
        this.id = id;
        this.sujet = sujet;
        this.nom = nom;
        this.description = description;
        this.image_link = image_link;
        this.like = like;
        this.id_user_post = id_user_post;
    }
    //Methode pour get et set les paramettre de la classe Data_Feed

    public int getId() {
        return id;
    }

    public int getId_user_post() {
        return id_user_post;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public String getDescription() {
        return description;
    }

    public int getLike() {
        return like;
    }

    public String getNom() {
        return nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

}
