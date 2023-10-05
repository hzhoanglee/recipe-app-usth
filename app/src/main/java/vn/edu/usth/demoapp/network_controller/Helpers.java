package vn.edu.usth.demoapp.network_controller;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import vn.edu.usth.demoapp.R;
import vn.edu.usth.demoapp.activity_ui.MainActivity;
import vn.edu.usth.demoapp.activity_ui.SearchResultActivity;

public class Helpers {
    public static Dialog showLoadingDialog(Context context) {
        int gravity = Gravity.CENTER;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_loading);

        Window window = dialog.getWindow();
        if(window == null) {
            return null;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(false);

        dialog.show();
        return dialog;

    }

    public static Dialog showPopup(Context context, String text) {
        int gravity = Gravity.CENTER;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_popup);

        Window window = dialog.getWindow();
        if(window == null) {
            return null;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(true);

        TextView notiText = dialog.findViewById(R.id.notiText);
        if(notiText != null) {
            notiText.setText(text);
        }

        return dialog;

    }

    public static String trimContent(String content, int length) {
        if(content.length() > length) {
            return content.substring(0, length) + "...";
        } else {
            return content;
        }
    }

    public static void storeSharedPreferenceLogin(Context context, String name, String token, String email, String created_at) {
        context.getSharedPreferences("myKey", Context.MODE_PRIVATE).edit()
                .putString("user_name", name)
                .putString("token", token)
                .putString("user_email", email)
                .putString("user_created_at", created_at)
                .putBoolean("isLoggedIn", true)
                .apply();
    }

    public static void storeSharedPreferenceLogout(Context context) {
        context.getSharedPreferences("myKey", Context.MODE_PRIVATE).edit()
                .remove("token")
                .remove("user_name")
                .remove("user_email")
                .remove("user_created_at")
                .putBoolean("isLoggedIn", false)
                .apply();
    }

    public static String convertToUriQuery(String query) {
        return query.replace(" ", "%20");
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myKey", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    public static void storePref(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myKey", MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String getPref(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("myKey", MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static boolean setTmpValue(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("tmpValue", MODE_PRIVATE);
        return sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getTmpValue(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("tmpValue", MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void cleanTmpValue(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("tmpValue", MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public static void loadImageUsingVolley(Context context, String url, ImageView imageView) {
        RequestQueue queue = Volley.newRequestQueue(context);
        ImageRequest imageRequest = new ImageRequest(url, imageView::setImageBitmap, 0, 0, ImageView.ScaleType.CENTER_CROP, null, error -> {
            Toast.makeText(context, "Error while loading image", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        });
        queue.add(imageRequest);
    }
}
