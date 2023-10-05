package vn.edu.usth.demoapp.interface_controller;

import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.List;

import vn.edu.usth.demoapp.object_ui.Category;

public interface CategoryCallback {
    void onCategoryListReceived(List<Category> categoryList);
    void onError(VolleyError error);
}

