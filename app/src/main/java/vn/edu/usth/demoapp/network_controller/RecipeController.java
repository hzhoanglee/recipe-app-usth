package vn.edu.usth.demoapp.network_controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.List;

import vn.edu.usth.demoapp.interface_controller.FoodListCallback;
import vn.edu.usth.demoapp.object_ui.Food;

public class RecipeController {
    public static String getRecipeEndpoint() {
        return GlobalVariables.API_ENDPOINT + "recipe/explore";
    }

    public void getExploreList(Context context, FoodListCallback callback) {
        String url = getRecipeEndpoint();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(url, response -> {
            List<Food> list;
            try {
                list = Food.fromJson(response);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
//            Log.i("RecipeController", list.toString());
            callback.onSuccess(list);
        }, error -> {
//            Log.i("RecipeController", error.toString());
            callback.onError(error);
        });
        queue.add(stringRequest);

    }



}
