package vn.edu.usth.demoapp.object_ui;

public class Food {

    private String description;
    private float star;
    private String urlImage;
    private String name;

    public Food(String urlImage, String name, float star, String description) {
        this.urlImage = urlImage;
        this.name = name;
        this.star = star;
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getName() {
        return name;
    }

    public float getStar() {
        return star;
    }

    public String getDescription() {
        return description;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}