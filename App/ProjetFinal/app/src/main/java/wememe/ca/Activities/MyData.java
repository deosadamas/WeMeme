package wememe.ca.Activities;


public class MyData {

    private int id, like;
    private String sujet, nom, description,image_link;

    public MyData(int id, String sujet, String nom, String description, String image_link, int like) {
        this.id = id;
        this.sujet = sujet;
        this.nom = nom;
        this.description = description;
        this.image_link = image_link;
        this.like = like;
    }

    public int getId() {
        return id;
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
