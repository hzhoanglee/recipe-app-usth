package vn.edu.usth.demoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 mViewPager2;
    private BottomNavigationView mBottomNavigationView;
    private TextView appTitle;
    private Switch modeSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            Intent onboardingIntent = new Intent(this, OnboardingActivity.class);
            startActivity(onboardingIntent);
        } else {
            appTitle = findViewById(R.id.textViewTitle);
            appTitle.setText("Home");
            mViewPager2 = findViewById(R.id.viewpager);
            mBottomNavigationView = findViewById(R.id.bottom_navigation);

            ViewPagerAdapter2 MyViewAdapter = new ViewPagerAdapter2(this);
            mViewPager2.setAdapter(MyViewAdapter);
            //change app title when swipe
            mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    appTitle.setText(MyViewAdapter.getTitle(position));
                }
            });

            mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.navigation_home) {
                        mViewPager2.setCurrentItem(0);
                        appTitle.setText("Home");
                    } else if (id == R.id.navigation_recent) {
                        mViewPager2.setCurrentItem(1);
                        appTitle.setText("Explore");
                    } else if (id == R.id.navigation_category) {
                        mViewPager2.setCurrentItem(2);
                        appTitle.setText("Category");
                    } else if (id == R.id.navigation_favorite) {
                        mViewPager2.setCurrentItem(3);
                        appTitle.setText("Favorite");
                    }
                    return true;
                }
            });

            mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    mBottomNavigationView.getMenu().getItem(position).setChecked(true);
                }
            });


            // Handle search button @+id/imageButtonSearch
//            findViewById(R.id.action_search).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    openSearchDialog(Gravity.CENTER);
//                }
//            });
//
//            findViewById(R.id.imageButtonSettings).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // set isLoggedIn to false
//                    SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("isLoggedIn", false);
//                    editor.apply();
//                    Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
//                    Intent intent = getIntent();
//                    finish();
//                    startActivity(intent);
//                }
//            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top, menu);

        // if user is not logged in, hide logout button
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            menu.findItem(R.id.action_logout).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            openSearchDialog(Gravity.CENTER);
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
            SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();
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

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void openSearchDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_search);

        Window window = dialog.getWindow();
        if(window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(Gravity.BOTTOM == gravity);

        EditText editSearch = dialog.findViewById(R.id.editSearch);
        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
        Button buttonSearch = dialog.findViewById(R.id.buttonSearch);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get search param from editSearch
                Toast.makeText(MainActivity.this, "Search: " + editSearch.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                Bundle b = new Bundle();
                b.putString("search_param", editSearch.getText().toString());
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        dialog.show();

    }

}