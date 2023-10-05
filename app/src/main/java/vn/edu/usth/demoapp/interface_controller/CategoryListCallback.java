package vn.edu.usth.demoapp.interface_controller;

import com.android.volley.VolleyError;

import java.util.List;

import vn.edu.usth.demoapp.object_ui.Category;

public interface CategoryListCallback {
    void onSuccess(List<Category> result);
    void onError(VolleyError error);
}
