package vn.edu.usth.demoapp.network_controller;

import static android.content.Context.MODE_PRIVATE;

import static vn.edu.usth.demoapp.network_controller.Helpers.convertToUriQuery;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.edu.usth.demoapp.interface_controller.CategoryListCallback;
import vn.edu.usth.demoapp.interface_controller.FoodListCallback;
import vn.edu.usth.demoapp.interface_controller.StatusCallback;
import vn.edu.usth.demoapp.object_ui.Category;
import vn.edu.usth.demoapp.object_ui.Food;

public class CategoryController {
    public static String getCategoryEndpoint() {
        return GlobalVariables.API_ENDPOINT + "categories";
    }

    public void getCatgoryList(Context context, CategoryListCallback callback) {
        String url = getCategoryEndpoint();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(url, response -> {
            List<Category> list;
            try {
                list = Category.fromJson(response);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            callback.onSuccess(list);
        }, callback::onError);
        queue.add(stringRequest);

    }

    public void getCategoryRecipe(Context context, Integer categoryId, FoodListCallback callback) {
        String url = GlobalVariables.API_ENDPOINT + "categories-data?category=" + categoryId;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(url, response -> {
            List<Food> list;
            try {
                list = Food.fromJson(response);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            callback.onSuccess(list);
        }, callback::onError);
        queue.add(stringRequest);
    }

    private String getBearerToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myKey", MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

}
