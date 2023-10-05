package vn.edu.usth.demoapp.network_controller;

import static android.content.Context.MODE_PRIVATE;

import static com.google.android.material.color.utilities.MaterialDynamicColors.error;

import static vn.edu.usth.demoapp.network_controller.Helpers.storeSharedPreferenceLogin;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import vn.edu.usth.demoapp.R;
import vn.edu.usth.demoapp.interface_controller.FoodListCallback;
import vn.edu.usth.demoapp.interface_controller.StatusCallback;

public class UserController {
    public void userLogin(String username, String password, Context context, StatusCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = context.getString(R.string.api_endpoint) + "auth/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        // Parse JSON
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject user = jsonObject.getJSONObject("user");
                        // Get values
                        String token = jsonObject.getString("token");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
                        String dt = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(java.time.LocalDateTime.parse(created_at, formatter));
                        // Store Value
                        storeSharedPreferenceLogin(context, name, token, email, dt);
                        Toast.makeText(context, "Logged in as " + name, Toast.LENGTH_SHORT).show();
                        callback.onStatusOK(true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    callback.onError(error);
                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", username);
                params.put("password", password);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void userRegister(String username, String password, String email, Context context, StatusCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = context.getString(R.string.api_endpoint) + "auth/register";

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("email", email);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
            response -> {
                // Parse JSON
                JSONObject jsonObject = new JSONObject(params);

                Toast.makeText(context, "Logged in as " + username, Toast.LENGTH_SHORT).show();
                callback.onStatusOK(true);
            }, error -> {
            try {
                JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            callback.onError(error);
            }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", username);
                params.put("password", password);
                params.put("email", email);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void registerFirebaseToken(String token, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = context.getString(R.string.api_endpoint) + "auth/firebase?token=" + token;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.i("Firebase token", "Registered");
                }, error -> {
                    Log.i("Firebase token", "Failed");
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + getBearerToken(context));
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    public void regFirebaseGeneral(String token, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = context.getString(R.string.api_endpoint) + "regFirebase?token=" + token;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.i("Firebase token", "Registered");
                }, error -> {
                    Log.i("Firebase token", "Failed");
                });
        queue.add(stringRequest);
    }

    private String getBearerToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myKey", MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }
}
