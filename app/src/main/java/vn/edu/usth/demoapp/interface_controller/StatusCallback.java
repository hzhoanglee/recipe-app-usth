package vn.edu.usth.demoapp.interface_controller;

import com.android.volley.VolleyError;

public interface StatusCallback {
    void onStatusOK(boolean status);

    void onError(VolleyError error);
}
