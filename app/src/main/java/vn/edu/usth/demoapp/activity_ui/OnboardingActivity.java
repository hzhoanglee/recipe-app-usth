package vn.edu.usth.demoapp.activity_ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

import vn.edu.usth.demoapp.R;


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

        setTransformer(AppIntroPageTransformerType.Fade.INSTANCE);
        showStatusBar(true);
        setColorTransitionsEnabled(true);
        setSystemBackButtonLocked(true);
        setWizardMode(false);
        setIndicatorEnabled(false);
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