package vn.edu.usth.demoapp.interface_controller;

import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.List;

import vn.edu.usth.demoapp.object_ui.Food;

public interface FoodCallback {
    void onFoodListReceived(List<Food> foodList);
    void onError(VolleyError error);
}

