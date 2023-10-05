package vn.edu.usth.demoapp.object_ui;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private Integer id;

    private String urlImage;


    public Category ( int id, String image, String name){
        this.id = id;
        this.urlImage = image;
        this.name = name;
    }


    public static List<Category> fromJson(String json) throws JSONException {
        List<Category> categories = new ArrayList<>();

        JSONObject obj = new JSONObject(json);
        JSONArray data = obj.getJSONArray("data");

        for(int i = 0; i < data.length(); i++) {
            JSONObject categoryObj = data.getJSONObject(i);

            int id = categoryObj.getInt("id");
            String image = categoryObj.getString("image");
            Log.i("Category", image);
            String name = categoryObj.getString("name");
            Category category = new Category(id, image, name);
            categories.add(category);
        }

        return categories;
    }

    // Setters
    public void setName(String name){
        this.name = name;
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getUrlImage() {
        return urlImage;
    }
}
    

