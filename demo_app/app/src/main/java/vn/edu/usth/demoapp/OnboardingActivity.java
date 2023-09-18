package vn.edu.usth.demoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;


public class OnboardingActivity extends AppIntro {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Color Color;
        addSlide(AppIntroFragment.createInstance(getResources().getString(R.string.onboarding_title_1),
                getResources().getString(R.string.onboarding_subtitle_1),
                R.drawable.person_cooking1,
                R.color.onboarding_1
        ));

        addSlide(AppIntroFragment.createInstance(getResources().getString(R.string.onboarding_title_2),
                getResources().getString(R.string.onboarding_subtitle_2),
                R.drawable.person_cooking2,
                R.color.onboarding_2
        ));

        addSlide(AppIntroFragment.createInstance(getResources().getString(R.string.onboarding_title_3),
                getResources().getString(R.string.onboarding_subtitle_3),
                R.drawable.person_cooking3,
                R.color.onboarding_3
        ));


        // Fade Transition
        setTransformer(AppIntroPageTransformerType.Fade.INSTANCE);

        // Show/hide status bar
        showStatusBar(true);
        //Enable the color "fade" animation between two slides (make sure the slide implements SlideBackgroundColorHolder)
        setColorTransitionsEnabled(true);

        //Prevent the back button from exiting the slides
        setSystemBackButtonLocked(true);

        //Activate wizard mode (Some aesthetic changes)
        setWizardMode(false);

        //Enable/disable page indicators
        setIndicatorEnabled(false);

        // next button
        setSkipText("Skip");

        setSkipButtonEnabled(true);


    }

    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFirstRun", false);
        editor.apply();

        finish();
        Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFirstRun", false);
        editor.apply();

        finish();
        Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
        startActivity(intent);
    }


}