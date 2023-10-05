package vn.edu.usth.demoapp.interface_controller;

import com.android.volley.VolleyError;

import java.util.List;

import vn.edu.usth.demoapp.object_ui.Photo;

public interface PhotoListCallback {
    void onPhotoListReceived(List<Photo> photoList);
    void onError(VolleyError error);
}

