package vn.edu.usth.demoapp.activity_ui;

import static vn.edu.usth.demoapp.network_controller.Helpers.cleanTmpValue;
import static vn.edu.usth.demoapp.network_controller.Helpers.storeSharedPreferenceLogout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.WindowManager.LayoutParams;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import vn.edu.usth.demoapp.R;
import vn.edu.usth.demoapp.adapter_ui.ViewPagerAdapter2;
import vn.edu.usth.demoapp.interface_controller.StatusCallback;
import vn.edu.usth.demoapp.network_controller.RecipeController;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView mBottomNavigationView;
    private TextView appTitle;

    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewPager2 mViewPager2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cleanTmpValue(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = Volley.newRequestQueue(this);

        if (checkFirstRun()) {
            Intent onboardingIntent = new Intent(this, OnboardingActivity.class);
            startActivity(onboardingIntent);
            askNotificationPermission();
        } else {
            appTitle = findViewById(R.id.textViewTitle);
            appTitle.setText(getString(R.string.title_nav_home));
            mViewPager2 = findViewById(R.id.viewpager);
            mBottomNavigationView = findViewById(R.id.bottom_navigation);

            ViewPagerAdapter2 myViewAdapter = new ViewPagerAdapter2(this);
            mViewPager2.setAdapter(myViewAdapter);
            mViewPager2.setUserInputEnabled(false);
            mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    appTitle.setText(myViewAdapter.getTitle(position));
                }
            });

            mBottomNavigationView.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    mViewPager2.setCurrentItem(0);
                    appTitle.setText(getString(R.string.title_nav_home));
                } else if (id == R.id.navigation_recent) {
                    mViewPager2.setCurrentItem(1);
                    appTitle.setText(getString(R.string.title_nav_recent));
                } else if (id == R.id.navigation_category) {
                    mViewPager2.setCurrentItem(2);
                    appTitle.setText(getString(R.string.title_nav_category));
                } else if (id == R.id.navigation_favorite) {
                    mViewPager2.setCurrentItem(3);
                    appTitle.setText(getString(R.string.title_nav_favorite));
                }
                return true;
            });

            mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    mBottomNavigationView.getMenu().getItem(position).setChecked(true);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top, menu);

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            menu.findItem(R.id.action_logout).setVisible(false);
        } else {
            menu.findItem(R.id.action_hello).setTitle("Hello, " + sharedPreferences.getString("user_name", "User") + "!");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            openSearchDialog(Gravity.CENTER, "search");
            return true;
        }
        if (item.getItemId() == R.id.action_request) {
            openSearchDialog(Gravity.CENTER, "request_recipe");
            return true;
        }
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.action_logout) {
            storeSharedPreferenceLogout(this);
            Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.action_exit) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
    }

    private void openSearchDialog(int gravity, String type) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(type.equals("search"))
            dialog.setContentView(R.layout.layout_dialog_search);
        else if(type.equals("request_recipe")) {
            dialog.setContentView(R.layout.layout_dialog_request);
        } else {
            Toast.makeText(MainActivity.this, "Error: Invalid dialog type", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
        Window window = dialog.getWindow();
        if(window == null) {
            return;
        }

        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(Gravity.CENTER == gravity);

        EditText editSearch = dialog.findViewById(R.id.editSearch);
        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
        Button buttonSearch = dialog.findViewById(R.id.buttonSearch);

        buttonCancel.setOnClickListener(v -> dialog.dismiss());

        if(type.equals("search")) {
            buttonSearch.setOnClickListener(v -> {
                Toast.makeText(MainActivity.this, "Search: " + editSearch.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                Bundle b = new Bundle();
                b.putString("search_param", editSearch.getText().toString());
                intent.putExtras(b);
                startActivity(intent);
            });
        } else if (type.equals("request_recipe")) {
            buttonSearch.setOnClickListener(v -> {
                RecipeController recipeController = new RecipeController();
                recipeController.requestFood(MainActivity.this, editSearch.getText().toString(), new StatusCallback() {
                    @Override
                    public void onStatusOK(boolean status) {
                        Toast.makeText(MainActivity.this, "Request sent successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Log.e("Request Recipe", error.networkResponse.toString());
                        Toast.makeText(MainActivity.this, "Error while sending request", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Thank you for requesting: " + editSearch.getText().toString(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(MainActivity.this, "Error: Invalid dialog type", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

        dialog.show();

    }


    private boolean checkFirstRun() {
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isFirstRun", true);
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You decided to opt out of Notification", Toast.LENGTH_SHORT).show();
                }
            });

    private void askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                Toast.makeText(this, "Please allow notification permission", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please allow notification permission in Settings", Toast.LENGTH_SHORT).show();
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

}