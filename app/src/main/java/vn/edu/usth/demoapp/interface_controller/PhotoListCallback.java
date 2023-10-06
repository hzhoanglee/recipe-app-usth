package vn.edu.usth.demoapp.interface_controller;

import com.android.volley.VolleyError;

import java.util.List;

import vn.edu.usth.demoapp.object_ui.Food;
import vn.edu.usth.demoapp.object_ui.Photo;

public interface PhotoListCallback {
    void onPhotoListReceived(List<Food> photoList);
    void onError(VolleyError error);
}

