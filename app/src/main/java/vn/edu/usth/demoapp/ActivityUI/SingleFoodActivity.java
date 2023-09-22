package vn.edu.usth.demoapp.ActivityUI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Objects;

import vn.edu.usth.demoapp.R;

public class SingleFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_food);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            if (Objects.equals(b.getString("type"), "recipe_item")) {
                String itemValue = b.getString("food_name");
                String titleValue = "Recipe: " + itemValue;
                Integer imgValue = b.getInt("food_img");
                setTitle(titleValue);
                setFoodName(itemValue);
                setFoodImage(imgValue);
                setDescription(b.getString("food_description"));
                setRating(b.getFloat("food_rate"));
            }
        }

        handleBack();

    }

    private void setTitle(String title) {
        TextView textView = findViewById(R.id.textViewTitle);
        textView.setText(title);
    }

    private void setFoodName(String name) {
        TextView textView = findViewById(R.id.RecipeNameViewPhotoDetail);
        textView.setText(name);
    }

    private void setFoodImage(Integer img) {
        ImageView imageView = findViewById(R.id.RecipeImageViewDetail);
        imageView.setImageResource(img);
    }

    private void setDescription(String description) {
        TextView textView = findViewById(R.id.DescriptionViewPhotoDetail);
        textView.setText(description);
    }

    private void setRating(Float rating) {
        RatingBar ratingBar = findViewById(R.id.ViewRating);
        ratingBar.setRating(rating);
    }

    private void handleBack() {
        findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}