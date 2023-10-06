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

import vn.edu.usth.demoapp.interface_controller.FoodListCallback;
import vn.edu.usth.demoapp.interface_controller.StatusCallback;
import vn.edu.usth.demoapp.object_ui.Food;

public class RecipeController {
    public static String getRecipeEndpoint() {
        return GlobalVariables.API_ENDPOINT + "recipe/explore";
    }

    public void getExploreList(Context context, int page, FoodListCallback callback) {
        String url = getRecipeEndpoint() + "?page=" + page;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(url, response -> {
            List<Food> list;
            try {
                list = Food.fromJson(response);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            callback.onSuccess(list);
        }, callback::onError) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + getBearerToken(context));
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    public void getSearchRecipe(Context context, String searchQuery, FoodListCallback callback) {
        String url = GlobalVariables.API_ENDPOINT + "recipe/search?query_string=" + convertToUriQuery(searchQuery);
        Log.i("Search result", url);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(url, response -> {
            List<Food> list;
            try {
                Log.i("Search result", response);
                list = Food.fromJson(response);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            callback.onSuccess(list);
        }, callback::onError) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + getBearerToken(context));
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    public void requestFood(Context context, String foodName, StatusCallback callback) {
        String url = GlobalVariables.API_ENDPOINT + "recipe/request";
        RequestQueue queue = Volley.newRequestQueue(context);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", foodName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                response -> {
                    callback.onStatusOK(true);
                },
                error -> {
                    callback.onError(error);
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + getBearerToken(context));
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                if (jsonBody != null) {
                    return jsonBody.toString().getBytes();
                }
                return super.getBody();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        queue.add(stringRequest);
    }

    private String getBearerToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myKey", MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    private boolean checkLogin(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myKey", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLogin", false);
    }

}
