package vn.edu.usth.demoapp.network_controller;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.List;

import vn.edu.usth.demoapp.interface_controller.FoodListCallback;
import vn.edu.usth.demoapp.interface_controller.PhotoListCallback;
import vn.edu.usth.demoapp.object_ui.Food;
import vn.edu.usth.demoapp.object_ui.Photo;

public class FeaturedController {
    public static String getFeaturedUrl() {
        return GlobalVariables.API_ENDPOINT + "recipe/featured";
    }

    public static void getFeaturedRecipe(Context context, PhotoListCallback callback) {
        String url = getFeaturedUrl();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(url, response -> {
            List<Food> list;
            try {
                list = Food.fromJson(response);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            callback.onPhotoListReceived(list);
        }, callback::onError);
        queue.add(stringRequest);
    }
}
