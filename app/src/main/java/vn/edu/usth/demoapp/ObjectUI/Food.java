package vn.edu.usth.demoapp.ObjectUI;

public class Food {

    private String description;
    private float star;
    private int resourceImage;
    private String name;

    public Food(int resourceImage, String name, float star, String description) {
        this.resourceImage = resourceImage;
        this.name = name;
        this.star = star;
        this.description = description;
    }

    public int getResourceImage() {
        return resourceImage;
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

    public void setResourceImage(int resourceImage) {
        this.resourceImage = resourceImage;
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