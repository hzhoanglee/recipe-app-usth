package vn.edu.usth.demoapp.object_ui;

import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Photo implements Serializable {
    private Integer id;

    private String description;
    private float star;
    private String urlImage;
    private String name;
    private String category;
    private String[] ingredients;
    private String[] steps;
    private String prepTime;
    private String cookTime;
    private String serving;
    private String level;
    private String html_content;


    public Photo(Integer id, String urlImage, String name, float star, String description, String category, String[] ingredients, String[] steps, String prepTime, String cookTime, String serving, String level, String html_content) {
        this.id = id;
        this.urlImage = urlImage;
        this.name = name;
        this.star = star;
        this.description = description;
        this.category = category;
        this.ingredients = ingredients;
        this.steps = steps;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.serving = serving;
        this.level = level;
        this.html_content = html_content;
    }

    public static List<Photo> fromJson(String json) throws JSONException {
        List<Photo> foods = new ArrayList<>();

        JSONObject obj = new JSONObject(json);
        JSONArray data = obj.getJSONArray("data");

        for(int i = 0; i < data.length(); i++) {
            JSONObject foodObj = data.getJSONObject(i);

            int id = foodObj.getInt("id");
            String name = foodObj.getString("name");
            String description = foodObj.getString("description");
            String image = foodObj.getString("image");
            float star = (float) foodObj.getDouble("review");
            JSONObject categoryObj = foodObj.getJSONObject("category");
            String category = categoryObj.getString("name");
            JSONArray ingredientsArr = foodObj.getJSONArray("ingredients");
            String[] ingredients = new String[ingredientsArr.length()];
            for(int j = 0; j < ingredientsArr.length(); j++) {
                ingredients[j] = ingredientsArr.getString(j);
            }
            JSONArray stepsArr = foodObj.getJSONArray("steps");
            String[] steps = new String[stepsArr.length()];
            for(int j = 0; j < stepsArr.length(); j++) {
                steps[j] = stepsArr.getString(j);
            }
            String prepTime = foodObj.getString("prep_time");
            String cookTime = foodObj.getString("cook_time");
            String serving = foodObj.getString("serving");
            String level = foodObj.getString("level");
            String html_content = foodObj.getString("html");

            Photo food = new Photo(id, image, name, star, description, category, ingredients, steps, prepTime, cookTime, serving, level, html_content);
            foods.add(food);
        }

        return foods;
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

    public String getCategory() {
        return category;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public String[] getSteps() {
        return steps;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public String getCookTime() {
        return cookTime;
    }

    public String getServing() {
        return serving;
    }

    public String getLevel() {
        return level;
    }

    public String getHtmlContent() {
        return html_content;
    }

    public Integer getId() {
        return id;
    }
}