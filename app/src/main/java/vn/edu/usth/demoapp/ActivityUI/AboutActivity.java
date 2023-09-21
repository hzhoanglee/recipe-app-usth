package vn.edu.usth.demoapp.ActivityUI;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import vn.edu.usth.demoapp.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findViewById(R.id.AboutBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.visit_website_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to website hzme.net
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://hzme.net"));
                startActivity(browserIntent);
            }
        });
    }
}