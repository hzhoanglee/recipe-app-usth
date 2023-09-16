package vn.edu.usth.myrecipesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 mViewPager2;
    private BottomNavigationView mBottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager2 = findViewById(R.id.viewpager);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        ViewPagerAdapter2 MyViewAdapter = new ViewPagerAdapter2(this);
        mViewPager2.setAdapter(MyViewAdapter);

        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    mViewPager2.setCurrentItem(0);
                } else if (id == R.id.navigation_recent) {
                    mViewPager2.setCurrentItem(1);
                } else if (id == R.id.navigation_category) {
                    mViewPager2.setCurrentItem(2);
                } else if (id == R.id.navigation_favorite) {
                    mViewPager2.setCurrentItem(3);
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