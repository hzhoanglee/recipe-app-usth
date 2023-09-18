package vn.edu.usth.demoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        FragmentManager fragmentManager = getSupportFragmentManager();

        final PaperOnboardingFragment paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataforOnboarding());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        paperOnboardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isFirstRun", false);
                editor.apply();

                finish();
                Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        fragmentTransaction.add(R.id.activity_onboarding, paperOnboardingFragment);

        fragmentTransaction.commit();
    }

    private ArrayList<PaperOnboardingPage> getDataforOnboarding() {

        PaperOnboardingPage source = new PaperOnboardingPage(getResources().getString(R.string.onboarding_title_1), getResources().getString(R.string.onboarding_subtitle_1), Color.parseColor("#ffb174"),R.drawable.person_cooking1, R.drawable.ic_setting_recipes);
        PaperOnboardingPage source1 = new PaperOnboardingPage(getResources().getString(R.string.onboarding_title_2), getResources().getString(R.string.onboarding_subtitle_2), Color.parseColor("#22eaaa"),R.drawable.person_cooking2, R.drawable.ic_setting_recipes);
        PaperOnboardingPage source2 = new PaperOnboardingPage(getResources().getString(R.string.onboarding_title_3), getResources().getString(R.string.onboarding_subtitle_3), Color.parseColor("#ed9898"),R.drawable.person_cooking3, R.drawable.ic_setting_recipes);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();

        elements.add(source);
        elements.add(source1);
        elements.add(source2);
        return elements;
    }
}