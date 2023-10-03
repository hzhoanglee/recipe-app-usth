package vn.edu.usth.demoapp.interface_controller;

import com.android.volley.VolleyError;

import java.util.List;

import vn.edu.usth.demoapp.object_ui.Food;

public interface FoodListCallback {
    void onSuccess(List<Food> result);
    void onError(VolleyError error);
}
