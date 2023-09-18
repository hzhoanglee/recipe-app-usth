package vn.edu.usth.demoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 mViewPager2;
    private BottomNavigationView mBottomNavigationView;
    private TextView appTitle;
    private Switch modeSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        }


    }
}